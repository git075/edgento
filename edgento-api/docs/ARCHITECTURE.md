# Edgento API — Architecture

## Overview
Edgento API is a **Spring Boot 3.3.x** backend serving two core purposes:
1. **Lead Management**: Capture and persist leads from website contact forms and the AI agent widget.
2. **AI Business Diagnostic Agent**: A stateful, streaming AI agent that conducts a 5-step business audit via conversation and generates an executive-level report.

**Runtime**: Java 21 (LTS)
**Build**: Maven
**Database**: PostgreSQL 16
**Migrations**: Flyway
**Streaming**: Spring WebFlux (Reactor — Mono/Flux)
**External APIs**: OpenAI (direct HTTP via WebClient, no Spring AI abstraction)
**Email**: Resend API
**Containerization**: Docker + Docker Compose

---

## Layered Architecture

Every HTTP request flows through exactly these layers in order. **No layer may skip another.**

```
HTTP Request (from React frontend or API client)
     │
     ▼
┌──────────────────────────────────────────────────┐
│  Controller Layer  (@RestController)             │
│  • Parses HTTP request (URL, body, headers)      │
│  • Runs validation (@Valid on request DTO)       │
│  • Calls one Service method                      │
│  • Returns HTTP response with correct status code│
│  • ZERO business logic allowed here              │
└──────────────────────┬───────────────────────────┘
                       │ calls
                       ▼
┌──────────────────────────────────────────────────┐
│  Service Layer  (@Service)                       │
│  • ALL business logic lives here                 │
│  • Orchestrates calls to Repositories + APIs     │
│  • Handles transactions (@Transactional)         │
│  • Converts Entities → DTOs via Mappers          │
│  • Throws domain exceptions (ResourceNotFound)   │
└──────────┬───────────────────────────────────────┘
           │ calls                │ calls
           ▼                     ▼
┌──────────────────┐   ┌─────────────────────────────┐
│ Repository Layer │   │ External APIs               │
│ (@Repository)    │   │ • OpenAIService (WebClient)  │
│ • Spring Data JPA│   │ • EmailService (Resend HTTP) │
│ • @Query for     │   └─────────────────────────────┘
│   custom JPQL    │
└────────┬─────────┘
         │
         ▼
┌────────────────────┐
│  PostgreSQL 16     │
│  (via Docker)      │
└────────────────────┘
```

### Layer Rules (Absolute — Never Break These)
| Rule | Reason |
|---|---|
| Controllers NEVER call Repositories directly | Skips business logic, breaks testability |
| Controllers NEVER return JPA Entities | Exposes internal DB structure, security risk |
| Services NEVER contain HTTP-specific code | Services must be reusable outside HTTP context |
| Entities NEVER used as DTOs | Entity changes would silently break the API contract |
| @Autowired NEVER used | Constructor injection (via @RequiredArgsConstructor) is safer and testable |

---

## Package Structure

```
com.edgento.api/
│
├── config/                     ← Spring @Configuration classes only
│   ├── CorsConfig.java         ← CORS rules (allow React frontend origin)
│   ├── OpenAIConfig.java        ← WebClient bean configured with OpenAI base URL + API key
│   └── WebConfig.java          ← General web MVC configuration
│
├── controller/                 ← Thin HTTP handlers only
│   ├── LeadController.java     ← POST/GET /api/v1/leads
│   ├── AgentController.java    ← POST /start, POST /message, GET /stream (SSE), GET /report
│   └── ContactController.java  ← POST /api/v1/contact
│
├── service/                    ← ALL business logic
│   ├── LeadService.java        ← Lead CRUD operations
│   ├── AgentService.java       ← FSM orchestration, conversation persistence
│   ├── OpenAIService.java      ← Raw HTTP calls to OpenAI API (streaming + structured outputs)
│   └── EmailService.java       ← Resend API integration for email notifications
│
├── repository/                 ← Spring Data JPA interfaces
│   ├── LeadRepository.java
│   ├── ConversationRepository.java
│   ├── MessageRepository.java
│   └── AuditReportRepository.java
│
├── model/
│   ├── entity/                 ← JPA-mapped DB tables (never leave the service layer)
│   │   ├── Lead.java
│   │   ├── AuditConversation.java
│   │   ├── ConversationMessage.java
│   │   └── AuditReport.java
│   │
│   ├── dto/
│   │   ├── request/            ← Immutable Java records for incoming API data
│   │   │   ├── CreateLeadRequest.java
│   │   │   ├── StartAuditRequest.java
│   │   │   ├── SendMessageRequest.java
│   │   │   └── ContactRequest.java
│   │   └── response/           ← Immutable Java records for outgoing API data
│   │       ├── LeadResponse.java
│   │       ├── ConversationResponse.java
│   │       ├── AuditReportResponse.java
│   │       └── ApiErrorResponse.java
│   │
│   └── enums/
│       ├── AuditStep.java      ← FSM state: INTRO→TEAM_SIZE→TOOLS_USED→PAIN_POINTS→BUDGET→COMPLETE
│       └── ConversationStatus.java  ← ACTIVE | COMPLETED | ABANDONED
│
├── mapper/                     ← Manual Entity ↔ DTO conversion (no MapStruct yet)
│   ├── LeadMapper.java
│   └── ConversationMapper.java
│
├── exception/                  ← Custom exceptions + global handler
│   ├── GlobalExceptionHandler.java    ← @ControllerAdvice catches all exceptions
│   ├── ResourceNotFoundException.java ← 404 responses
│   └── AgentProcessingException.java  ← 500 when AI pipeline fails
│
└── EdgentoApiApplication.java  ← @SpringBootApplication entry point
```

---

## AI Agent Architecture: Finite State Machine (FSM)

The diagnostic agent enforces a strict 5-step linear conversation flow:

```
INTRO → TEAM_SIZE → TOOLS_USED → PAIN_POINTS → BUDGET → COMPLETE
```

### How Each Step Works

```
User sends message via POST /api/v1/agent/{id}/message
          │
          ▼
AgentService reads currentStep from AuditConversation (from DB)
          │
          ▼
OpenAIService.extractStructuredData() 
  → Sends message to OpenAI with a JSON Schema
  → Forces AI to return a structured JSON object
  → e.g., { "teamSize": 12 } or { "painPoints": ["no CRM", "manual invoicing"] }
          │
          ▼
AgentService saves extracted data to ConversationMessage.extractedData (JSONB)
          │
          ▼
AgentService.transitionStep() — advances FSM to next state
          │
          ▼
OpenAIService.streamNextQuestion()
  → Calls OpenAI with full conversation history
  → Returns a reactive Flux<String> of tokens
          │
          ▼
AgentController streams tokens to frontend via SSE (text/event-stream)
  → Frontend displays typing animation as tokens arrive
          │
          ▼ (when currentStep == COMPLETE)
AgentService.generateAuditReport()
  → Single OpenAI call with all extracted data
  → Returns structured AuditReport JSON
  → Persisted to audit_reports table
```

### SSE Streaming
- The GET `/api/v1/agent/{id}/stream` endpoint returns `Flux<String>` with `MediaType.TEXT_EVENT_STREAM_VALUE`.
- Spring WebFlux handles keeping the connection open and pushing chunks as they arrive from OpenAI.
- The frontend's `useSSE.js` hook subscribes to this stream using the native browser `EventSource` API.

---

## Error Handling Strategy

All errors are caught by `GlobalExceptionHandler` (`@ControllerAdvice`) and returned as:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Lead not found with id: abc-123",
  "timestamp": "2026-08-18T00:00:00Z"
}
```

| Exception | HTTP Status | When |
|---|---|---|
| `ResourceNotFoundException` | 404 | Entity not found by ID |
| `AgentProcessingException` | 500 | OpenAI call failed |
| `MethodArgumentNotValidException` | 400 | @Valid validation failed on DTO |
| `Exception` (catch-all) | 500 | Unexpected server error |

---

## Security & CORS
- CORS is configured in `CorsConfig.java` to allow only `http://localhost:5173` (dev) and `https://edgento.com` (prod).
- No authentication is implemented in Phase B. JWT/Spring Security will be added in a future phase.

---

## Environment Configuration

| Profile | File | When Used |
|---|---|---|
| `dev` | `application-dev.yml` | Local development (Docker Postgres) |
| `prod` | `application-prod.yml` | Hetzner VPS production server |

Key environment variables required:
```
OPENAI_API_KEY=sk-...
DB_URL=jdbc:postgresql://localhost:5432/edgento_dev
DB_USERNAME=postgres
DB_PASSWORD=password
RESEND_API_KEY=re_...
```

---

## Local Development Stack
```
Terminal 1: docker compose up -d postgres     ← DB only (not the api service)
Terminal 2: mvn spring-boot:run               ← Spring Boot (connects to Docker Postgres)
Terminal 3: cd ../edgento-web && npm run dev  ← Vite React frontend
```

URLs:
- Frontend: http://localhost:5173
- Backend:  http://localhost:8080
- API Base: http://localhost:8080/api/v1

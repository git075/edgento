# Edgento API — Coding Rules for AI Assistants

## 🔴 MANDATORY RULE #0: POST-PHASE EXPLANATION (NEVER SKIP THIS)
After completing ANY phase, feature, or significant block of work, you MUST
automatically provide a full elaborated explanation WITHOUT the user having to ask.

This explanation is NOT optional. It is a core deliverable of every task.

The explanation MUST cover every file, class, or concept introduced, using this exact format for EACH one:

1. **WHAT**: What is it? (Plain English, no jargon)
2. **WHY**: Why did we create/use it? (Business + technical reason)
3. **HOW**: How does it work in this project specifically? (Concrete Edgento example)
4. **ALTERNATIVES**: What are the alternatives out there?
5. **WHY NOT ALTERNATIVES**: Why did we choose this approach over those alternatives?

The developer on this project is a BEGINNER. This is their FIRST project. They need
to understand everything deeply. Assume ZERO prior knowledge.

IF YOU COMPLETE A TASK AND DON'T AUTOMATICALLY EXPLAIN IT IN THIS FORMAT, YOU HAVE
FAILED TO COMPLETE THE TASK — even if the code is perfect.

---


## Project Context
Edgento API is a Spring Boot 3.x backend that serves:
1. Lead Management: capture and manage leads from the Edgento website.
2. AI Business Diagnostic Agent: an interactive, streaming AI agent that audits a business through a 5-step conversational flow and generates an executive report.

Base URL: /api/v1/
Database: PostgreSQL 16 (UUIDs as PKs, snake_case columns)
Migrations: Flyway SQL files in src/main/resources/db/migration/

## ⚠️ TEACHING MODE (MANDATORY)
The developer on this project is actively learning. ALL code generation MUST follow these teaching rules:

1. **File-level comment**: Start with a block comment explaining WHAT this file is, WHY it exists, and HOW it fits into the overall architecture.
2. **Annotation explanations**: Every Spring/Java annotation must have an inline comment explaining what it does.
3. **Method-level comments**: Every method must have a comment explaining what it does, why it exists, and how it works for complex logic.
4. **'Why not' explanations**: When a design choice is made, briefly explain the alternative and why it was rejected.
5. **SQL explanations**: Every Flyway migration must explain each line.
6. **Concept callouts**: When using a concept for the first time, include a 📚 CONCEPT block.

## Architecture: Layered (Controller → Service → Repository)

```text
HTTP Request
    ↓
Controller        ← Only HTTP concerns (parsing, status codes, validation)
    ↓
Service           ← ALL business logic lives here
    ↓
Repository        ← Database queries only (Spring Data JPA)
    ↓
Database (PostgreSQL)
    ↓ (also)
External APIs (OpenAI, Resend)
```

### Layer Rules (NEVER break these)
1. **Controllers**: handle HTTP only. NO business logic, NO direct DB calls.
2. **Services**: ALL business logic. Call repositories and external APIs.
3. **Repositories**: Spring Data JPA interfaces. @Query for custom JPQL queries.
4. **Entities**: NEVER returned directly from controllers. Always convert to DTOs.
5. **DTOs**: Java records. Immutable. Only for API communication.

## AI Agent FSM
The diagnostic agent uses a Finite State Machine:
```text
INTRO → TEAM_SIZE → TOOLS_USED → PAIN_POINTS → BUDGET → COMPLETE
```
Each step: receive user message → extract structured data via OpenAI → transition state → generate next question (streamed via SSE) → on COMPLETE: generate AuditReport.

## Code Conventions
- Use constructor injection via Lombok `@RequiredArgsConstructor`. NEVER use `@Autowired`.
- NEVER return JPA entities from controllers. Always use Mapper classes to convert to DTOs.
- Use Java records for all DTOs (request and response objects).
- All request DTOs must have Jakarta Validation annotations (`@NotBlank`, `@Email`, etc.).
- Use Lombok (`@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`) on entities.
- Use enums for all fixed value sets. No magic strings.

## Naming Conventions
- Entities: singular noun (Lead, AuditConversation)
- DTOs: CreateLeadRequest, ConversationResponse (Request/Response suffix)
- Services: LeadService, AgentService (Service suffix)
- Controllers: LeadController (Controller suffix)
- Repositories: LeadRepository (Repository suffix)
- Table/column names: `snake_case`
- Java field names: `camelCase` (JPA maps automatically)

## Error Handling
- All exceptions caught by `GlobalExceptionHandler` (`@ControllerAdvice`).
- Custom exceptions extend `RuntimeException`.
- API errors always return `ApiErrorResponse { status, error, message, timestamp }`.

## Database Conventions
- Always use UUID for primary keys: `DEFAULT gen_random_uuid()`
- All timestamps: `TIMESTAMPTZ` (timezone-aware)
- JSONB for flexible structured data (extracted AI data, arrays)
- All Flyway files: `V{number}__{description}.sql`

## API Design
- Base path: `/api/v1/`
- Use plural nouns for resources (`/leads`, not `/lead`)
- Return 201 for creation, 204 for deletion, 200 for reads/updates
- Paginated responses include: content, totalElements, totalPages, currentPage

## When Adding New Features
1. Add migration SQL first (new Flyway file)
2. Create/update JPA entity
3. Create/update DTO records (request + response)
4. Create/update Mapper
5. Create/update Repository
6. Create/update Service (business logic here)
7. Create/update Controller (HTTP wiring here)
8. Update `docs/API_SPEC.md` and `docs/DATABASE_SCHEMA.md`

## Testing
- Controllers: MockMvc integration tests (`@WebMvcTest`)
- Services: Unit tests with Mockito (`@ExtendWith(MockitoExtension.class)`)
- Repositories: `@DataJpaTest` with Testcontainers (PostgreSQL)

# Architecture Decision Records (ADRs)

An ADR documents a significant architectural decision: what was decided, why, and what was rejected.
This file is the canonical source of truth for "why does this project work the way it does?"

---

## ADR-001: Layered Architecture over Microservices

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** Use a simple Layered Architecture (Controller → Service → Repository).

**Context:** Edgento API has a focused scope — leads and an AI agent. We need to ship fast.

**Rejected Alternative:** Microservices (separate services for Leads, Agent, Notifications).

**Reason for Rejection:** Microservices require service discovery, inter-service HTTP calls, distributed tracing, and significantly more infrastructure. For a two-feature API built by a solo developer, this is extreme over-engineering. We save that complexity for when the product actually needs it.

---

## ADR-002: Flyway over Hibernate Auto-DDL

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** Use Flyway SQL migration files (`V1__...sql`) to manage all schema changes.

**Context:** The database schema will evolve over time. We need a safe, repeatable way to apply changes.

**Rejected Alternative:** `spring.jpa.hibernate.ddl-auto=update` (Hibernate auto-generates and updates the schema).

**Reason for Rejection:**
1. Hibernate's auto-update can silently drop columns if a field is renamed in Java.
2. It doesn't produce a human-readable audit trail of schema changes.
3. It cannot be rolled back safely.
4. Flyway forces us to write real SQL — building genuine DBMS skills.

---

## ADR-003: Java Records for DTOs

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** All DTOs (request and response objects) are Java Records.

**Context:** DTOs are simple data containers that should be immutable.

**Rejected Alternative:** Regular Java classes with Lombok `@Data`.

**Reason for Rejection:** Records are natively immutable (no setters), auto-generate `equals`/`hashCode`/`toString`, and are the modern Java 16+ standard for data carrier objects. Using `@Data` on a DTO risks mutability bugs where something accidentally modifies the request object mid-processing.

---

## ADR-004: SSE over WebSockets for AI Streaming

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** Use Server-Sent Events (SSE) via Spring WebFlux `Flux<String>` for streaming AI tokens.

**Context:** The AI generates responses token-by-token (like ChatGPT's typing effect). We need to stream these to the browser in real time.

**Rejected Alternative:** WebSockets.

**Reason for Rejection:** WebSockets are bidirectional — both the client and server can send messages at any time. This is unnecessary for our use case where only the server streams (the AI response). SSE is:
1. Simpler to implement (standard HTTP, no upgrade handshake).
2. Works seamlessly through CDNs, proxies, and load balancers.
3. Natively supported by browsers via the `EventSource` API.
4. Automatically reconnects if the connection drops.

---

## ADR-005: Direct OpenAI API over Spring AI Framework

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** Call OpenAI directly using Spring's `WebClient` (reactive HTTP client), not the Spring AI abstraction library.

**Context:** We need to use OpenAI's latest "Strict Structured Outputs" feature to force the AI to return guaranteed-valid JSON during data extraction steps.

**Rejected Alternative:** Spring AI (`spring-ai-openai-spring-boot-starter`).

**Reason for Rejection:**
1. Spring AI is still in milestone releases (`0.x.x`) and changes its API frequently.
2. Framework abstractions often lag behind the latest OpenAI features by weeks or months.
3. "Strict Structured Outputs" (using `strict: true` in the JSON Schema) requires precise control over the request payload — harder to achieve through an abstraction layer.
4. Directly calling the API teaches the developer exactly how AI APIs work — foundational knowledge.

---

## ADR-006: OpenAI Structured Outputs for FSM Data Extraction

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** Use OpenAI's JSON Schema mode (`response_format: { type: "json_schema", strict: true }`) to extract structured data from user messages at each FSM step.

**Context:** At each conversational step, we need to extract specific data (e.g., team size as an integer, not "about twelve people"). Free-form AI responses are unreliable for data extraction.

**Why This Works:** OpenAI guarantees the response will exactly match the provided JSON Schema. If the schema says `teamSize` is an integer, the AI will always return an integer — no parsing errors, no hallucinated formats.

---

## ADR-007: Manual Mappers over MapStruct

**Status:** Accepted (Phase B) — To Be Revisited in Phase D
**Date:** 2026-08-10

**Decision:** Write `LeadMapper` and `ConversationMapper` manually instead of using the MapStruct annotation processor.

**Context:** Mappers convert entities to DTOs and back.

**Rejected Alternative:** MapStruct (annotation-based code generation).

**Reason for Rejection (for now):** MapStruct generates code at compile time and requires understanding annotation processors. For a beginner, manually writing mappers:
1. Makes the data transformation 100% explicit and readable.
2. Eliminates a "magic" dependency that's hard to debug.
3. Teaches how mapping actually works before abstracting it away.

MapStruct will be introduced in Phase D when the mapper count grows large enough to justify it.

---

## ADR-008: Separate Frontend and Backend Repositories

**Status:** Accepted
**Date:** 2026-08-10

**Decision:** `edgento-api` and `edgento-web` are two completely separate Git repositories.

**Rejected Alternative:** A monorepo (both projects in one Git repo, e.g., in a `/backend` and `/frontend` folder).

**Reason for Rejection:**
1. They deploy to different infrastructure: backend → Hetzner VPS, frontend → Cloudflare Pages.
2. Separate repos enable completely independent CI/CD pipelines.
3. A backend change that doesn't affect the frontend doesn't need to trigger a frontend deployment.
4. Different team members (a Java dev and a React dev) can work independently without merge conflicts.

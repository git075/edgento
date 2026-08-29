# Edgento API — Endpoint Specification

Base URL (dev):  `http://localhost:8080/api/v1`
Base URL (prod): `https://api.edgento.com/api/v1`

All responses are `application/json`.
All timestamps are ISO 8601 UTC (`2026-08-18T00:00:00Z`).
All IDs are UUID v4.

---

## Standard Error Response

Every failed request returns this shape:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Lead not found with id: 550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2026-08-18T00:00:00Z"
}
```

---

## 1. Leads

### POST `/leads`
Capture a new lead from the website contact form or AI agent widget.

**Request Body:**
```json
{
  "name": "Rahul Sharma",
  "email": "rahul@company.com",
  "companyName": "Sharma Textiles",
  "phone": "+91-9876543210",
  "sourcePage": "/services",
  "utmSource": "google"
}
```
- `name` — Required. Max 255 chars.
- `email` — Required. Must be valid email format.
- `companyName`, `phone`, `sourcePage`, `utmSource` — Optional.

**Response: `201 Created`**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Rahul Sharma",
  "email": "rahul@company.com",
  "companyName": "Sharma Textiles",
  "createdAt": "2026-08-18T00:00:00Z"
}
```

**Error Cases:**
- `400` — Missing `name` or invalid `email` format.

---

### GET `/leads`
Retrieve all leads, paginated. (Admin use only in future — will be secured.)

**Query Parameters:**
- `page` (default: `0`) — Zero-indexed page number.
- `size` (default: `20`) — Records per page.
- `sort` (default: `createdAt,desc`) — Sort field and direction.

**Response: `200 OK`**
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Rahul Sharma",
      "email": "rahul@company.com",
      "companyName": "Sharma Textiles",
      "createdAt": "2026-08-18T00:00:00Z"
    }
  ],
  "totalElements": 45,
  "totalPages": 3,
  "currentPage": 0,
  "size": 20
}
```

---

### GET `/leads/{id}`
Get a specific lead by UUID.

**Response: `200 OK`** — Same shape as single item in list.

**Error Cases:**
- `404` — Lead not found.

---

## 2. AI Diagnostic Agent

### POST `/agent/start`
Begin a new audit conversation. Creates a `Lead` record and an `AuditConversation`, then returns the AI's opening message.

**Request Body:**
```json
{
  "visitorName": "Rahul",
  "visitorEmail": "rahul@company.com"
}
```
- Both fields are required.

**Response: `201 Created`**
```json
{
  "conversationId": "660e8400-e29b-41d4-a716-446655440001",
  "currentStep": "INTRO",
  "status": "ACTIVE",
  "message": "Hi Rahul! I'm the Edgento Business Diagnostic AI. I'll ask you 5 quick questions to audit your business operations and identify where you're leaving money on the table. Ready? Let's start — what's your company name and what industry are you in?",
  "extractedData": null
}
```

---

### POST `/agent/{conversationId}/message`
Send the user's reply to the current step. The backend:
1. Extracts structured data from the message using OpenAI.
2. Saves the message to the DB.
3. Advances the FSM to the next step.
4. Returns the next question.

**Path Variable:** `conversationId` — UUID of an ACTIVE conversation.

**Request Body:**
```json
{
  "content": "We're Sharma Textiles, a fabric manufacturing company with 12 people."
}
```

**Response: `200 OK`**
```json
{
  "conversationId": "660e8400-e29b-41d4-a716-446655440001",
  "currentStep": "TEAM_SIZE",
  "status": "ACTIVE",
  "message": "Got it! A 12-person team in fabric manufacturing — great context. What tools or software does your team currently use day-to-day? (e.g., WhatsApp for orders, Excel for inventory, Tally for accounts)",
  "extractedData": {
    "businessName": "Sharma Textiles",
    "industry": "Fabric Manufacturing"
  }
}
```

**FSM Step → Data Extracted Mapping:**
| Step Completed | Data Extracted |
|---|---|
| `INTRO` | `businessName`, `industry` |
| `TEAM_SIZE` | `teamSize` (integer) |
| `TOOLS_USED` | `tools` (array of strings) |
| `PAIN_POINTS` | `painPoints` (array of strings) |
| `BUDGET` | `monthlyBudget` (string, e.g., "₹15,000") |
| `COMPLETE` | — triggers report generation |

**Error Cases:**
- `404` — Conversation not found.
- `400` — Conversation is not in ACTIVE status.
- `500` — OpenAI API call failed.

---

### GET `/agent/{conversationId}/stream`
SSE endpoint that streams the next AI response token-by-token. The client should open this connection after `POST /message` to receive the response as it generates.

**Response Headers:**
```
Content-Type: text/event-stream
Cache-Control: no-cache
Connection: keep-alive
```

**Response Stream:**
```
data: {"token": "Got"}
data: {"token": " it"}
data: {"token": "!"}
data: {"token": " A"}
data: {"token": " 12"}
data: [DONE]
```

---

### GET `/agent/{conversationId}/report`
Retrieve the generated audit report once the conversation reaches `COMPLETE`.

**Response: `200 OK`**
```json
{
  "reportId": "770e8400-e29b-41d4-a716-446655440002",
  "conversationId": "660e8400-e29b-41d4-a716-446655440001",
  "healthScore": 62,
  "vulnerabilities": [
    "No CRM system — leads likely falling through the cracks",
    "Manual invoicing via Excel — 6-8 hours of admin time per week",
    "WhatsApp for orders — zero audit trail, high error risk"
  ],
  "revenueGapEstimate": "₹28,000/month",
  "recommendations": [
    "Implement a lightweight CRM (Zoho Free or HubSpot Free) to reduce lead leakage by 30%",
    "Move to Vyapar or Zoho Books for invoicing — saves 6+ hours/week",
    "Use a WhatsApp Business API tool for structured order management"
  ],
  "generatedAt": "2026-08-18T00:05:00Z"
}
```

**Error Cases:**
- `404` — Conversation not found or report not yet generated.

---

## 3. Contact Form

### POST `/contact`
Submit the general contact form from the website.

**Request Body:**
```json
{
  "name": "Rahul Sharma",
  "email": "rahul@company.com",
  "message": "I'd like to discuss a custom automation project for my textile business.",
  "service": "CUSTOM_SOLUTION"
}
```
- `name`, `email`, `message` — Required.
- `service` — Optional. Intended service: `CUSTOM_SOLUTION`, `CLIENTOS`, `CLASSKHATA`, `GENERAL`.

**Response: `200 OK`**
```json
{
  "message": "Thank you, Rahul! We'll get back to you within 24 hours."
}
```

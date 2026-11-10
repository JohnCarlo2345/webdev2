# Lab 11 — Answer Template

**Course:** Web Development 2 &nbsp;|&nbsp; **Week:** 11 &nbsp;|&nbsp; **Lab:** Capstone Milestone 3

> **Instructions:** Fill in each section with links to your code and screenshots/recordings. Do not paste full file contents here — link to the file in your repository instead. Submit your GitHub repository URL to Moodle.

---

## Student Information

| Field | Value |
|---|---|
| Name | |
| Student ID | |
| GitHub Repository URL | |
| Branch (if not `main`) | |

---

## Task 1 — Input Validation on DTOs

**DTO files with annotations** *(link to files in your repo)*:

- `CreateStudentRequest.java`: 
- *(add others if applicable)*:

**Evidence of 400 response for missing field:**

> Paste the JSON response body you received when you sent a POST with a missing required field:

```json
// paste 400 response here
```

---

## Task 2 — Global Exception Handler

**Files created:**

- `GlobalExceptionHandler.java`:
- `ErrorResponse.java`:
- `ResourceNotFoundException.java`:

**Evidence — 404 response:**

> Paste the response body for a GET request to a non-existent ID:

```json
// paste 404 response here
```

**Evidence — 400 response with field errors:**

> Paste the response body for a POST with an invalid body, showing the `errors` array:

```json
// paste 400 response with errors array here
```

---

## Task 3 — CORS Configuration

**File created:**

- `WebConfig.java`:

**Evidence — CORS headers present:**

> Paste the curl output or describe the browser Network tab result showing `Access-Control-Allow-Origin`:

```
// paste curl output or description here
```

---

## Task 4 — Pagination and Filtering

**Files updated:**

- Repository method added:
- Service method updated:
- Controller method updated:

**Evidence — paginated response:**

> Paste the JSON response for `GET /api/students?page=0&size=5&sort=name,asc`:

```json
// paste paginated response here
```

**Evidence — filtered response:**

> Paste the JSON response for `GET /api/{resource}?{filter}=...`:

```json
// paste filtered response here
```

**Evidence — max size cap:**

> What response did you get when you requested `?size=999`? What was the actual page size in the metadata?

```
// describe or paste response here
```

---

## Task 5 — Documentation and Test Collection

**Files committed:**

- `API.md` location in repo:
- `api_tests.json` location in repo:

**Test collection run evidence:**

> Paste a screenshot link or describe the test run result (e.g., "5/5 requests passed"):

```
// describe test run result here
```

**List the requests in your collection:**

| Request name | Method | Path | Expected status |
|---|---|---|---|
| | | | |
| | | | |
| | | | |
| | | | |
| | | | |

---

## Reflection

> In 2–3 sentences: which of the five topics from Week 11 had the biggest impact on your capstone API? What surprised you most when implementing it?

*(Write your reflection here)*

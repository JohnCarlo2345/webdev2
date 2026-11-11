# Student API Documentation

## Base URL
`http://localhost:8080/api`

---

## Endpoints

### 1. Get All Students
- **Method:** GET
- **Path:** `/students`
- **Description:** Returns a paginated list of all students
- **Query Params:**
  - `page` (optional, default=0) — Page number
  - `size` (optional, default=20, max=100) — Items per page
  - `sort` (optional, default=id) — Sort field
  - `direction` (optional, default=asc) — Sort direction: asc/desc
  - `program` (optional) — Filter by program name
- **Response Codes:**
  - `200 OK` — Successful
- **Example:**

### 2. Create Student
- **Method:** POST
- **Path:** `/students`
- **Description:** Creates a new student
- **Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "program": "BSIT"
}

- Response Codes:
-  201 Created  — Successful
-  400 Bad Request  — Validation failed

3. Get Student by ID

- Method: GET
- Path:  /students/{id}
- Description: Returns a single student by ID
- Response Codes:
-  200 OK  — Successful
-  404 Not Found  — Student not found

4. Update Student

- Method: PUT
- Path:  /students/{id}
- Description: Updates an existing student
- Response Codes:
-  200 OK  — Successful
-  404 Not Found  — Student not found

5. Delete Student

- Method: DELETE
- Path:  /students/{id}
- Description: Deletes a student
- Response Codes:
-  204 No Content  — Successful
-  404 Not Found  — Student not found

# WebDev2 Lab 10 – Answers.md

Name: John Carlo O. Valdez
Student ID number: 2511004
URL Link: https://github.com/JohnCarlo2345/webdev2
Course/Year: BSIT‑II
Date Submitted: October 31, 2026

## Task 1: URL Design & Controller Skeleton
Evidence:
The six REST endpoints are fully defined: GET /api/students (collection), GET /api/students/{id}, POST /api/students, PUT /api/students/{id}, PATCH /api/students/{id}, DELETE /api/students/{id}. The RestController skeleton compiles correctly and Spring Boot maps each HTTP method properly. When accessed via Postman or browser, the routes are recognized by the server ready for full implementation.

## Task 2: GET Endpoints & DTO Mapping
Evidence:
The findAll method returns a list of StudentResponseDTO (not raw entity) with status 200 OK even for empty array. findById returns 200 OK when found and correctly triggers 404 Not Found when the ID does not exist. The service layer converts the JPA entity to DTO successfully so internal database fields are not exposed directly to the client.

## Task 3: POST – Create Resource with 201 Created
Evidence:
When sending valid CreateStudentRequest JSON through POST, the service saves the new record into MySQL, creates the Location header pointing to the new student URI, and returns status 201 Created plus the StudentResponseDTO body.

## Task 4: PUT, PATCH & DELETE
Evidence:
- PUT: Full replacement updates all fields and returns 200 OK; returns 404 if resource missing
- PATCH: Partial update only changes non‑null fields while keeping unchanged data intact and returns 200 OK
- DELETE: Successful deletion returns 204 No Content; calling DELETE again on already deleted resource returns 404, following idempotent REST rules
All operations use DTOs only and never send raw entity objects back.

## Task 5: DTO Audit + Full Smoke Check
Evidence:
Full audit confirmed: Controller parameters and return types use only CreateStudentRequest, UpdateStudentRequest, StudentResponseDTO (no Student entity exposed). The full smoke flow runs end‑to‑end: POST creates → GET verifies → PUT full update → PATCH partial update → DELETE removes → GET deleted returns 404. Spring Boot connects successfully to MySQL webdev2_lab10 database and all service, repository, model layers communicate properly.

## Reflection
Reflection:
This lab showed the importance of separating model/entity from DTO for security and clean REST design. Correct HTTP status codes help clients understand the result without reading only messages. Using service layers decouples the controller from database logic making the application easier to maintain.

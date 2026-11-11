## Lab 11 - Capstone Milestone 3: Complete Rest API

**Name:** John Carlo Valdez
**Student** ID: 251104
**GitHub Link:** https://github.com/JohnCarlo2345/webdev2
**Branch:** week11
 
## Task 1 - Input Validation on DTOs
 
DTO files with annotations:
- StudentRequest.java - located at  src/main/java/com/johncarlo/webdev2/dto/StudentRequest.java
 
Evidence of 400 response for missing field:
json  
{
  "timestamp": "2026-11-11T16:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for object='studentRequest'. Error count: 1",
  "errors": [
    {
      "field": "name",
      "message": "Name is required"
    }
  ]
}

## Task 2 - Global Exception Handler
 
Files created:
-  GlobalExceptionHandler.java  - located at  src/main/java/com/johncarlo/webdev2/exception/GlobalExceptionHandler.java 
-  ErrorResponse.java  - located at  src/main/java/com/johncarlo/webdev2/dto/ErrorResponse.java 
-  ResourceNotFoundException.java  - located at  src/main/java/com/johncarlo/webdev2/exception/ResourceNotFoundException.java 
 
Evidence - 404 response:
json  
{
  "timestamp": "2026-11-11T16:02:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id: 999",
  "path": "/api/students/999"
}

Evidence - 400 response with field errors:
json  
{
  "timestamp": "2026-11-11T16:03:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "program",
      "message": "Program is required"
    },
    {
      "field": "name",
      "message": "Name must be between 2 and 50 characters"
    }
  ]
}

## Task 3 - CORS Configuration
 
File created:
 
- WebConfig.java  - located at  src/main/java/com/johncarlo/webdev2/config/WebConfig.java
 
Evidence - CORS headers present:
Using curl command:  curl - http://localhost:8080/api/students
 
Response headers show:
 
- Access-Control-Allow-Origin: *
- Access-Control-Allow-Methods: GET,POST,PUT,DELETE,OPTIONS
- Access-Control-Allow-Headers: *

## Task 4 - Pagination and Filtering
 
Files updated:
 
- Repository method added:  StudentRepository.java  - added  findByProgramContainingIgnoreCase  or custom query method
- Service method updated:  StudentService.java  - updated  getAll()  to accept page, size, sort, direction, and program filter parameters
- Controller method updated:  StudentController.java  - added  @GetMapping  with request params for pagination and filtering
 
Evidence - paginated response:
json  
{
  "content": [
    {
      "id": 1,
      "name": "John Carlo Valdez",
      "program": "BSCS"
    },
    {
      "id": 2,
      "name": "Jane Doe",
      "program": "BSIT"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 50,
  "totalPages": 3,
  "size": 20,
  "number": 0,
  "first": true,
  "last": false,
  "numberOfElements": 20,
  "empty": false
}

Evidence - filtered response:
Request:  GET /api/students?program=BSCS 
json  
{
  "content": [
    {
      "id": 1,
      "name": "John Carlo Valdez",
      "program": "BSCS"
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}

Evidence - max size cap:
Request:  GET /api/students?size=999 
Response shows: Spring automatically caps or clamps the page size to a maximum value (commonly set to 20 or 100). The response metadata shows  "pageSize": 20  instead of 999, returning only 20 elements per page.

## Task 5 - Documentation and Test Collection
 
Files committed:
 
-  API.md  - located at root of repository:  API.md 
-  api_tests.json  - located at root of repository:  api_tests.json 
 
Test collection run evidence:
 
5/5 requests passed ✅
 
- ✅ GET all students - 200 OK
- ✅ Create student - 201 Created
- ✅ Get student by ID - 200 OK
- ✅ Update student - 200 OK
- ✅ Delete student - 204 No Content
 
List the requests in your collection:
 
Request name Method Path Expected status 
Get All Students GET  /api/students  200 
Create Student POST  /api/students  201 
Get Student by ID GET  /api/students/{id}  200 
Update Student PUT  /api/students/{id}  200 
Delete Student DELETE  /api/students/{id}  204 
 
 
 
Reflection
 
Implementing Global Exception Handling had the biggest impact on my capstone API because it made error responses consistent and professional across all endpoints, instead of messy default error pages. I was most surprised by how CORS configuration was just a few lines of code but completely unlocked the API for frontend integration and it was simpler than I expected but critical for making the API usable outside Postman.

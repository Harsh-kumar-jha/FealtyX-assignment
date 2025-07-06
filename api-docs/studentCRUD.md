# Student API Documentation

## Base URL
```
/api/v1/students
```

## Endpoints

### 1. Create Student
Creates a new student record.

**Endpoint:** `POST /api/v1/students`

**Request Body:**
```json
{
    "name": "string",
    "age": "integer",
    "email": "string"
}
```

**Validation Rules:**
- name: Required, 2-100 characters
- age: Required, between 16 and 120
- email: Required, valid email format, max 255 characters

**Response:** `201 Created`
```json
{
    "id": "integer",
    "name": "string",
    "age": "integer",
    "email": "string",
    "version": "integer",
    "createdAt": "datetime",
    "updatedAt": "datetime"
}
```

**Error Responses:**
- `400 Bad Request`: Invalid input data
- `409 Conflict`: Email already exists

### 2. Get Student by ID
Retrieves a specific student by their ID.

**Endpoint:** `GET /api/v1/students/{id}`

**Path Parameters:**
- id: Integer (Required)

**Response:** `200 OK`
```json
{
    "id": "integer",
    "name": "string",
    "age": "integer",
    "email": "string",
    "version": "integer",
    "createdAt": "datetime",
    "updatedAt": "datetime"
}
```

**Error Responses:**
- `404 Not Found`: Student not found

### 3. Get All Students (Paginated)
Retrieves a paginated list of students.

**Endpoint:** `GET /api/v1/students`

**Response:** `200 OK`
```json
{
    "content": [
        {
            "id": "integer",
            "name": "string",
            "age": "integer",
            "email": "string",
            "createdAt": "datetime",
            "updatedAt": "datetime"
        }
    ],
}
```

### 4. Update Student
Updates an existing student's information.

**Endpoint:** `PUT /api/v1/students/{id}`

**Path Parameters:**
- id: Integer (Required)

**Request Body:**
```json
{
    "name": "string",
    "age": "integer",
    "email": "string",
    "version": "integer"  // Required for optimistic locking
}
```

**Validation Rules:**
- Same as Create Student
- version: Required, must match current version

**Response:** `200 OK`
```json
{
    "id": "integer",
    "name": "string",
    "age": "integer",
    "email": "string",
    "version": "integer",
    "createdAt": "datetime",
    "updatedAt": "datetime"
}
```

**Error Responses:**
- `400 Bad Request`: Invalid input data
- `404 Not Found`: Student not found
- `409 Conflict`: 
  - Email already exists (if trying to update to an email that belongs to another student)
  - Concurrent modification detected (version mismatch)

### 5. Delete Student
Deletes a specific student.

**Endpoint:** `DELETE /api/v1/students/{id}`

**Path Parameters:**
- id: Integer (Required)

**Response:** `204 No Content`

**Error Responses:**
- `404 Not Found`: Student not found

### 6. Delete All Students
Deletes all students from the database.

**Endpoint:** `DELETE /api/v1/students`

**Response:** `204 No Content`

**Error Responses:**
- `404 Not Found`: No student records found to delete

### 7. Get Student Summary
Generates a summary of a student's profile using Ollama AI.

**Endpoint:** `GET /api/v1/students/{id}/summary`

**Path Parameters:**
- id: Integer (Required)

**Response:** `200 OK`
```json
{
    "studentId": "integer",
    "summary": "string",
    "generatedAt": "datetime"
}
```

**Error Responses:**
- `404 Not Found`: Student not found
- `500 Internal Server Error`: Failed to generate summary (Ollama service error)

## Common Error Response Format
```json
{
    "timestamp": "datetime",
    "status": "integer",
    "error": "string",
    "message": "string",
    "path": "string"
}
```

## Concurrency Handling

The API implements optimistic locking to handle concurrent modifications:

1. Each student record includes a version field that is automatically incremented on updates
2. When updating a student, you must include the current version in the request
3. If the version doesn't match (indicating someone else modified the record), a 409 Conflict response is returned
4. To resolve conflicts:
   - Fetch the latest version of the record
   - Apply your changes to the latest version
   - Submit the update with the new version number

## Pagination

The API supports pagination for listing students:

1. Default page size is 10 records
2. Maximum page size is 100 records
3. Page numbering starts at 0
4. Sort parameters support multiple fields (e.g., "name,asc&sort=age,desc")
5. Response includes metadata for total elements, total pages, and navigation

## Date Format
All datetime fields use the format: `yyyy-MM-dd HH:mm:ss` 
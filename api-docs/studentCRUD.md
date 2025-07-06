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
- age: Required, between 16 and 65
- email: Required, valid email format, max 255 characters

**Response:** `201 Created`
```json
{
    "id": "integer",
    "name": "string",
    "age": "integer",
    "email": "string",
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
    "createdAt": "datetime",
    "updatedAt": "datetime"
}
```

**Error Responses:**
- `404 Not Found`: Student not found

### 3. Get All Students
Retrieves a list of all students.

**Endpoint:** `GET /api/v1/students`

**Response:** `200 OK`
```json
[
    {
        "id": "integer",
        "name": "string",
        "age": "integer",
        "email": "string",
        "createdAt": "datetime",
        "updatedAt": "datetime"
    }
]
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
    "email": "string"
}
```

**Validation Rules:**
- Same as Create Student

**Response:** `200 OK`
```json
{
    "id": "integer",
    "name": "string",
    "age": "integer",
    "email": "string",
    "createdAt": "datetime",
    "updatedAt": "datetime"
}
```

**Error Responses:**
- `400 Bad Request`: Invalid input data
- `404 Not Found`: Student not found
- `409 Conflict`: Email already exists (if trying to update to an email that belongs to another student)

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

## Date Format
All datetime fields use the format: `yyyy-MM-dd HH:mm:ss` 
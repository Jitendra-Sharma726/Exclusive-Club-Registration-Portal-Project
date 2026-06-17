# Exclusive Club Registration Portal Project

## Project Overview

The Exclusive Club Registration Portal is a Spring Boot-based backend application designed to manage memberships for an elite club where data accuracy, validation, and record preservation are critical. The system ensures that only valid members can register, prevents duplicate email registrations, and implements a professional soft delete mechanism that preserves historical data while removing inactive members from active listings.

Unlike simple CRUD applications, this project focuses on enforcing strict validation rules, maintaining data integrity, and implementing real-world membership lifecycle management practices commonly used in enterprise applications.

---

## Business Problem

Elite organizations cannot afford poor-quality data. A member registration system must ensure:

* Members provide valid information.
* Duplicate registrations are prevented.
* Invalid email addresses are rejected.
* Member records are never permanently lost.
* Historical membership data remains available for auditing purposes.

Instead of physically deleting records from the database, the system uses a Soft Delete strategy by marking members as inactive using an `isDeleted` flag.

---

## Core Features

### Member Registration

* Register new club members.
* Validate incoming registration data.
* Enforce minimum name requirements.
* Validate email formats.
* Prevent duplicate email registrations.

### Data Validation

* Reject blank names.
* Reject names shorter than three characters.
* Reject empty emails.
* Reject invalid email formats.

### Duplicate Prevention

* Verify email uniqueness before registration.
* Prevent multiple accounts using the same email address.

### Soft Delete Management

* Mark members as deleted instead of removing records.
* Preserve historical membership data.
* Hide inactive members from active member listings.

### Active Member Directory

* Display only active members.
* Exclude soft-deleted records automatically.

---

## Architecture

The application follows a layered architecture pattern.

### DTO Layer

#### MemberDTO

Acts as a validation layer between client requests and business logic.

Responsibilities:

* Validate incoming data.
* Prevent invalid requests from reaching the database.
* Improve application security and maintainability.

---

### Entity Layer

#### Member

Represents a club member stored in the database.

Attributes:

* id
* name
* email
* isDeleted

The `isDeleted` field defaults to:

```java
private boolean isDeleted = false;
```

Meaning newly registered members are automatically active.

---

### Repository Layer

#### MemberRepository

Provides database operations including:

* Save members
* Find members by ID
* Find members by email
* Retrieve active members using JPQL

---

### Service Layer

#### MemberService

Contains business logic for:

* Registration validation
* Duplicate checking
* Soft deletion
* Active member retrieval

---

### Controller Layer

#### MemberController

Exposes REST APIs for:

* Member registration
* Member removal
* Viewing active members

---

## Validation Rules

### Name Validation

The member name must:

* Not be null
* Not be empty
* Not contain only spaces
* Be at least 3 characters long

Examples:

| Name | Result  |
| ---- | ------- |
| John | Valid   |
| Al   | Invalid |
| ""   | Invalid |
| " "  | Invalid |

---

### Email Validation

The email must:

* Not be blank
* Follow proper email formatting

Examples:

| Email                                     | Result  |
| ----------------------------------------- | ------- |
| [user@gmail.com](mailto:user@gmail.com)   | Valid   |
| [admin@yahoo.com](mailto:admin@yahoo.com) | Valid   |
| abc.com                                   | Invalid |
| hello@                                    | Invalid |

---

## Duplicate Email Prevention

Before saving a member, the system checks:

```java
memberRepository.findByEmail(email)
```

If a matching email exists:

```java
throw new RuntimeException(
    "Email already registered!"
);
```

This guarantees email uniqueness throughout the platform.

---

## Soft Delete Mechanism

### Traditional Delete

```sql
DELETE FROM members
WHERE id = 1;
```

Problem:

* Data is permanently lost.

---

### Soft Delete

Instead:

```java
member.setDeleted(true);
memberRepository.save(member);
```

Database record remains:

| id | name | email                                   | is_deleted |
| -- | ---- | --------------------------------------- | ---------- |
| 1  | John | [john@gmail.com](mailto:john@gmail.com) | true       |

Benefits:

* Audit history retained.
* Recovery possible.
* Compliance-friendly.

---

## Active Member Query

Custom JPQL:

```java
@Query("SELECT m FROM Member m WHERE m.isDeleted = false")
List<Member> findActiveMembers();
```

This ensures only active members appear in the club roster.

---

## REST API Endpoints

### Register Member

**POST**

```http
/api/register
```

Request:

```json
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

Success Response:

```http
200 OK
```

Duplicate Email Response:

```http
400 BAD REQUEST
```

Validation Error Response:

```http
400 BAD REQUEST
```

---

### Soft Delete Member

**DELETE**

```http
/api/{id}
```

Example:

```http
/api/1
```

Success Response:

```http
200 OK
```

Member Not Found:

```http
404 NOT FOUND
```

---

### View Active Members

**GET**

```http
/api/
```

Returns:

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "deleted": false
  }
]
```

Soft-deleted members are excluded.

---

## Example Workflow

### Registration Flow

```text
Receive Request
       |
       v
Validate DTO
       |
       v
Check Duplicate Email
       |
       v
Create Member Entity
       |
       v
Save to Database
       |
       v
Registration Successful
```

---

### Soft Delete Flow

```text
Find Member
      |
      v
Exists?
      |
   Yes
      |
      v
isDeleted = true
      |
      v
Save Member
      |
      v
Hidden From Active List
```

---

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* H2 Database
* Lombok
* Maven

---

## Learning Objectives

By completing this project, developers will gain experience with:

* Spring Boot REST APIs
* DTO-Based Validation
* Bean Validation Annotations
* JPA Entity Mapping
* Custom JPQL Queries
* Derived Query Methods
* Soft Delete Patterns
* Exception Handling
* ResponseEntity Usage
* Layered Architecture Design

---

## Expected Outcome

Upon completion, the Exclusive Club Registration Portal will function as a robust membership management backend capable of validating registrations, preventing duplicate memberships, preserving historical records through soft deletion, and maintaining a clean list of active members. The project demonstrates real-world practices used in enterprise applications where data quality, compliance, and auditability are essential.

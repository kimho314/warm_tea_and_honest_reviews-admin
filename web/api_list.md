# Admin API Specification

## Book Review Admin System

---

This document defines the **API endpoints** for the Book Review Admin System.

The APIs are designed for:

- Spring Boot backend
- Session-based authentication (Spring Security form login)
- Vue.js admin frontend

All admin APIs are **private** and require an authenticated session.

---

## 1. Authentication APIs

### 1.1 Login

**Endpoint**

```
POST /admin/login
```

**Description**

- Authenticates an admin using form login
- Managed by Spring Security

**Request (Form Data)**

```
username=<string>
password=<string>
```

**Success Response**

- HTTP 302 redirect to `/admin`
- HTTP Session created

**Failure Response**

- HTTP 401 or 403
- Error message returned to login page

---

### 1.2 Logout

**Endpoint**

```
POST /admin/logout
```

**Description**

- Logs out the current admin
- Invalidates server-side session

**Success Response**

- HTTP 302 redirect to `/admin/login`
- Session invalidated

---

## 2. Admin Page APIs

### 2.1 Get Admin Dashboard

**Endpoint**

```
GET /admin
```

**Description**

- Entry point after successful login
- Returns admin dashboard page

**Authentication**

- Required

---

## 3. Book Review APIs

### 3.1 Create Book Review

**Endpoint**

```
POST /admin/reviews
```

**Description**

- Creates a new book review
- Associates the review with the logged-in admin

**Authentication**

- Required (session-based)

**Request (Multipart Form Data)**

```
title: string
author: string
rating: number (1–5)
reviewDate: date
coverImage: file (optional)
content: string (HTML)
```

**Server Behavior**

- Extracts adminUserId from authenticated session
- Converts uploaded Word document to HTML (if applicable)
- Saves review to MongoDB

**Success Response**

```
HTTP 201 Created
```

**Failure Responses**

- 400 Bad Request (validation error)
- 401 Unauthorized

---

## 4. Public APIs (Read-Only)

> These APIs are consumed by the public-facing website.

### 4.1 Get All Book Reviews

**Endpoint**

```
GET /api/reviews?page={page}&offset={offset}
```

**Description**

- Returns a list of published book reviews
- Sorted by review date (latest first)
- shows 30 review per page

**Authentication**

- Not required

**Response Example**

```json
[
  {
    "id": "ObjectId",
    "title": "Atomic Habits",
    "author": "James Clear",
    "rating": 4,
    "reviewDate": "2026-01-18",
    "coverImageUrl": "/images/atomic-habits.jpg",
    "total": 100,
    "page": 1,
    "offset" : 30
  }
]
```

---

### 4.2 Get Book Review Detail

**Endpoint**

```
GET /api/reviews/{id}
```

**Description**

- Returns full details of a single book review

**Authentication**

- Not required

**Response Example**

```json
{
  "id": "ObjectId",
  "title": "Atomic Habits",
  "author": "James Clear",
  "rating": 4,
  "reviewDate": "2026-01-18",
  "coverImageUrl": "/images/atomic-habits.jpg",
  "content": "<p>This book explains...</p>"
}
```

---

## 5. Error Handling

| Status Code | Meaning                             |
| ----------- | ----------------------------------- |
| 400         | Invalid request or validation error |
| 401         | Not authenticated                   |
| 403         | Access denied                       |
| 404         | Resource not found                  |
| 500         | Internal server error               |

---

## 6. Security Notes

- All `/admin/**` routes are protected by Spring Security
- CSRF protection enabled for form submissions
- Session-based authentication only

---

## 7. Status

- API list finalized
- Ready for controller and security implementation

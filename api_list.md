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

```bash
$ curl 'http://localhost:8080/admin/reviews' -i -X POST \
    -H 'Content-Type: multipart/form-data' \
    -F 'title=test title;type=application/json' \
    -F 'author=test author;type=application/json' \
    -F 'rating=4.5;type=application/json' \
    -F 'page=300;type=application/json' \
    -F 'language=English;type=application/json' \
    -F 'category=Fiction;type=application/json' \
    -F 'publishedAt=2021-08-01;type=application/json' \
    -F 'excerpt=test excerpt;type=application/json' \
    -F 'cover=@IlkbaharRuyasi.jpg;type=image/jpeg'
```

**Server Behavior**

- Extracts adminUserId from authenticated session
- Converts uploaded Word document to HTML (if applicable)
- Saves review to MongoDB

**Success Response**

```
HTTP 200 OK
```

```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 53

{
  "id" : "0f389622-7051-4472-baed-aa7117718c15"
}
```

**Failure Responses**

- 400 Bad Request (validation error)
- 401 Unauthorized

---

## 4. Public APIs (Read-Only)

> These APIs are consumed by the public-facing website.

### 4.1 Get All Book Reviews

**Endpoint**

```bash
GET /admin/reviews?page={page}&offset={offset}
```

**Request**

```bash
$ curl 'http://localhost:8080/admin/reviews?page=1&offset=30' -i -X GET \
    -H 'Content-Type: application/json'
```

**Description**

- Returns a list of published book reviews
- Sorted by review date (latest first)
- shows 30 review per page

**Authentication**

- Not required

**Response Example**

```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 490

{
  "reviews" : [ {
    "id" : "b1e34854-66a7-40b8-8790-9dae5659fe0b",
    "adminUserId" : "162a59e1-571f-42a3-a41a-edc83b03618a",
    "title" : "test title",
    "author" : "test author",
    "rating" : 4.5,
    "page" : 300,
    "language" : "English",
    "category" : "Fiction",
    "publishedAt" : "2026-02-15",
    "createdAt" : "2026-02-15",
    "coverImage" : "test cover image",
    "excerpt" : "test excerpt"
  } ],
  "total" : 2,
  "page" : 1,
  "offset" : 30
}
```

---

### 4.2 Get Book Review Detail

**Endpoint**

```
GET /admin/reviews/{id}
```

**Request**

```bash
$ curl 'http://localhost:8080/admin/reviews/6c4e703b-3f06-4718-81b1-59bfe0baf013' -i -X GET \
    -H 'Content-Type: application/json'
```

**Description**

- Returns full details of a single book review

**Authentication**

- Not required

**Response Example**

```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 391

{
  "id" : "6c4e703b-3f06-4718-81b1-59bfe0baf013",
  "adminUserId" : "162a59e1-571f-42a3-a41a-edc83b03618a",
  "title" : "test title",
  "author" : "test author",
  "rating" : 4.5,
  "page" : 300,
  "language" : "English",
  "category" : "Fiction",
  "publishedAt" : "2026-02-15",
  "createdAt" : "2026-02-15",
  "coverImage" : "test cover image",
  "excerpt" : "test excerpt"
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

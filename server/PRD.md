# Product Requirements Document (PRD)

## Admin Book Review Management System

---

## 1. Overview

This document defines the product requirements for the **Book Review Admin System**.

The admin system is a **private internal tool** used by the site owner to manage book reviews displayed on the public book review website.

The system is intentionally minimal, prioritizing **simplicity, clarity, and low maintenance**. There are no public users and no self-service registration.

---

## 2. Goals & Non-Goals

### 2.1 Goals

- Provide a secure admin-only interface
- Allow the admin to add and manage book reviews
- Keep the feature set minimal and easy to maintain
- Support a straightforward content publishing workflow

### 2.2 Non-Goals

- No public user access
- No user registration or password recovery UI
- No role-based access control
- No analytics, metrics, or dashboards

---

## 3. Target Users

| User Type | Description                                  |
| --------- | -------------------------------------------- |
| Admin     | Site owner / developer managing book reviews |

Admin accounts are **manually created directly in MongoDB** by the developer.

---

## 4. Tech Stack

### 4.1 Frontend

- HTML
- CSS
- Vue.js (Vue 3)

### 4.2 Backend

- Spring Boot
- Spring Security
- MongoDB

---

## 5. Functional Requirements

> **Authentication Method**: Form-based login using Spring Security
>
> - Username (or email) + password authentication
> - Session-based authentication (HTTP Session)
> - No JWT or token-based authentication

### 5.1 Authentication (Form Login)

#### 5.1.1 Login

- Admin logs in using:
  - Username or email
  - Password

- Credentials are validated by the backend
- Spring Security form login is used
- On successful login:
  - An HTTP session is created
  - Admin is redirected to the admin dashboard

- On failed login:
  - An error message is displayed

#### 5.1.2 Logout

- Admin can log out manually
- Logout invalidates the server-side session
- After logout:
  - Session is cleared
  - Admin is redirected to the login page

---

### 5.2 Book Review Management

#### 5.2.1 Add Book Review

The admin can create a new book review with the following fields:

**Metadata (stored separately from review body):**

- Book title
- Author
- Rating (e.g. 1–5 stars)
- Review date
- Cover image (URL or uploaded file)

**Content:**

- Review body text (imported from a Word document or entered via editor)

#### 5.2.2 Review Submission Flow

1. Admin navigates to the "Add Book Review" page
2. Admin fills in review metadata
3. Admin provides review content
4. Admin submits the form
5. Backend validates and stores the review in MongoDB
6. Review becomes available on the public website

---

## 6. Admin Pages

### 6.1 Login Page

- Username / password input fields
- Login button
- Error message on authentication failure

### 6.2 Admin Dashboard (Minimal)

- Default landing page after login
- Navigation options:
  - Add Book Review
  - Logout

### 6.3 Add Book Review Page

- Form-based UI
- Clear separation between:
  - Review metadata fields
  - Review content input

- Submit and cancel actions

---

## 7. Security Requirements

- Spring Security form login-based authentication
- Session-based authentication only (no JWT)
- All admin routes require an authenticated session
- CSRF protection enabled for all form submissions
- Passwords stored as securely hashed values in MongoDB
- No admin credentials exposed on the frontend

---

## 8. Data Model (High-Level)

### Admin User

- id
- username or email
- passwordHash
- createdAt

### Book Review

- id
- title
- author
- rating
- reviewDate
- coverImageUrl
- content
- createdAt
- updatedAt

---

## 9. UX Principles

- Minimal and distraction-free UI
- Clear form labels and validation feedback
- Fast page load times
- Desktop-first usage
- Keyboard-friendly form interactions

---

## 10. Constraints & Assumptions

- Admin accounts are manually managed in MongoDB
- Only one or very few admins are expected
- Admin interface is not public-facing
- No requirement for mobile optimization

---

## 11. Success Criteria

- Admin can log in reliably
- Admin can add a book review in under 2 minutes
- Admin functionality is not accessible without authentication
- System operates stably with minimal maintenance

---

## 12. Status

- Requirements finalized
- Ready for technical design and implementation

# Warm Tea and Honest Reviews - Admin Frontend

This project is an admin frontend web application for managing a book review site.  
Built with Vite and Vue 3, it integrates with a backend (Spring Boot) to provide review registration and management features.

## 🛠 Tech Stack

- **Framework**: Vue 3 (Composition API)
- **Build Tool**: Vite
- **Routing**: Vue Router
- **HTTP Client**: Axios
- **Styling**: Plain CSS (maintaining original reference design)

## 📁 Project Structure

```text
web/
├── src/
│   ├── api/            # API client configuration (Axios)
│   ├── assets/         # CSS stylesheets and static assets
│   ├── router/         # Vue Router configuration and route definitions
│   ├── views/          # Page components (Login, Dashboard, AddReview)
│   └── App.vue         # Root component
├── reference/          # Original reference HTML/CSS files
├── public/             # Static resources
├── index.html          # Main HTML file
├── vite.config.js      # Vite configuration (including proxy settings)
├── package.json        # Dependency and script definitions
├── PRD.md              # Product Requirements Document
└── api_list.md         # API Specification
```

## ✨ Features

- **Admin Authentication**: 
  - Supports Spring Security form login.
  - Uses session-based authentication.
- **Dashboard**: 
  - Provides an admin-only menu and logout functionality.
- **Add Book Review**: 
  - Input metadata such as book title, author, rating, and date.
  - Upload cover image files.
  - Compose review bodies via Word files (.docx) or direct HTML input.

## 🚀 Getting Started

### Prerequisites

- Node.js (Latest LTS recommended)
- npm or yarn

### Installation

```bash
# Install dependencies
npm install
```

### Development

```bash
# Run local development server (Default port: 5173)
npm run dev
```

Requests to `/admin` and `/api` are forwarded to `http://localhost:8080` (Spring Boot server) via the proxy configured in `vite.config.js`.

### Build

```bash
# Create production build (generated in 'dist' folder)
npm run build
```

## 🔐 Security & Backend Integration

- **CORS & Session**: The `withCredentials: true` option is applied to Axios requests to enable session sharing with the backend.
- **API Specification**: Please refer to `api_list.md` for detailed API information.
- **Requirements**: Please refer to `PRD.md` for detailed functional requirements.

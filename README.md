# Warm Tea and Honest Reviews - Admin Frontend

This project is an admin frontend web application for managing a book review site.  
Built with Vite and Vue 3, it integrates with a backend (Spring Boot) to provide review registration and management features.

## 🛠 Tech Stack

- **Framework**: Vue 3 (Composition API)
- **Build Tool**: Vite
- **Routing**: Vue Router
- **HTTP Client**: Axios
- **Rich Text Editor**: Vue Quill
- **Styling**: Plain CSS

## 📁 Project Structure

```text
warm-tea-and-honest-reviews-admin/
├── src/
│   ├── api/            # API client configuration (Axios)
│   ├── assets/         # CSS stylesheets and static assets
│   ├── components/     # Reusable UI components
│   ├── router/         # Vue Router configuration
│   ├── views/          # Page components (Login, Dashboard, AddReview, ReviewDetail)
│   ├── App.vue         # Root component
│   └── main.js         # Application entry point
├── public/             # Static resources
├── reference/          # Original reference HTML/CSS files
├── index.html          # Main HTML template
├── vite.config.js      # Vite configuration (proxy settings)
├── package.json        # Dependencies and scripts
├── PRD.md              # Product Requirements Document
└── api_list.md         # API Specification
```

## ✨ Features

- **Admin Authentication**: 
  - Supports Spring Security Basic Authentication.
  - Maintains session via cookies and locally stored credentials for API requests.
- **Dashboard**: 
  - Overview of existing reviews with pagination.
  - Admin-only navigation and logout functionality.
- **Add/View Book Review**: 
  - Input metadata such as book title, author, rating, page count, language, categories, and date.
  - Upload cover image files.
  - Compose review content using a rich text editor (Quill).
  - Detailed view of published reviews.

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

Requests to `/admin` and `/api` are forwarded to the backend server (configured in `vite.config.js`).

### Build

```bash
# Create production build (generated in 'dist' folder)
npm run build
```

## 🔐 Security & Backend Integration

- **CORS & Session**: The `withCredentials: true` option is applied to Axios requests to enable session sharing. 
- **Authentication**: Uses Basic Authentication. Credentials are stored in `localStorage` as a Base64-encoded string (`basicToken`) and included in the `Authorization` header for each request.
- **API Specification**: See `api_list.md` for details.
- **Requirements**: See `PRD.md` for functional requirements.

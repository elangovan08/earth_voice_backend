# EcoBlog Frontend Architecture

## Folder Structure

```text
src/
├── components/      Reusable UI components
├── context/         Auth Context API state
├── hooks/           Reusable React hooks
├── layouts/         Shared page layout
├── pages/           Route-level pages
├── routes/          Router and protected route setup
├── services/        Axios API layer
├── utils/           Validation and storage helpers
└── assets/          Static frontend assets
```

## JSP to React Mapping

| Old JSP | React Page |
| --- | --- |
| `home.jsp` | `HomePage.jsx` |
| `about.jsp` | `AboutPage.jsx` |
| `contact.jsp` | `ContactPage.jsx` |
| `post-list.jsp` | `PostsPage.jsx` |
| `post-details.jsp` | `PostDetailsPage.jsx` |
| `add-post.jsp` | `PostFormPage.jsx` |
| `login.jsp` | `LoginPage.jsx` |
| `signup.jsp` | `SignupPage.jsx` |
| `admin-dashboard.jsp` | `AdminDashboardPage.jsx` |
| `header.jsp` | `Navbar.jsx` |
| `footer.jsp` | `Footer.jsx` |

## API Integration

All backend calls go through `src/services/apiClient.js`.

Set the backend URL in `.env`:

```text
VITE_API_BASE_URL=http://localhost:8080/api
```

Service files:

- `authService.js`: login/signup
- `postService.js`: CRUD, search, likes, bookmarks
- `commentService.js`: list/add/delete comments
- `contactService.js`: contact form submit
- `adminService.js`: admin dashboard data

## Authentication Flow

The current backend returns a user object after login/signup, not a JWT. The frontend stores that user in `localStorage` and exposes it through `AuthContext`.

Protected routes:

- `/posts/new`
- `/posts/:id/edit`
- `/admin` requires role `ADMIN`

For production, replace this local user-only auth with JWT or secure cookie sessions from the backend.

## Best Practices Used

- Route pages stay thin and call service methods.
- Reusable UI lives in `components`.
- Axios configuration is centralized.
- Form validation happens before API calls.
- Loading and error states are explicit.
- Password is never stored, because the backend DTO does not return it.
- Bootstrap provides responsive layout; custom CSS keeps the EcoBlog identity consistent.

## Run Order

1. Start backend:

```bash
./mvnw spring-boot:run
```

2. Start frontend:

```bash
cd frontend
npm install
npm run dev
```

3. Open:

```text
http://localhost:3000
```

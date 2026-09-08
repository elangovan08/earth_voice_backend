# EcoBlog React Frontend

React + Vite frontend for the EcoBlog Spring Boot REST API.

## Run

```bash
npm install
npm run dev
```

The app runs at `http://localhost:3000` and calls the backend at `VITE_API_BASE_URL`.

## Main Folders

- `components`: reusable UI pieces
- `pages`: route-level screens
- `services`: Axios API service layer
- `context`: app-wide state such as auth
- `routes`: React Router and protected route setup
- `layouts`: shared page shell
- `hooks`: reusable async helpers
- `utils`: local storage and validation helpers

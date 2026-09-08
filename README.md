# EarthVoice – An Eco-Conscious Blog Platform

Eco-conscious blog platform built with Spring Boot and React. Features posts, comments, likes, bookmarks, login/signup, roles, notifications, and more.

## Notification email configuration

The backend stores admin notifications in the `notification` table and sends HTML email through Gmail SMTP asynchronously. Configure these environment variables before starting the application:

- `MAIL_USERNAME`: Gmail SMTP username
- `MAIL_PASSWORD`: Gmail app password
- `ADMIN_NOTIFICATION_EMAIL`: admin recipient; defaults to `elangovandev27@gmail.com`
- `MAIL_FROM`: optional sender address; defaults to `MAIL_USERNAME`

The starter mail dependency is already included in `pom.xml`. For an existing production database, run `src/main/resources/db/notification-system-migration.sql` once before starting with the production profile. Production keeps `spring.jpa.hibernate.ddl-auto=validate`.

---

## 🔒 User Roles

| Role    | Access Permissions |
|---------|--------------------|
| Admin   | Manage users, posts, and comments |
| Poster  | Create and manage own posts |
| Viewer  | View posts and comment/like/bookmark |

---

## 🖼️ Screenshots

> Add screenshots of:
- Home page
- Post list and details
- Admin dashboard
- Comment section
- Responsive view on mobile

---
## Project Structure

```text
eco-blog-backend-main/
├── src/main/java/          Spring Boot API and business logic
├── src/main/resources/     Application configuration and database scripts
├── src/main/webApp/        Legacy server-rendered views
├── frontend/               React/Vite client
├── scripts/                Local development helpers
├── Dockerfile
├── pom.xml
└── mvnw.cmd
```
---

## ⚙️ Installation & Setup

### Prerequisites
- Java 25 LTS
- Maven Wrapper (`mvnw.cmd`)
- PostgreSQL (Neon recommended for production)
- IntelliJ IDEA / Eclipse

### Steps
1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd eco-blog-backend-main
    ```

2. Create a PostgreSQL/Neon database and configure its JDBC URL:
    ```sql
    -- Run src/main/resources/db/notification-system-migration.sql
    ```

3. Configure `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://your-neon-host/blogdb?sslmode=require
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Build the backend:
    ```bash
    mvnw.cmd clean test
    ```

5. Run the backend:
    ```bash
    mvnw.cmd spring-boot:run
    ```

6. Run the frontend from `frontend/`:
    ```bash
    npm install
    npm run dev
    ```

### Render and Neon deployment

Set these Render environment variables for the backend:

- `DATABASE_URL`: Neon JDBC URL, for example `jdbc:postgresql://your-neon-host/blogdb?sslmode=require`
- `DB_USERNAME`: Neon database user
- `DB_PASSWORD`: Neon database password
- `SPRING_PROFILES_ACTIVE=prod`
- `APP_BASE_URL`: deployed Vercel frontend URL

Run `src/main/resources/db/notification-system-migration.sql` once against Neon before using the production profile.

---

## 💡 Future Enhancements

- 🔐 Integrate Spring Security for full role-based login
- 📱 Progressive Web App (PWA) support
- 🌐 Multi-language support
- 📈 Post analytics dashboard
- 🗳️ Post voting and reaction emojis

---

## 🤝 Contributing

Contributions are welcome!  
Fork the repo, create a branch, make your changes, and submit a pull request.

---

## 🙌 Acknowledgements

- Spring Boot Documentation
- Bootstrap 5
- Unsplash (for eco images)
- JSTL & JSP Communities

---

## 🌏 About EarthVoice

EarthVoice is built with the mission to **promote awareness**, **share green ideas**, and **protest harmful practices**.  
Together, let’s create a cleaner and more conscious future. 🌿

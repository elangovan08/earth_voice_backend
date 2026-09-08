<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Signup - EarthVoice</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
    <style>
        body {
            background-color: #f1f8ff;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .form-label {
            font-weight: 500;
        }
        .text-danger {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

<div class="container mt-5" style="max-width: 500px;">
    <div class="card p-4">
        <h3 class="text-center mb-4">📝 Create Your EarthVoice Account</h3>

        <form:form action="/signup" modelAttribute="user" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <form:input path="username" cssClass="form-control"/>
                <form:errors path="username" cssClass="text-danger"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <form:input path="email" cssClass="form-control"/>
                <form:errors path="email" cssClass="text-danger"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Password</label>
                <form:password path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="text-danger"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Select Role</label>
                <form:select path="role" cssClass="form-select">
                    <form:option value="POSTER" label="Poster"/>
                    <form:option value="VIEWER" label="Viewer"/>
                </form:select>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-success">Register</button>
            </div>
        </form:form>

        <div class="mt-3 text-center">
            Already have an account? <a href="/login">Login here</a>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

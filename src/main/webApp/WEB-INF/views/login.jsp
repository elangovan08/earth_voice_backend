<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - EarthVoice</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
</head>
<body style="background-color: #f1f8ff;">

<div class="container mt-5" style="max-width: 400px;">
    <h3 class="text-center mb-4">🔐 Login to EarthVoice</h3>

    <!-- Show login error -->
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger text-center">
            Invalid username or password.
        </div>
    </c:if>

    <!-- Show logout message -->
    <c:if test="${not empty param.logout}">
        <div class="alert alert-success text-center">
            You have been logged out successfully.
        </div>
    </c:if>

<form action="<c:url value='/perform_login'/>" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required />
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required />
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
    </form>

    <div class="mt-3 text-center">
        <p>Don't have an account? <a href="/signup">Sign up here</a></p>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

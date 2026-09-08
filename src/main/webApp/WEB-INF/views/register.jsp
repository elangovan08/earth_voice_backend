<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - EarthVoice</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
</head>
<body style="background-color: #f1f8ff;">

<div class="container mt-5" style="max-width: 500px;">
    <h3 class="text-center mb-4">📝 Register for EarthVoice</h3>

    <form action="<c:url value='/register'/>" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required />
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required />
        </div>

        <div class="d-grid">
            <button type="submit" class="btn btn-success">Register</button>
        </div>
    </form>

    <div class="mt-3 text-center">
        <p>Already have an account? <a href="/login">Login here</a></p>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

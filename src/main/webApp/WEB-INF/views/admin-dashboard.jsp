<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - EarthVoice</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
</head>
<body style="background-color: #f0f8ff;">
<div class="container mt-4">
    <h2 class="text-center mb-4">🌿 Admin Dashboard</h2>

    <div class="row text-center">
        <div class="col-md-4">
            <div class="card bg-success text-white mb-3">
                <div class="card-body">
                    <h5 class="card-title">Total Users</h5>
                    <p class="card-text fs-4">${userCount}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card bg-primary text-white mb-3">
                <div class="card-body">
                    <h5 class="card-title">Total Posts</h5>
                    <p class="card-text fs-4">${postCount}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card bg-warning text-dark mb-3">
                <div class="card-body">
                    <h5 class="card-title">Total Comments</h5>
                    <p class="card-text fs-4">${commentCount}</p>
                </div>
            </div>
        </div>
    </div>

    <h4 class="mt-5">🧑 Recent Users</h4>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${recentUsers}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.role}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

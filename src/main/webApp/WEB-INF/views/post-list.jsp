<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Posts</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background-color: #f8f9fa; }
        .card-img-top {
            height: 180px;
            object-fit: cover;
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .card-body p {
            color: #555;
            min-height: 60px;
        }
        .card-footer {
            background-color: #f0f0f0;
            border-top: 1px solid #e0e0e0;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4 text-center text-success">EarthVoice - All Posts</h2>

    <div class="row">
        <c:forEach var="post" items="${listPosts}">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <!-- Single Eco-friendly Image -->
                    <img src="https://images.unsplash.com/photo-1508780709619-79562169bc64?auto=format&fit=crop&w=800&q=60"
                         class="card-img-top" alt="EarthVoice" />

                    <div class="card-body">
                        <h5 class="card-title">${post.title}</h5>
                        <p class="card-text">
                            <c:choose>
                                <c:when test="${fn:length(post.content) > 100}">
                                    ${fn:substring(post.content, 0, 100)}...
                                </c:when>
                                <c:otherwise>
                                    ${post.content}
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

                    <div class="card-footer d-flex flex-column">
                        <small class="text-muted">
                            👤 <c:out value="${post.author.username}" /> |
                            🕒 ${post.createdAt} |
                            ❤️ ${post.likeCount}
                        </small>
                        <div class="mt-2 d-flex justify-content-between">
                            <a href="/posts/${post.id}" class="btn btn-sm btn-outline-primary">View</a>
                            <a href="/edit/${post.id}" class="btn btn-sm btn-outline-warning">Edit</a>
                            <a href="/delete/${post.id}" class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Are you sure you want to delete this post?')">Delete</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<%@ include file="footer.jsp" %>
</body>
</html>

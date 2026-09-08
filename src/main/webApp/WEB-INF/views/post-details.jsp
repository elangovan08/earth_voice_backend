<!-- Updated post-details.jsp with comments, like/bookmark, Bootstrap/jQuery -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${post.title} - EarthVoice</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body style="background-color: #f0fdf4;">

<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h3>${post.title}</h3>
            <p class="text-muted">Posted by ${post.author.username} on ${post.createdAt}</p>
            <p>${post.content}</p>

            <div class="d-flex justify-content-between">
                <div>
                    <a href="/like/${post.id}" class="btn btn-outline-danger btn-sm">❤️ Like (${post.likeCount})</a>
                    <a href="/bookmark/${post.id}" class="btn btn-outline-warning btn-sm">🔖 Bookmark</a>
                </div>
                <a href="/posts" class="btn btn-secondary btn-sm">⬅ Back to Posts</a>
            </div>
        </div>
    </div>

    <!-- Comments Section -->
    <div class="mt-4">
        <h5>💬 Comments (${fn:length(comments)})</h5>

        <c:forEach var="comment" items="${comments}">
            <div class="border p-3 mb-2 rounded bg-light">
                <strong>${comment.user.username}</strong> <small class="text-muted">on ${comment.createdAt}</small>
                <p>${comment.content}</p>
            </div>
        </c:forEach>

        <!-- Comment Form -->
        <form method="post" action="/posts/${post.id}/comment" class="mt-3">
            <div class="mb-2">
                <label for="commentContent" class="form-label">Add a comment</label>
                <textarea class="form-control" id="commentContent" name="content" required rows="3"></textarea>
            </div>
            <button type="submit" class="btn btn-success btn-sm">➕ Submit</button>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>

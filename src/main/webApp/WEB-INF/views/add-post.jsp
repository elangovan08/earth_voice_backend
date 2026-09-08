<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${post.id == null ? 'Add Post' : 'Edit Post'}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
    <style>
        body { background-color: #f1f8ff; }
        .card { border-radius: 15px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); }
        .form-label { font-weight: 500; }
        .preview-img { max-height: 200px; object-fit: cover; margin-top: 10px; border: 1px solid #ccc; padding: 4px; }
    </style>
</head>
<body>

<div class="container mt-5" style="max-width: 700px;">
    <div class="card p-4">
        <h2 class="mb-4 text-center text-primary">
            ${post.id == null ? '📝 Add New Post' : '✏️ Edit Post'}
        </h2>

        <form:form action="/save" modelAttribute="post" method="post" enctype="multipart/form-data">
            <c:if test="${_csrf != null}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </c:if>

            <form:hidden path="id" />

            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <form:input path="title" class="form-control" id="title" required="required" />
                <form:errors path="title" cssClass="text-danger" />
            </div>
            <div class="mb-3">
              <label for="author" class="form-label">Author</label>
              <form:input path="author.username" class="form-control" id="author" readonly="${not empty post.id}" required="required"/>
              <form:errors path="author.username" cssClass="text-danger"/>
            </div>

            <div class="mb-3">
                <label for="content" class="form-label">Content</label>
                <form:textarea path="content" rows="5" class="form-control" id="content" required="required" />
                <form:errors path="content" cssClass="text-danger" />
            </div>

            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-success">Save</button>
                <a href="/posts" class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<%@ include file="footer.jsp" %>
</body>
</html>

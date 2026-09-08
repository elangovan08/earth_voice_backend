<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>EarthVoice</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

  <style>
    /* Sticky Navi Blue Navbar */
    .navbar {
      position: sticky;
      top: 0;
      z-index: 999;
      background-color: #0a4275; /* Navi Blue */
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .navbar-brand {
      font-size: 1.6rem;
      font-weight: bold;
      color: #ffffff !important;
      display: flex;
      align-items: center;
      gap: 5px;
      animation: slideIn 0.8s ease-out forwards;
    }

    @keyframes slideIn {
      from {
        transform: translateX(-50px);
        opacity: 0;
      }
      to {
        transform: translateX(0);
        opacity: 1;
      }
    }

    .nav-link {
      font-weight: 500;
      color: #ffffff !important;
      margin-right: 1rem;
      transition: color 0.3s ease;
    }

    .nav-link:hover {
      color: #99d8ff !important;
    }

    .search-icon {
      border: none;
      background: none;
      font-size: 1.2rem;
      color: #ffffff;
    }

    .search-icon:hover {
      color: #99d8ff;
    }

    .search-input {
      display: none;
      width: 180px;
      margin-left: 10px;
      transition: width 0.3s ease;
    }

    .search-active .search-input {
      display: inline-block;
    }

    .search-input {
      background-color: #f1f1f1;
      border-radius: 5px;
      padding: 3px 10px;
      border: 1px solid #ccc;
    }

    @media (max-width: 992px) {
      .navbar-nav {
        flex-wrap: wrap;
      }
    }
  </style>
</head>

<body style="padding-top: 72px;">

<!-- Sticky Navi Blue Navbar -->
<nav class="navbar navbar-expand-lg fixed-top px-4">
  <div class="container-fluid">
    <!-- Logo / Brand -->
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
      🌿 EarthVoice
    </a>

    <!-- Full Navbar Links -->
    <ul class="navbar-nav flex-row flex-wrap align-items-center mb-0">
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a></li>
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/about">About</a></li>
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/posts">Posts</a></li>
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/add">Add Post</a></li>
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a></li>
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/signup">Signup</a></li>
      <li class="nav-item">
        <button class="search-icon btn" id="toggleSearch"><i class="bi bi-search"></i></button>
      </li>
      <li class="nav-item">
        <form class="d-inline" method="get" action="${pageContext.request.contextPath}/search">
          <input type="text" class="form-control form-control-sm search-input" name="keyword" placeholder="Search..." id="searchInput">
        </form>
      </li>
    </ul>
  </div>
</nav>

<!-- JS Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Toggle search input visibility
  document.getElementById("toggleSearch").addEventListener("click", function () {
    document.querySelector(".navbar-nav").classList.toggle("search-active");
    document.getElementById("searchInput").focus();
  });
</script>

</body>
</html>

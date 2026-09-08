<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Page-specific CSS -->
<style>
    body {
        background: linear-gradient(135deg, #ofa4af 0%, #afdde5 100%);
        font-family: 'Segoe UI', sans-serif;
        background-attachment: fixed;
        background-repeat: no-repeat;
        background-size: cover;
    }

    .about-hero {
        position: relative;
        background: url('https://images.unsplash.com/photo-1522199778941-98bc10a2ca72?auto=format&fit=crop&w=1500&q=80') no-repeat center;
        background-size: cover;
        height: 60vh;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        border-radius: 0 0 30px 30px;
    }

    .about-hero::before {
        content: "";
        position: absolute;
        top: 0; left: 0; width: 100%; height: 100%;
        background: rgba(0, 0, 0, 0.5);
        border-radius: 0 0 30px 30px;
    }

    .about-hero h1 {
        position: relative;
        font-size: 3rem;
        font-weight: bold;
        z-index: 1;
        text-shadow: 1px 1px 3px rgba(0,0,0,0.6);
    }

    .section-title {
        color: #1d5c36;
        font-weight: 700;
        margin: 60px 0 20px;
        text-align: center;
    }

    .about-content p {
        font-size: 1.1rem;
        color: #444;
    }

    .info-section {
        background: white;
        border-radius: 15px;
        padding: 30px;
        margin-bottom: 40px;
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
    }

    .list-group-item {
        border: none;
        background: #f6fdf6;
    }

    .btn-lg {
        padding: 0.75rem 2rem;
        font-size: 1.1rem;
    }
</style>

<!-- Hero Section -->
<div class="about-hero">
    <h1 data-aos="zoom-in">About EarthVoice 🌿</h1>
</div>

<!-- Main Content -->
<div class="container about-content my-5">

    <div class="info-section" data-aos="fade-up">
        <h2 class="section-title">Who We Are</h2>
        <p class="text-center">
            <strong>EarthVoice</strong> is an open blogging platform created with Spring Boot and JSP, designed for people who care about the planet.
            Our goal is to build a safe, inspiring digital space where users can share ideas, solutions, and opinions on environmental topics.
        </p>
    </div>

    <div class="info-section" data-aos="fade-up">
        <h3 class="section-title">🌍 Our Mission</h3>
        <p>
            EarthVoice was born from the idea that small voices can make a big impact. Whether you’re fighting against plastic pollution, supporting wildlife, or writing about green technology,
            your blog can educate, inspire, and activate change. Our mission is to give your voice the platform it deserves.
        </p>
    </div>

    <div class="info-section" data-aos="fade-up">
        <h3 class="section-title">🚀 Technologies Used</h3>
        <ul class="list-group mb-4">
            <li class="list-group-item">✅ Spring Boot (Backend Framework)</li>
            <li class="list-group-item">✅ JSP & JSTL (Frontend View Layer)</li>
            <li class="list-group-item">✅ Bootstrap 5 (Responsive UI)</li>
            <li class="list-group-item">✅ PostgreSQL / Neon (Database)</li>
        </ul>
    </div>

    <div class="info-section" data-aos="fade-up">
        <h3 class="section-title">🤝 Get Involved</h3>
        <p>
            This is more than just a blog. It’s a movement. Join us as a writer, reader, or contributor.
            Let’s protect nature, share our stories, and influence society — one blog at a time.
        </p>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-success btn-lg">⬅️ Back to Home</a>
        </div>
    </div>
</div>

<!-- Footer Include -->
<jsp:include page="footer.jsp" />

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
    AOS.init({ duration: 1000 });
</script>

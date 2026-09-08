<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EarthVoice - Home</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Animate on Scroll -->
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(to right, #e6f2e6, #ffffff);
            font-family: 'Segoe UI', sans-serif;
        }

        .hero {
            background: url('https://png.pngtree.com/thumb_back/fw800/background/20250619/pngtree-green-tree-blossoming-on-earth-with-butterflies-symbolizing-nature-and-environmental-image_17441652.jpg') no-repeat center center;
            background-size: cover;
            height: 90vh;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            border-radius: 0 0 30px 30px;
            color: white;
            position: relative;
        }

        .hero::after {
            content: '';
            position: absolute;
            top: 0; left: 0; width: 100%; height: 100%;
            background: rgba(0, 0, 0, 0.4);
            border-radius: 0 0 30px 30px;
        }

        .hero-content {
            position: relative;
            z-index: 1;
        }

        .hero h1 {
            font-size: 3.5rem;
            font-weight: 800;
            text-shadow: 2px 2px 6px rgba(0,0,0,0.6);
        }

        .hero p {
            font-size: 1.3rem;
            margin-top: 10px;
            font-weight: 500;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.5);
        }

        .section-title {
            color: #2d6a4f;
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .marquee {
            background-color: #0a4275;
            color: white;
            font-weight: bold;
            padding: 8px 0;
            font-size: 1rem;
        }

        .scroll-top-btn {
            position: fixed;
            bottom: 20px;
            right: 25px;
            display: none;
            background-color: #2d6a4f;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 50%;
            font-size: 18px;
            cursor: pointer;
            z-index: 1000;
        }
    </style>
</head>
<body>

<!-- Hero Section -->
<div class="hero">
    <div class="hero-content" data-aos="zoom-in">
        <h1>🌿 Welcome to EarthVoice</h1>
        <p>Your voice. Your ideas. Our planet's future.</p>
    </div>
</div>

<!-- Marquee Scroller -->
<marquee class="marquee" behavior="scroll" direction="left" scrollamount="6">
    🌱 Raise your voice for nature • ♻️ Blog green ideas that matter • 🌍 Join a growing eco-conscious community • 💡 Inspire, Educate & Act!
</marquee>

<!-- Main Content -->
<div class="container my-5">
    <h2 class="text-center section-title" data-aos="fade-up">About EarthVoice</h2>
    <p class="text-center text-muted" data-aos="fade-up">
        EarthVoice is a digital platform built with Spring Boot & JSP to empower people to speak for nature. Whether you're fighting pollution, sharing innovations, or educating others – your blog can be a change-maker.
    </p>

    <!-- Core Features -->
    <div class="row text-center mt-5">
        <div class="col-md-4 mb-4" data-aos="fade-right">
            <div class="card shadow border-0">
                <div class="card-body">
                    <h5 class="card-title">🌱 Every Opinion Matters</h5>
                    <p class="card-text">Raise your voice to protect the planet. Share your environmental concerns and ideas.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4" data-aos="zoom-in">
            <div class="card shadow border-0">
                <div class="card-body">
                    <h5 class="card-title">⚙️ Eco & Tech Together</h5>
                    <p class="card-text">Discuss sustainable technologies and green coding practices.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4" data-aos="fade-left">
            <div class="card shadow border-0">
                <div class="card-body">
                    <h5 class="card-title">🌍 United for Change</h5>
                    <p class="card-text">Join a green-minded community advocating for nature’s future.</p>
                </div>
            </div>
        </div>
    </div>

    <!-- What You Can Do -->
    <div class="my-5" data-aos="fade-up">
        <h3 class="text-center section-title">🌐 What You Can Do Here</h3>
        <ul class="list-group list-group-flush mx-auto" style="max-width: 700px;">
            <li class="list-group-item">📖 Read posts about sustainability, biodiversity, and pollution awareness.</li>
            <li class="list-group-item">📝 Create and share your eco-focused blogs.</li>
            <li class="list-group-item">💬 Comment and connect with like-minded people.</li>
            <li class="list-group-item">❤️ Like, bookmark, and support important stories.</li>
            <li class="list-group-item">🔐 Login or sign up to participate fully.</li>
        </ul>
    </div>

    <!-- Categories -->
    <div class="my-5" data-aos="fade-up">
        <h3 class="text-center section-title">📚 Popular Categories</h3>
        <div class="row text-center">
            <div class="col-sm-6 col-md-4 mb-3">🌾 <span class="badge bg-success">Sustainable Living</span></div>
            <div class="col-sm-6 col-md-4 mb-3">⚡ <span class="badge bg-info text-dark">Eco-Tech</span></div>
            <div class="col-sm-6 col-md-4 mb-3">📣 <span class="badge bg-warning text-dark">Activism</span></div>
            <div class="col-sm-6 col-md-4 mb-3">🐾 <span class="badge bg-primary">Biodiversity</span></div>
            <div class="col-sm-6 col-md-4 mb-3">🧪 <span class="badge bg-danger">Pollution</span></div>
            <div class="col-sm-6 col-md-4 mb-3">📷 <span class="badge bg-secondary">Green Media</span></div>
        </div>
    </div>
</div>

<!-- Scroll to Top Button -->
<button onclick="scrollToTop()" id="scrollTopBtn" class="scroll-top-btn">&#8679;</button>

<!-- Footer -->
<jsp:include page="footer.jsp" />

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
    AOS.init({ duration: 1000 });

    // Scroll top button
    const scrollBtn = document.getElementById("scrollTopBtn");
    window.onscroll = () => {
        scrollBtn.style.display = (window.scrollY > 100) ? "block" : "none";
    };
    function scrollToTop() {
        window.scrollTo({top: 0, behavior: 'smooth'});
    }
</script>

</body>
</html>

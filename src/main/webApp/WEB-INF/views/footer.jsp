<!-- Include this before </body> in your footer.jsp -->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link href="https://unpkg.com/aos@2.3.4/dist/aos.css" rel="stylesheet" />
<script src="https://unpkg.com/aos@2.3.4/dist/aos.js"></script>
<script>
    AOS.init();
</script>

<footer class="bg-dark text-white pt-5 pb-4 mt-5">
    <div class="container text-md-start text-center" data-aos="fade-up" data-aos-duration="1000">
        <div class="row">

            <!-- EarthVoice Brand -->
            <div class="col-lg-4 col-md-6 mb-4">
                <h5 class="text-success fw-bold">🌿 EarthVoice</h5>
                <p class="small">
                    Empowering eco-conscious voices since 2025. <br>
                    Share, Act, Inspire.
                </p>
                <p class="small mb-0">© 2025 EarthVoice. All rights reserved.</p>
            </div>

            <!-- Quick Links -->
            <div class="col-lg-4 col-md-6 mb-4">
                <h6 class="text-light fw-bold">Quick Links</h6>
                <ul class="list-unstyled">
                    <li><a href="${pageContext.request.contextPath}/home" class="text-info text-decoration-none">🏠 Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/about" class="text-info text-decoration-none">🌱 About</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact" class="text-info text-decoration-none">✉️ Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/posts" class="text-info text-decoration-none">📝 Posts</a></li>
                </ul>
            </div>

            <!-- Social Media -->
            <div class="col-lg-4 col-md-12 mb-4">
                <h6 class="text-light fw-bold">Connect with Us</h6>
                <div class="d-flex flex-column gap-2">
                    <a href="#" class="text-info text-decoration-none"><i class="bi bi-facebook me-1"></i> Facebook</a>
                    <a href="#" class="text-info text-decoration-none"><i class="bi bi-twitter-x me-1"></i> Twitter</a>
                    <a href="#" class="text-info text-decoration-none"><i class="bi bi-instagram me-1"></i> Instagram</a>
                    <a href="#" class="text-info text-decoration-none"><i class="bi bi-youtube me-1"></i> YouTube</a>
                </div>
            </div>

        </div>

        <!-- Back to Top -->
        <div class="text-center mt-4">
            <a href="#" class="text-light small text-decoration-none">
                ↑ Back to Top
            </a>
        </div>
    </div>
</footer>

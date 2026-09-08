<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contact – EarthVoice</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>

        body {
            background: linear-gradient(135deg, #ofa4af 0%, #afdde5 100%);
            font-family: 'Segoe UI', sans-serif;
            background-attachment: fixed;
            background-repeat: no-repeat;
            background-size: cover;
        }


        .contact-wrapper {
            max-width: 1100px;
            margin: 50px auto;
            padding: 30px;
        }

        .contact-left {
            background-color: #f8f9fa;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        .contact-form {
            background-color: #ffffff;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        h3 {
            color: #2d6a4f;
            font-weight: 600;
        }

        .form-label {
            font-weight: 500;
        }

        .error-message {
            color: red;
            font-size: 0.9rem;
        }

        .alert-success {
            font-weight: bold;
        }

        .bi {
            color: #2d6a4f;
        }
    </style>
</head>
<body>

<div class="container contact-wrapper">
    <div class="row gx-5 align-items-start">
        <!-- Left Contact Info -->
        <div class="col-md-5 contact-left">
            <h3>Contact Us</h3>
            <p class="mb-4">Reach out via the form — we’d love to hear from you. Whether feedback, questions, or support, we’re here for the green community.</p>
            <ul class="list-unstyled">
                <li><i class="bi bi-geo-alt-fill me-2"></i> 123 Eco Street, Green City</li>
                <li><i class="bi bi-telephone-fill me-2"></i> +91 12345 67890</li>
                <li><i class="bi bi-envelope-fill me-2"></i> hello@earthvoice.com</li>
            </ul>
        </div>

        <!-- Right Contact Form -->
        <div class="col-md-7">
            <div class="contact-form">
                <h4 class="mb-4">Send a Message</h4>
                <form:form modelAttribute="contact" method="post" action="/submitContact" onsubmit="return validateContactForm()">
                    <div class="mb-3">
                        <label class="form-label">Name</label>
                        <form:input path="name" cssClass="form-control" />
                        <form:errors path="name" cssClass="error-message" />
                        <div id="nameError" class="error-message"></div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <form:input path="email" cssClass="form-control" />
                        <form:errors path="email" cssClass="error-message" />
                        <div id="emailError" class="error-message"></div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Message</label>
                        <form:textarea path="message" cssClass="form-control" rows="5" />
                        <form:errors path="message" cssClass="error-message" />
                        <div id="messageError" class="error-message"></div>
                    </div>

                    <div class="text-end">
                        <button type="submit" class="btn btn-success">Send Message</button>
                    </div>
                </form:form>

                <c:if test="${not empty message}">
                    <div class="alert alert-success mt-3">${message}</div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- JS Validation -->
<script>
    function validateContactForm() {
        let valid = true;

        document.getElementById("nameError").innerText = "";
        document.getElementById("emailError").innerText = "";
        document.getElementById("messageError").innerText = "";

        const name = document.querySelector("[name='name']").value.trim();
        const email = document.querySelector("[name='email']").value.trim();
        const message = document.querySelector("[name='message']").value.trim();

        const emailPattern = /^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/;

        if (name === "") {
            document.getElementById("nameError").innerText = "Please enter your name.";
            valid = false;
        }

        if (email === "") {
            document.getElementById("emailError").innerText = "Please enter your email.";
            valid = false;
        } else if (!emailPattern.test(email)) {
            document.getElementById("emailError").innerText = "Please enter a valid email.";
            valid = false;
        }

        if (message.length < 10) {
            document.getElementById("messageError").innerText = "Message must be at least 10 characters.";
            valid = false;
        }

        return valid;
    }
</script>

<%@ include file="footer.jsp" %>
</body>
</html>

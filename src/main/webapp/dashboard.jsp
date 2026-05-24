<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.itservices.service.ServiceCatalogService" %>
<%@ page import="com.itservices.model.Service" %>
<%@ page import="com.itservices.model.Product" %>
<%@ page import="java.util.List" %>

<%
    // Check if user is logged in
    if (session.getAttribute("userEmail") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get services and products
    ServiceCatalogService catalogService = ServiceCatalogService.getInstance();
    List<Service> services = catalogService.getAllServices();
    List<Product> allProducts = catalogService.getAllProducts();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - IT Services Portal</title>
    <link rel="stylesheet" href="css/style.css">
    <script>
        function showTab(tabName) {
            // Hide all tabs
            var tabs = document.querySelectorAll('.tab-content');
            tabs.forEach(function(tab) {
                tab.style.display = 'none';
            });

            // Remove active class from all buttons
            var buttons = document.querySelectorAll('.tab-button');
            buttons.forEach(function(btn) {
                btn.classList.remove('active');
            });

            // Show selected tab
            document.getElementById(tabName).style.display = 'block';
            event.target.classList.add('active');
        }
    </script>
</head>
<body>
    <div class="navbar">
        <div class="navbar-container">
            <h1 class="navbar-title">IT Services Portal</h1>
            <div class="navbar-menu">
                <span class="welcome-msg">Welcome, <%= session.getAttribute("userFullName") %></span>
                <a href="logout" class="btn btn-logout">Logout</a>
            </div>
        </div>
    </div>

    <div class="dashboard-container">
        <div class="tabs">
            <button class="tab-button active" onclick="showTab('company-tab')">Company Info</button>
            <button class="tab-button" onclick="showTab('services-tab')">Services</button>
            <button class="tab-button" onclick="showTab('products-tab')">Products</button>
        </div>

        <div id="company-tab" class="tab-content">
            <div class="company-info">
                <h2>About Our Company</h2>
                <p>Welcome to <strong>IT Services Portal</strong>, your trusted partner in digital transformation and technology solutions.</p>

                <h3>Our Mission</h3>
                <p>To empower businesses with cutting-edge IT services and solutions that drive innovation, efficiency, and growth.</p>

                <h3>Our Vision</h3>
                <p>To be the leading provider of comprehensive IT services, recognized for excellence, innovation, and customer satisfaction.</p>

                <h3>Key Statistics</h3>
                <div class="stats-grid">
                    <div class="stat-box">
                        <div class="stat-number"><%= ServiceCatalogService.getInstance().getServiceCount() %></div>
                        <div class="stat-label">Services</div>
                    </div>
                    <div class="stat-box">
                        <div class="stat-number"><%= ServiceCatalogService.getInstance().getProductCount() %></div>
                        <div class="stat-label">Products</div>
                    </div>
                    <div class="stat-box">
                        <div class="stat-number">500+</div>
                        <div class="stat-label">Happy Clients</div>
                    </div>
                    <div class="stat-box">
                        <div class="stat-number">15+</div>
                        <div class="stat-label">Years Experience</div>
                    </div>
                </div>

                <h3>Contact Us</h3>
                <p>
                    Email: <strong>info@itservicesportal.com</strong><br>
                    Phone: <strong>+1 (555) 123-4567</strong><br>
                    Address: <strong>123 Tech Street, Silicon Valley, CA 94025</strong>
                </p>
            </div>
        </div>

        <div id="services-tab" class="tab-content" style="display: none;">
            <div class="services-grid">
                <h2>Our Services</h2>
                <%
                    for (Service service : services) {
                %>
                <div class="service-card">
                    <h3><%= service.getName() %></h3>
                    <p><%= service.getDescription() %></p>
                    <div class="service-details">
                        <strong>Products:</strong> <%= service.getProducts().size() %>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>

        <div id="products-tab" class="tab-content" style="display: none;">
            <div class="products-section">
                <h2>Our Products</h2>
                <div class="products-grid">
                    <%
                        for (Product product : allProducts) {
                    %>
                    <div class="product-card">
                        <div class="product-image">
                            <img src="<%= product.getImagePath() %>" alt="<%= product.getName() %>" onerror="this.src='/images/placeholder.jpg'">
                        </div>
                        <div class="product-info">
                            <h4><%= product.getName() %></h4>
                            <p class="product-category"><%= product.getCategory() %></p>
                            <p class="product-description"><%= product.getDescription() %></p>
                            <div class="product-footer">
                                <span class="product-price"><%= product.getPrice() %></span>
                                <a href="<%= product.getProductUrl() %>" target="_blank" class="btn btn-small">Learn More</a>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2026 IT Services Portal. All rights reserved.</p>
    </footer>
</body>
</html>

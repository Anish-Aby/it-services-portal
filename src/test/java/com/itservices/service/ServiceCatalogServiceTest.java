package com.itservices.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itservices.model.Product;
import com.itservices.model.Service;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ServiceCatalogService class.
 */
@DisplayName("ServiceCatalogService Tests")
class ServiceCatalogServiceTest {

    private final ServiceCatalogService serviceCatalogService = ServiceCatalogService.getInstance();

    @Test
    @DisplayName("Should get singleton instance")
    void testGetInstance() {
        ServiceCatalogService instance1 = ServiceCatalogService.getInstance();
        ServiceCatalogService instance2 = ServiceCatalogService.getInstance();
        assertEquals(instance1, instance2, "Should return the same instance");
    }

    @Test
    @DisplayName("Should get all services")
    void testGetAllServices() {
        List<Service> services = serviceCatalogService.getAllServices();
        assertNotNull(services, "Services list should not be null");
        assertTrue(services.size() > 0, "Should have at least one service");
    }

    @Test
    @DisplayName("Should get service by ID")
    void testGetServiceById() {
        List<Service> allServices = serviceCatalogService.getAllServices();
        String serviceId = allServices.get(0).getServiceId();

        Service service = serviceCatalogService.getServiceById(serviceId);
        assertNotNull(service, "Service should be found");
        assertEquals(serviceId, service.getServiceId(), "Service ID should match");
    }

    @Test
    @DisplayName("Should get all products")
    void testGetAllProducts() {
        List<Product> products = serviceCatalogService.getAllProducts();
        assertNotNull(products, "Products list should not be null");
        assertTrue(products.size() > 0, "Should have at least one product");
    }

    @Test
    @DisplayName("Should get products by category")
    void testGetProductsByCategory() {
        String category = "Cloud Computing";
        List<Product> products = serviceCatalogService.getProductsByCategory(category);
        assertNotNull(products, "Products list should not be null");
        assertTrue(products.size() > 0, "Should have products in Cloud Computing category");

        // Verify all products are in the correct category
        products.forEach(product ->
                assertEquals(category, product.getCategory(), "Product should be in correct category")
        );
    }

    @Test
    @DisplayName("Should get correct service count")
    void testGetServiceCount() {
        int count = serviceCatalogService.getServiceCount();
        assertTrue(count > 0, "Should have at least one service");
        assertEquals(serviceCatalogService.getAllServices().size(), count, "Count should match actual services");
    }

    @Test
    @DisplayName("Should get correct product count")
    void testGetProductCount() {
        int count = serviceCatalogService.getProductCount();
        assertTrue(count > 0, "Should have at least one product");
        assertEquals(serviceCatalogService.getAllProducts().size(), count, "Count should match actual products");
    }

    @Test
    @DisplayName("Should verify all services have products")
    void testServicesHaveProducts() {
        List<Service> services = serviceCatalogService.getAllServices();
        services.forEach(service ->
                assertTrue(service.getProducts().size() > 0, "Each service should have at least one product")
        );
    }

    @Test
    @DisplayName("Should verify product details are populated")
    void testProductDetailsPopulated() {
        List<Product> products = serviceCatalogService.getAllProducts();
        products.forEach(product -> {
            assertNotNull(product.getProductId(), "Product ID should not be null");
            assertNotNull(product.getName(), "Product name should not be null");
            assertNotNull(product.getDescription(), "Product description should not be null");
            assertNotNull(product.getCategory(), "Product category should not be null");
            assertTrue(product.getProductUrl() != null || product.getProductUrl().length() > 0,
                    "Product URL should not be empty");
        });
    }

    @Test
    @DisplayName("Should have specific cloud computing products")
    void testCloudComputingProducts() {
        List<Product> cloudProducts = serviceCatalogService.getProductsByCategory("Cloud Computing");
        assertTrue(cloudProducts.size() >= 3, "Should have at least 3 cloud products");
    }

    @Test
    @DisplayName("Should have specific cybersecurity products")
    void testCybersecurityProducts() {
        List<Product> securityProducts = serviceCatalogService.getProductsByCategory("Cybersecurity");
        assertTrue(securityProducts.size() >= 3, "Should have at least 3 security products");
    }

    @Test
    @DisplayName("Should have specific data analytics products")
    void testDataAnalyticsProducts() {
        List<Product> analyticsProducts = serviceCatalogService.getProductsByCategory("Data Analytics");
        assertTrue(analyticsProducts.size() >= 3, "Should have at least 3 analytics products");
    }

    @Test
    @DisplayName("Should have 5 different services")
    void testNumberOfServices() {
        List<Service> services = serviceCatalogService.getAllServices();
        assertEquals(5, services.size(), "Should have exactly 5 services");
    }

    @Test
    @DisplayName("Should have 15 total products")
    void testNumberOfProducts() {
        List<Product> products = serviceCatalogService.getAllProducts();
        assertEquals(15, products.size(), "Should have exactly 15 products (3 per service)");
    }
}

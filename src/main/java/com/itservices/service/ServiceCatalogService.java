package com.itservices.service;

import com.itservices.model.Product;
import com.itservices.model.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing IT services and products.
 */
public class ServiceCatalogService {

    private static final ServiceCatalogService instance = new ServiceCatalogService();
    private final List<Service> services;

    /**
     * Private constructor to prevent instantiation.
     */
    private ServiceCatalogService() {
        this.services = new ArrayList<>();
        initializeServices();
    }

    /**
     * Gets the singleton instance.
     *
     * @return the ServiceCatalogService instance
     */
    public static ServiceCatalogService getInstance() {
        return instance;
    }

    /**
     * Initializes the service catalog with sample services and products.
     */
    private void initializeServices() {
        // Cloud Computing Service
        Service cloudService = new Service("svc001", "Cloud Computing",
                "Scalable and reliable cloud infrastructure solutions");
        cloudService.addProduct(new Product("prod001", "AWS Managed Services",
                "Comprehensive AWS cloud management and optimization",
                "Cloud Computing", "/images/aws.jpg", "https://aws.amazon.com", "$2,500/month"));
        cloudService.addProduct(new Product("prod002", "Azure Solutions",
                "Microsoft Azure cloud platform and services",
                "Cloud Computing", "/images/azure.jpg", "https://azure.microsoft.com", "$2,200/month"));
        cloudService.addProduct(new Product("prod003", "Google Cloud Services",
                "Google Cloud Platform infrastructure and tools",
                "Cloud Computing", "/images/gcp.jpg", "https://cloud.google.com", "$2,000/month"));
        services.add(cloudService);

        // Cybersecurity Service
        Service securityService = new Service("svc002", "Cybersecurity",
                "Enterprise-grade security and threat protection");
        securityService.addProduct(new Product("prod004", "SIEM Solution",
                "Security Information and Event Management system",
                "Cybersecurity", "/images/siem.jpg", "https://www.splunk.com", "$5,000/month"));
        securityService.addProduct(new Product("prod005", "Endpoint Protection",
                "Advanced endpoint security and malware protection",
                "Cybersecurity", "/images/endpoint.jpg", "https://www.crowdstrike.com", "$1,500/month"));
        securityService.addProduct(new Product("prod006", "Network Security",
                "Firewall and network intrusion prevention",
                "Cybersecurity", "/images/firewall.jpg", "https://www.paloaltonetworks.com", "$3,000/month"));
        services.add(securityService);

        // Data Analytics Service
        Service analyticsService = new Service("svc003", "Data Analytics",
                "Business intelligence and data analytics solutions");
        analyticsService.addProduct(new Product("prod007", "BI Dashboard",
                "Business intelligence dashboards and reporting",
                "Data Analytics", "/images/bi.jpg", "https://www.tableau.com", "$1,800/month"));
        analyticsService.addProduct(new Product("prod008", "Big Data Platform",
                "Large-scale data processing and storage",
                "Data Analytics", "/images/bigdata.jpg", "https://www.databricks.com", "$4,000/month"));
        analyticsService.addProduct(new Product("prod009", "Data Warehouse",
                "Centralized data storage and management",
                "Data Analytics", "/images/warehouse.jpg", "https://www.snowflake.com", "$3,500/month"));
        services.add(analyticsService);

        // DevOps & CI/CD Service
        Service devopsService = new Service("svc004", "DevOps & CI/CD",
                "Continuous integration and deployment solutions");
        devopsService.addProduct(new Product("prod010", "Jenkins Pipeline",
                "Automated CI/CD pipeline setup and management",
                "DevOps", "/images/jenkins.jpg", "https://www.jenkins.io", "$800/month"));
        devopsService.addProduct(new Product("prod011", "Kubernetes Cluster",
                "Container orchestration and management",
                "DevOps", "/images/k8s.jpg", "https://kubernetes.io", "$2,000/month"));
        devopsService.addProduct(new Product("prod012", "GitLab DevOps",
                "Complete DevOps platform with Git integration",
                "DevOps", "/images/gitlab.jpg", "https://about.gitlab.com", "$1,200/month"));
        services.add(devopsService);

        // Application Development Service
        Service appDevService = new Service("svc005", "Application Development",
                "Custom software development and maintenance");
        appDevService.addProduct(new Product("prod013", "Web Development",
                "Full-stack web application development",
                "Application Development", "/images/web.jpg", "https://www.example.com", "Custom Pricing"));
        appDevService.addProduct(new Product("prod014", "Mobile Development",
                "Native and cross-platform mobile applications",
                "Application Development", "/images/mobile.jpg", "https://www.example.com", "Custom Pricing"));
        appDevService.addProduct(new Product("prod015", "API Development",
                "RESTful and GraphQL API development",
                "Application Development", "/images/api.jpg", "https://www.example.com", "Custom Pricing"));
        services.add(appDevService);
    }

    /**
     * Gets all services.
     *
     * @return list of all services
     */
    public List<Service> getAllServices() {
        return new ArrayList<>(services);
    }

    /**
     * Gets a service by ID.
     *
     * @param serviceId the service ID
     * @return the Service if found, null otherwise
     */
    public Service getServiceById(String serviceId) {
        return services.stream()
                .filter(s -> s.getServiceId().equals(serviceId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets all products across all services.
     *
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        services.forEach(service -> allProducts.addAll(service.getProducts()));
        return allProducts;
    }

    /**
     * Gets products by category.
     *
     * @param category the product category
     * @return list of products in the category
     */
    public List<Product> getProductsByCategory(String category) {
        List<Product> categoryProducts = new ArrayList<>();
        services.forEach(service ->
                service.getProducts().stream()
                        .filter(p -> p.getCategory().equals(category))
                        .forEach(categoryProducts::add)
        );
        return categoryProducts;
    }

    /**
     * Gets the number of services.
     *
     * @return the total number of services
     */
    public int getServiceCount() {
        return services.size();
    }

    /**
     * Gets the total number of products.
     *
     * @return the total number of products
     */
    public int getProductCount() {
        return getAllProducts().size();
    }
}

package com.itservices.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Service model class representing an IT service offered by the company.
 */
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    private String serviceId;
    private String name;
    private String description;
    private List<Product> products;

    /**
     * Default constructor.
     */
    public Service() {
        this.products = new ArrayList<>();
    }

    /**
     * Constructor with service details.
     *
     * @param serviceId unique service identifier
     * @param name service name
     * @param description service description
     */
    public Service(String serviceId, String name, String description) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.products = new ArrayList<>();
    }

    // Getters and Setters

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to the service's product list.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId='" + serviceId + '\'' +
                ", name='" + name + '\'' +
                ", productCount=" + products.size() +
                '}';
    }
}

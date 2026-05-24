package com.itservices.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Product model class representing an IT service product.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productId;
    private String name;
    private String description;
    private String category;
    private String imagePath;
    private String productUrl;
    private String price;

    /**
     * Default constructor.
     */
    public Product() {
    }

    /**
     * Constructor with product details.
     *
     * @param productId unique product identifier
     * @param name product name
     * @param description product description
     * @param category product category
     * @param imagePath path to product image
     * @param productUrl URL to product details
     * @param price product price
     */
    public Product(String productId, String name, String description,
                   String category, String imagePath, String productUrl, String price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.imagePath = imagePath;
        this.productUrl = productUrl;
        this.price = price;
    }

    // Getters and Setters

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

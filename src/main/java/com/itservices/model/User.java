package com.itservices.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User model class representing a registered user in the system.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String email;
    private String password;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor with user details.
     *
     * @param email the user's email
     * @param password the user's hashed password
     * @param fullName the user's full name
     */
    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}

package com.itservices.service;

import com.itservices.model.User;
import com.itservices.util.FileStorageUtil;
import com.itservices.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service class for user authentication and registration.
 */
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    /**
     * Registers a new user.
     *
     * @param email the user's email
     * @param password the user's password
     * @param fullName the user's full name
     * @return true if registration is successful, false otherwise
     */
    public boolean registerUser(String email, String password, String fullName) {
        // Validate inputs
        if (email == null || email.trim().isEmpty()) {
            logger.warn("Registration failed: email is empty");
            return false;
        }

        if (password == null || !PasswordUtil.isPasswordStrong(password)) {
            logger.warn("Registration failed: password is not strong enough");
            return false;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            logger.warn("Registration failed: full name is empty");
            return false;
        }

        // Check if user already exists
        if (FileStorageUtil.userExists(email)) {
            logger.warn("Registration failed: user with email {} already exists", email);
            return false;
        }

        // Hash password and create user
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(email, hashedPassword, fullName);

        boolean saved = FileStorageUtil.saveUser(user);
        if (saved) {
            logger.info("User registered successfully: {}", email);
        }

        return saved;
    }

    /**
     * Authenticates a user with email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return the User object if authentication is successful, null otherwise
     */
    public User authenticateUser(String email, String password) {
        // Validate inputs
        if (email == null || email.trim().isEmpty()) {
            logger.warn("Authentication failed: email is empty");
            return null;
        }

        if (password == null || password.isEmpty()) {
            logger.warn("Authentication failed: password is empty");
            return null;
        }

        // Get user from storage
        User user = FileStorageUtil.getUserByEmail(email);
        if (user == null) {
            logger.warn("Authentication failed: user not found with email {}", email);
            return null;
        }

        // Verify password
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            logger.warn("Authentication failed: invalid password for user {}", email);
            return null;
        }

        // Update last login
        FileStorageUtil.updateLastLogin(email);
        logger.info("User authenticated successfully: {}", email);

        return user;
    }

    /**
     * Gets a user by email.
     *
     * @param email the user's email
     * @return the User object if found, null otherwise
     */
    public User getUserByEmail(String email) {
        return FileStorageUtil.getUserByEmail(email);
    }

    /**
     * Validates email format.
     *
     * @param email the email to validate
     * @return true if email format is valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Simple email validation
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

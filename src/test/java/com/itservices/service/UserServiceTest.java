package com.itservices.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itservices.model.User;
import com.itservices.util.FileStorageUtil;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for UserService class.
 */
@DisplayName("UserService Tests")
class UserServiceTest {

    private UserService userService;
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "testPass123";
    private static final String TEST_FULLNAME = "Test User";

    @BeforeEach
    void setUp() {
        userService = new UserService();
        // Clean up test data before each test
        cleanTestData();
    }

    @AfterEach
    void tearDown() {
        cleanTestData();
    }

    private void cleanTestData() {
        File usersFile = new File("data/users.json");
        if (usersFile.exists()) {
            usersFile.delete();
        }
    }

    @Test
    @DisplayName("Should register a new user successfully")
    void testRegisterUserSuccess() {
        boolean result = userService.registerUser(TEST_EMAIL, TEST_PASSWORD, TEST_FULLNAME);
        assertTrue(result, "User registration should succeed");

        User user = FileStorageUtil.getUserByEmail(TEST_EMAIL);
        assertNotNull(user, "User should exist after registration");
    }

    @Test
    @DisplayName("Should fail to register user with empty email")
    void testRegisterUserWithEmptyEmail() {
        boolean result = userService.registerUser("", TEST_PASSWORD, TEST_FULLNAME);
        assertFalse(result, "Registration should fail with empty email");
    }

    @Test
    @DisplayName("Should fail to register user with weak password")
    void testRegisterUserWithWeakPassword() {
        boolean result = userService.registerUser(TEST_EMAIL, "weak", TEST_FULLNAME);
        assertFalse(result, "Registration should fail with weak password");
    }

    @Test
    @DisplayName("Should fail to register user with empty name")
    void testRegisterUserWithEmptyName() {
        boolean result = userService.registerUser(TEST_EMAIL, TEST_PASSWORD, "");
        assertFalse(result, "Registration should fail with empty name");
    }

    @Test
    @DisplayName("Should fail to register duplicate email")
    void testRegisterDuplicateEmail() {
        userService.registerUser(TEST_EMAIL, TEST_PASSWORD, TEST_FULLNAME);
        boolean result = userService.registerUser(TEST_EMAIL, "different123", "Different User");
        assertFalse(result, "Registration should fail for duplicate email");
    }

    @Test
    @DisplayName("Should authenticate user successfully")
    void testAuthenticateUserSuccess() {
        userService.registerUser(TEST_EMAIL, TEST_PASSWORD, TEST_FULLNAME);
        User user = userService.authenticateUser(TEST_EMAIL, TEST_PASSWORD);
        assertNotNull(user, "User authentication should succeed");
        assertTrue(user.getEmail().equals(TEST_EMAIL), "Authenticated user should match");
    }

    @Test
    @DisplayName("Should fail authentication with wrong password")
    void testAuthenticateUserWithWrongPassword() {
        userService.registerUser(TEST_EMAIL, TEST_PASSWORD, TEST_FULLNAME);
        User user = userService.authenticateUser(TEST_EMAIL, "wrongPassword");
        assertNull(user, "Authentication should fail with wrong password");
    }

    @Test
    @DisplayName("Should fail authentication with non-existent email")
    void testAuthenticateUserNonExistent() {
        User user = userService.authenticateUser("nonexistent@example.com", TEST_PASSWORD);
        assertNull(user, "Authentication should fail for non-existent user");
    }

    @Test
    @DisplayName("Should fail authentication with empty email")
    void testAuthenticateUserWithEmptyEmail() {
        User user = userService.authenticateUser("", TEST_PASSWORD);
        assertNull(user, "Authentication should fail with empty email");
    }

    @Test
    @DisplayName("Should fail authentication with empty password")
    void testAuthenticateUserWithEmptyPassword() {
        userService.registerUser(TEST_EMAIL, TEST_PASSWORD, TEST_FULLNAME);
        User user = userService.authenticateUser(TEST_EMAIL, "");
        assertNull(user, "Authentication should fail with empty password");
    }

    @Test
    @DisplayName("Should validate valid email format")
    void testValidEmailFormat() {
        assertTrue(userService.isValidEmail("test@example.com"), "Valid email should return true");
        assertTrue(userService.isValidEmail("user.name@domain.co.uk"), "Valid email should return true");
        assertTrue(userService.isValidEmail("user+tag@example.com"), "Valid email should return true");
    }

    @Test
    @DisplayName("Should reject invalid email format")
    void testInvalidEmailFormat() {
        assertFalse(userService.isValidEmail("invalid.email"), "Invalid email should return false");
        assertFalse(userService.isValidEmail("@example.com"), "Invalid email should return false");
        assertFalse(userService.isValidEmail("user@"), "Invalid email should return false");
        assertFalse(userService.isValidEmail(""), "Empty email should return false");
        assertFalse(userService.isValidEmail(null), "Null email should return false");
    }

    @Test
    @DisplayName("Should retrieve user by email")
    void testGetUserByEmail() {
        userService.registerUser(TEST_EMAIL, TEST_PASSWORD, TEST_FULLNAME);
        User user = userService.getUserByEmail(TEST_EMAIL);
        assertNotNull(user, "User should be retrieved");
        assertTrue(user.getEmail().equals(TEST_EMAIL), "Retrieved user email should match");
        assertTrue(user.getFullName().equals(TEST_FULLNAME), "Retrieved user name should match");
    }

    @Test
    @DisplayName("Should return null for non-existent user")
    void testGetNonExistentUser() {
        User user = userService.getUserByEmail("nonexistent@example.com");
        assertNull(user, "Non-existent user should return null");
    }
}

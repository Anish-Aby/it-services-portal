package com.itservices.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PasswordUtil class.
 */
@DisplayName("PasswordUtil Tests")
class PasswordUtilTest {

    @Test
    @DisplayName("Should hash password successfully")
    void testHashPassword() {
        String password = "testPassword123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        assertTrue(hashedPassword.startsWith("$2"), "Hashed password should be BCrypt format");
    }

    @Test
    @DisplayName("Should verify correct password")
    void testVerifyPasswordSuccess() {
        String password = "testPassword123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        assertTrue(PasswordUtil.verifyPassword(password, hashedPassword),
                "Password verification should succeed for correct password");
    }

    @Test
    @DisplayName("Should reject incorrect password")
    void testVerifyPasswordFailure() {
        String password = "testPassword123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        assertFalse(PasswordUtil.verifyPassword("wrongPassword", hashedPassword),
                "Password verification should fail for incorrect password");
    }

    @Test
    @DisplayName("Should reject null password")
    void testVerifyNullPassword() {
        String hashedPassword = PasswordUtil.hashPassword("password123");
        assertFalse(PasswordUtil.verifyPassword(null, hashedPassword),
                "Password verification should fail for null password");
    }

    @Test
    @DisplayName("Should reject null hash")
    void testVerifyNullHash() {
        assertFalse(PasswordUtil.verifyPassword("password123", null),
                "Password verification should fail for null hash");
    }

    @Test
    @DisplayName("Should validate strong password")
    void testValidateStrongPassword() {
        assertTrue(PasswordUtil.isPasswordStrong("validPass123"),
                "Password with 6+ characters should be considered strong");
        assertTrue(PasswordUtil.isPasswordStrong("longerPassword"),
                "Password with 6+ characters should be considered strong");
    }

    @Test
    @DisplayName("Should reject weak password")
    void testValidateWeakPassword() {
        assertFalse(PasswordUtil.isPasswordStrong("weak"),
                "Password with less than 6 characters should be weak");
        assertFalse(PasswordUtil.isPasswordStrong(""),
                "Empty password should be weak");
    }

    @Test
    @DisplayName("Should reject null password in strength check")
    void testValidateNullPassword() {
        assertFalse(PasswordUtil.isPasswordStrong(null),
                "Null password should be weak");
    }

    @Test
    @DisplayName("Should produce different hashes for same password")
    void testHashedPasswordsAreUnique() {
        String password = "testPassword123";
        String hash1 = PasswordUtil.hashPassword(password);
        String hash2 = PasswordUtil.hashPassword(password);
        assertFalse(hash1.equals(hash2),
                "Two hashes of the same password should be different (due to salt)");
    }

    @Test
    @DisplayName("Should verify both hashes of same password")
    void testBothHashesVerifyCorrectly() {
        String password = "testPassword123";
        String hash1 = PasswordUtil.hashPassword(password);
        String hash2 = PasswordUtil.hashPassword(password);
        assertTrue(PasswordUtil.verifyPassword(password, hash1),
                "First hash should verify correctly");
        assertTrue(PasswordUtil.verifyPassword(password, hash2),
                "Second hash should verify correctly");
    }
}

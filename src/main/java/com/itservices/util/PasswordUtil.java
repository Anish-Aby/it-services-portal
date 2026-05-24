package com.itservices.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt.
 */
public class PasswordUtil {

    private static final int LOG_ROUNDS = 12;

    /**
     * Hashes a password using BCrypt.
     *
     * @param password the plain text password
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Verifies a password against its hash.
     *
     * @param password the plain text password
     * @param hash the hashed password
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String password, String hash) {
        if (password == null || hash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Validates password strength.
     * Password must be at least 6 characters long.
     *
     * @param password the password to validate
     * @return true if password is strong enough, false otherwise
     */
    public static boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 6;
    }
}

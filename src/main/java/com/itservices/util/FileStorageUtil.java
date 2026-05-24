package com.itservices.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.itservices.model.User;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for storing and retrieving users from file storage.
 */
public class FileStorageUtil {

    private static final Logger logger = LogManager.getLogger(FileStorageUtil.class);
    private static final String STORAGE_DIR = "data";
    private static final String USERS_FILE = "data/users.json";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    static {
        // Create data directory if it doesn't exist
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            logger.info("Created data storage directory: " + STORAGE_DIR);
        }
    }

    /**
     * Gets all users from storage.
     *
     * @return list of all users
     */
    public static List<User> getAllUsers() {
        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) {
                logger.info("Users file does not exist, returning empty list");
                return new ArrayList<>();
            }

            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                List<User> users = gson.fromJson(reader, listType);
                return users != null ? users : new ArrayList<>();
            }
        } catch (IOException e) {
            logger.error("Error reading users from file", e);
            return new ArrayList<>();
        }
    }

    /**
     * Saves a new user to storage.
     *
     * @param user the user to save
     * @return true if successful, false otherwise
     */
    public static boolean saveUser(User user) {
        try {
            List<User> users = getAllUsers();
            user.setUserId(UUID.randomUUID().toString());
            user.setCreatedAt(LocalDateTime.now());
            users.add(user);

            try (FileWriter writer = new FileWriter(USERS_FILE)) {
                gson.toJson(users, writer);
                logger.info("User saved successfully: " + user.getEmail());
                return true;
            }
        } catch (IOException e) {
            logger.error("Error saving user to file", e);
            return false;
        }
    }

    /**
     * Gets a user by email.
     *
     * @param email the user's email
     * @return the user if found, null otherwise
     */
    public static User getUserByEmail(String email) {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates the last login timestamp for a user.
     *
     * @param email the user's email
     * @return true if successful, false otherwise
     */
    public static boolean updateLastLogin(String email) {
        try {
            List<User> users = getAllUsers();
            boolean found = false;

            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    user.setLastLogin(LocalDateTime.now());
                    found = true;
                    break;
                }
            }

            if (found) {
                try (FileWriter writer = new FileWriter(USERS_FILE)) {
                    gson.toJson(users, writer);
                    logger.info("Updated last login for user: " + email);
                    return true;
                }
            }
        } catch (IOException e) {
            logger.error("Error updating last login for user: " + email, e);
        }

        return false;
    }

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    public static boolean userExists(String email) {
        return getUserByEmail(email) != null;
    }
}

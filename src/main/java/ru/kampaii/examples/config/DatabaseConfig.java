package ru.kampaii.examples.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {

            // Load the properties file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {

        return properties.getProperty("database.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("database.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("database.password");
    }
}

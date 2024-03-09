package ru.kampaii.examples.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ClassLoader.getSystemResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties from "
                        + new File("").getAbsolutePath());
                System.exit(1);
            }
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

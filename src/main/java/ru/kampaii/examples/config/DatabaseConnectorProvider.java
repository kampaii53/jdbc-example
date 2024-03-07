package ru.kampaii.examples.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnectorProvider {

    public static Connection connect() throws SQLException {
        File file = new File("C://properties.txt");
        try {
            Scanner scanner = new Scanner(file);
            return DriverManager.getConnection(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

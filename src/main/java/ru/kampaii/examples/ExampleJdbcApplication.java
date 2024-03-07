package ru.kampaii.examples;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class ExampleJdbcApplication {

    public static void main(String[] args) {
        File file = new File("C://script.txt");
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement(); Scanner scanner = new Scanner(file)) {
            System.out.println("Connected succesfully");
            String script = "";
            while (scanner.hasNext()) {
                script = scanner.nextLine() + "\n";
            }
            var result = statement.executeUpdate(script);
            System.out.println("result is " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

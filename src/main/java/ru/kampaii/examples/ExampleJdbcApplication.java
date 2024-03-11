package ru.kampaii.examples;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

public class ExampleJdbcApplication {


    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            InputStream input = ClassLoader.getSystemResourceAsStream("script.sql");
            Scanner scanner= new Scanner(input);
            System.out.println("Connected succesfully");
            var result= "";
            while (scanner.hasNext()) {
                result += statement.executeUpdate(scanner.nextLine());
            }
            System.out.println("result is " + result);

        } catch (SQLException e) {
            e.printStackTrace();

        }


    }
}

package ru.kampaii.examples;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class ExampleJdbcApplication {

    public static void main(String[] args) {
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement()) {
            System.out.println("Connected succesfully");

            var result = statement.executeUpdate("create table if not exists TEST(\n" +
                    "    id BIGINT PRIMARY KEY,\n" +
                    "    name varchar(255) NOT NULL\n" +
                    ");");

            System.out.println("result is " + result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

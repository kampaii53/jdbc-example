package ru.kampaii.examples.domain.idGenerators;


import java.sql.Connection;
import java.sql.SQLException;

public class UsersIdGeneratorImpl implements IdGenerator<Integer> {

    Connection connection;

    public UsersIdGeneratorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer makeNewId() {
        int maxNum = 0;
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT MAX(id) FROM users");
            if (results.next()) {
                maxNum = Integer.valueOf(String.valueOf(results.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (maxNum + 1);

    }

}

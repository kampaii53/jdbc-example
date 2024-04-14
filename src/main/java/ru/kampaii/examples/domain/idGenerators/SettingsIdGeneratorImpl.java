package ru.kampaii.examples.domain.idGenerators;

import java.sql.Connection;
import java.sql.SQLException;

public class SettingsIdGeneratorImpl implements IdGenerator<Integer> {
    Connection connection;

    public SettingsIdGeneratorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer makeNewId() {
        int maxNum = 0;
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT MAX(id) FROM settings");
            if (results.next()) {
                maxNum = Integer.valueOf(String.valueOf(results.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (maxNum + 1);

    }
}

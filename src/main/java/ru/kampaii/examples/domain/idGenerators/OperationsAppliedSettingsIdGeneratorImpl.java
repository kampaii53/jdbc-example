package ru.kampaii.examples.domain.idGenerators;

import java.sql.Connection;
import java.sql.SQLException;

public class OperationsAppliedSettingsIdGeneratorImpl implements IdGenerator<Integer> {

    Connection connection;

    public OperationsAppliedSettingsIdGeneratorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer makeNewId() {
        int maxNum = 0;
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT MAX(operation_id) FROM operations_applied_settings");
            if (results.next()) {
                maxNum = Integer.valueOf(String.valueOf(results.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (maxNum + 1);

    }
}

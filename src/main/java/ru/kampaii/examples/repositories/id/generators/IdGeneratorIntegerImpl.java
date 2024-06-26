package ru.kampaii.examples.repositories.id.generators;

import java.sql.Connection;
import java.sql.SQLException;

public class IdGeneratorIntegerImpl extends IdGenerator<Integer> {
    public IdGeneratorIntegerImpl(Connection connection, String tableName, String primaryKey, Integer numOfPrimaryKey) {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.numOfPrimaryKey = numOfPrimaryKey;
    }

    @Override
    public Integer makeNewId() {
        int maxNum = 0;
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT MAX(" + primaryKey + ") FROM " + tableName);
            if (results.next()) {
                maxNum = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (maxNum + 1);
    }
}

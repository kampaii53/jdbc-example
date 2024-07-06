package ru.kampaii.examples.repositories.id.generators;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPoolIdGenerator extends AbstractPoolIdGenerator<Integer> {
    private AtomicInteger idNum;
    Integer dequeSize;
    Integer finalNumber;

    public AtomicPoolIdGenerator(Connection connection, String tableName, String primaryKey, Integer numOfPrimaryKey, Integer dequeSize) {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.numOfPrimaryKey = numOfPrimaryKey;
        this.dequeSize = dequeSize;
    }

    @Override
    protected void refreshPool() {
        int maxNum = 0;
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT MAX(" + primaryKey + ") FROM " + tableName);
            if (results.next()) {
                maxNum = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.idNum = new AtomicInteger(maxNum);
        this.finalNumber = maxNum + dequeSize;
    }

    @Override
    protected Integer internalGetId() {
        int expectedValue = idNum.intValue();
        if (expectedValue < finalNumber) {
            if (idNum.compareAndSet(expectedValue, expectedValue + 1)) {
                return idNum.intValue();
            }
        }
        return null;
    }

}

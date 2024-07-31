package ru.kampaii.examples.repositories.id.generators;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPoolIdGenerator extends AbstractPoolIdGenerator<Integer> {
    private AtomicInteger idNum;
    Integer dequeSize;
    Integer firstNumber;

    public AtomicPoolIdGenerator(Connection connection, String tableName, String primaryKey, Integer numOfPrimaryKey, Integer dequeSize) {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.numOfPrimaryKey = numOfPrimaryKey;
        this.dequeSize = dequeSize;
        refreshPool();
    }

    @Override
    public final Integer makeNewId() {
        Integer result = internalGetId();
        if (result == 0) {
            refreshPool();
            return internalGetId();
        }
        return result;
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
        this.firstNumber = maxNum + 1;
        this.idNum = new AtomicInteger(firstNumber);
    }

    @Override
    protected synchronized Integer internalGetId() {
        idNum.compareAndSet(dequeSize + firstNumber, 0);
        return idNum.getAndIncrement();
    }

}

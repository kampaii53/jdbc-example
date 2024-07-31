package ru.kampaii.examples.repositories.id.generators;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIdGenerator extends IdGenerator<Integer> {
    private final AtomicInteger idNum;

    public AtomicIdGenerator(Connection connection, String tableName, String primaryKey, Integer numOfPrimaryKey) throws SQLException {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.numOfPrimaryKey = numOfPrimaryKey;
        int maxNum = 0;
        var statement = connection.createStatement();
        var results = statement.executeQuery("SELECT MAX(" + primaryKey + ") FROM " + tableName);
        if (results.next()) {
            maxNum = results.getInt(1);
        }
        this.idNum = new AtomicInteger(maxNum);
    }

    @Override
    public Integer makeNewId() {
        return idNum.addAndGet(1);
    }
}

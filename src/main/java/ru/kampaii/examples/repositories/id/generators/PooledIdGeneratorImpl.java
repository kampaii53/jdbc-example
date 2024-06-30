package ru.kampaii.examples.repositories.id.generators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class PooledIdGeneratorImpl extends IdGenerator<Integer> {
    protected static final Logger log = LoggerFactory.getLogger(PooledIdGeneratorImpl.class);
    Integer dequeSize;
    private final LinkedList<Integer> listOfId;

    public PooledIdGeneratorImpl(Connection connection, String tableName, String primaryKey, Integer numOfPrimaryKey, Integer dequeSize) {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.numOfPrimaryKey = numOfPrimaryKey;
        this.dequeSize = dequeSize;
        this.listOfId = new LinkedList<>();
    }

    @Override
    public synchronized Integer makeNewId() {
        if (listOfId.isEmpty()) {
            int maxNum = 0;
            try (var statement = connection.createStatement()) {
                var results = statement.executeQuery("SELECT MAX(" + primaryKey + ") FROM " + tableName);
                if (results.next()) {
                    maxNum = results.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < dequeSize; i++) {
                listOfId.add(maxNum + i + 1);
            }
            log.info("New select sql in " + tableName);
        }
        return listOfId.pop();
    }
}

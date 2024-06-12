package ru.kampaii.examples.domain.idGenerators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class PooledIdGeneratorImpl extends IdGenerator<Integer> {
    static final Logger log = LoggerFactory.getLogger(PooledIdGeneratorImpl.class);
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
    public Integer makeNewId() {
        if (listOfId.size() == 0) {
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

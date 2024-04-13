package ru.kampaii.examples.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdGenerator {
    public static Integer makeNewIdInt(Connection connection, String tableName, int numOfPrimaryKey) {
        List<Integer> representList = new ArrayList();
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT * FROM " + tableName);
            while (results.next()) {
                representList.add(Integer.valueOf(results.getInt(numOfPrimaryKey + 1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (representList.size() == 0) {
            return 1;
        }
        return (representList.get(representList.size() - 1) + 1);

    }

    public static Long makeNewIdLong(Connection connection, String tableName, int numOfPrimaryKey) {
        List<Long> representList = new ArrayList();
        try (var statement = connection.createStatement()) {
            var results = statement.executeQuery("SELECT * FROM " + tableName);
            while (results.next()) {
                representList.add(Long.valueOf(results.getInt(numOfPrimaryKey + 1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (representList.size() == 0) {
            return 1L;
        }
        return (representList.get(representList.size() - 1) + 1);
    }

    private static UUID makeNewIdUUID() {
        return UUID.randomUUID();
    }
}

package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.OperationsEntity;
import ru.kampaii.examples.domain.representers.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OperationsRepositoryImpl extends Repository {

    public OperationsRepositoryImpl(Connection connection) {
        this.tableName = "operations";
        this.primaryKey = "id";
        this.namesOfStrings = new ArrayList<>();
        this.connection = connection;
        try (var statement = connection.createStatement();) {
            ResultSet results = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                String name = metaData.getColumnName(column);
                namesOfStrings.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    @Override
    Entity makeT(List list) {
        return new OperationsEntity(list);
    }

    @Override
    Object makeNewId() {
        List<Integer> representList = new ArrayList();
        try (var statement = connection.createStatement();) {
            int numOfPrimaryKey = getNumOfLine(primaryKey);
            var results = statement.executeQuery("SELECT * FROM " + tableName);
            while (results.next()) {
                representList.add(Integer.valueOf(results.getString(numOfPrimaryKey + 1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        representList.sort(Comparator.reverseOrder());
        return (representList.get(0) + 1);
    }
}

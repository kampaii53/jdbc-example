package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.OperationsEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationsRepositoryImpl extends Repository<OperationsEntity, Integer> {

    public OperationsRepositoryImpl(Connection connection) {
        this.tableName = "operations";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
    }

    @Override
    Map<String, Object> getData(OperationsEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getToAccount());
        data.put(namesOfStrings.get(1), object.getFromAccount());
        data.put(namesOfStrings.get(2), object.getSum());
        data.put(namesOfStrings.get(3), object.getComment());
        data.put(namesOfStrings.get(4), object.getId());
        return data;
    }

    @Override
    OperationsEntity makeT(Map<String, Object> data) {
        return new OperationsEntity(Integer.valueOf((String) data.get(namesOfStrings.get(0))), Integer.valueOf((String) data.get(namesOfStrings.get(1))), Float.valueOf((String) data.get(namesOfStrings.get(2))), ((String) data.get(namesOfStrings.get(3))), Integer.valueOf((String) data.get(namesOfStrings.get(4))));
    }

    @Override
    Integer makeNewId() {
        List<Integer> representList = new ArrayList();
        try (var statement = connection.createStatement()) {
            int numOfPrimaryKey = getNumOfLine(primaryKey);
            var results = statement.executeQuery("SELECT * FROM " + tableName);
            while (results.next()) {
                representList.add(Integer.valueOf(results.getString(numOfPrimaryKey + 1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (representList.size() == 0) {
            return 1;
        }
        return (representList.get(representList.size() - 1) + 1);
    }
}

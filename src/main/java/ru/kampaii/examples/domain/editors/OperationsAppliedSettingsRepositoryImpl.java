package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.OperationsAppliedSettingsEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationsAppliedSettingsRepositoryImpl extends Repository<OperationsAppliedSettingsEntity, Integer> {

    public OperationsAppliedSettingsRepositoryImpl(Connection connection) {
        this.tableName = "operations_applied_settings";
        this.primaryKey = "operation_id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
    }

    @Override
    Map<String, Object> getData(OperationsAppliedSettingsEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getOperationId());
        data.put(namesOfStrings.get(1), object.getSettingId());
        return data;
    }

    @Override
    OperationsAppliedSettingsEntity makeT(Map<String, Object> data) {
        return new OperationsAppliedSettingsEntity(Integer.valueOf((String) data.get(namesOfStrings.get(0))), Integer.valueOf((String) data.get(namesOfStrings.get(1))));
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


package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.SettingsEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsRepositoryImpl extends Repository<SettingsEntity, Integer> {

    public SettingsRepositoryImpl(Connection connection) {
        this.tableName = "settings";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
    }

    @Override
    SettingsEntity makeT(Map<String, Object> data) {
        return new SettingsEntity(Integer.valueOf((String) data.get(namesOfStrings.get(0))), ((String) data.get(namesOfStrings.get(1))));
    }

    @Override
    Map<String, Object> getData(SettingsEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getId());
        data.put(namesOfStrings.get(1), object.getName());
        return data;
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


package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.UsersSettingEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersSettingRepositoryImpl extends Repository<UsersSettingEntity, Integer> {

    public UsersSettingRepositoryImpl(Connection connection) {
        this.tableName = "users_settings";
        this.primaryKey = "user_id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
    }

    @Override
    Map<String, Object> getData(UsersSettingEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getUserId());
        data.put(namesOfStrings.get(1), object.getSettingId());
        return data;
    }

    @Override
    UsersSettingEntity makeT(Map<String, Object> data) {
        return new UsersSettingEntity(Integer.valueOf((String) data.get(namesOfStrings.get(0))), Integer.valueOf((String) data.get(namesOfStrings.get(1))));
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

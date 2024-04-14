package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.UsersSettingEntity;
import ru.kampaii.examples.domain.idGenerators.UsersSettingsIdGeneratorImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class UsersSettingRepositoryImpl extends Repository<UsersSettingEntity, Integer> {

    public UsersSettingRepositoryImpl(Connection connection) {
        this.tableName = "users_settings";
        this.primaryKey = "user_id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.idGenerator = new UsersSettingsIdGeneratorImpl(connection);
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

}

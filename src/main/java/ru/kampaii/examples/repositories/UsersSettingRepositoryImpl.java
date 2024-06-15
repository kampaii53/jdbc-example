package ru.kampaii.examples.repositories;

import ru.kampaii.examples.domain.entities.UsersSettingEntity;
import ru.kampaii.examples.repositories.id.generators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class UsersSettingRepositoryImpl extends Repository<UsersSettingEntity, Integer> {

    public UsersSettingRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "users_settings";
        this.primaryKey = "user_id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
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
        return new UsersSettingEntity((Integer) data.get(namesOfStrings.get(0)), (Integer) data.get(namesOfStrings.get(1)));
    }

}

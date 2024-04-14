package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.SettingsEntity;
import ru.kampaii.examples.domain.idGenerators.SettingsIdGeneratorImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class SettingsRepositoryImpl extends Repository<SettingsEntity, Integer> {

    public SettingsRepositoryImpl(Connection connection) {
        this.tableName = "settings";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.idGenerator = new SettingsIdGeneratorImpl(connection);
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

}


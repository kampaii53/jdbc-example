package ru.kampaii.examples.repositories;

import ru.kampaii.examples.domain.entities.SettingsEntity;
import ru.kampaii.examples.repositories.id.generators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class SettingsRepositoryImpl extends Repository<SettingsEntity, Integer> {

    public SettingsRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "settings";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
    }

    @Override
    SettingsEntity makeT(Map<String, Object> data) {
        return new SettingsEntity((Integer) data.get(namesOfStrings.get(0)), (String) data.get(namesOfStrings.get(1)));
    }

    @Override
    Map<String, Object> getData(SettingsEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getId());
        data.put(namesOfStrings.get(1), object.getName());
        return data;
    }

}


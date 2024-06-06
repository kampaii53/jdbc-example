package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.OperationsAppliedSettingsEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class OperationsAppliedSettingsRepositoryImpl extends Repository<OperationsAppliedSettingsEntity, Integer> {

    public OperationsAppliedSettingsRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "operations_applied_settings";
        this.primaryKey = "operation_id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
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

}


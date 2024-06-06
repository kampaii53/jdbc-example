package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.OperationsEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class OperationsRepositoryImpl extends Repository<OperationsEntity, Integer> {

    public OperationsRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "operations";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
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

}

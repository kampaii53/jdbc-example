package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class UsersRepositoryImpl extends Repository<UsersEntity, Integer> {

    public UsersRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "users";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
    }

    @Override
    Map<String, Object> getData(UsersEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getId());
        data.put(namesOfStrings.get(1), object.getName());
        data.put(namesOfStrings.get(2), object.getTotalBalance());
        return data;
    }

    @Override
    UsersEntity makeT(Map<String, Object> data) {
        return new UsersEntity((Integer) data.get(namesOfStrings.get(0)), ((String) data.get(namesOfStrings.get(1))), (Float) data.get(namesOfStrings.get(2)));
    }

}

package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.AccountTypesEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class AccountTypesRepositoryImpl extends Repository<AccountTypesEntity, Integer> {

    public AccountTypesRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "account_types";
        this.primaryKey = "id";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
    }

    @Override
    AccountTypesEntity makeT(Map<String, Object> data) {
        return new AccountTypesEntity((Integer) data.get(namesOfStrings.get(0)), (String) data.get(namesOfStrings.get(1)));
    }

    @Override
    Map<String, Object> getData(AccountTypesEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getId());
        data.put(namesOfStrings.get(1), object.getName());
        return data;
    }

}

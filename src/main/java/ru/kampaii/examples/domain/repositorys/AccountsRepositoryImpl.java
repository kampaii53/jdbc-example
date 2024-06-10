package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class AccountsRepositoryImpl extends Repository<AccountsEntity, Integer> {

    public AccountsRepositoryImpl(Connection connection, IdGenerator idGenerator) {
        this.tableName = "accounts";
        this.primaryKey = "number";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.numOfPrimaryKey = getNumOfLine(primaryKey);
        this.idGenerator = idGenerator;
    }

    @Override
    Map<String, Object> getData(AccountsEntity object) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(namesOfStrings.get(0), object.getNumber());
        data.put(namesOfStrings.get(1), object.getBalance());
        data.put(namesOfStrings.get(2), object.getType());
        data.put(namesOfStrings.get(3), object.getUserId());
        return data;
    }

    @Override
    AccountsEntity makeT(Map<String, Object> data) {
        return new AccountsEntity((Integer) data.get(namesOfStrings.get(0)), (Float) data.get(namesOfStrings.get(1)), (Integer) data.get(namesOfStrings.get(2)), (Integer) data.get(namesOfStrings.get(3)));
    }

}

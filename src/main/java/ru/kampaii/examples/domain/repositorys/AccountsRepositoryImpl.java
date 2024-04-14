package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.idGenerators.AccountsIdGeneratorImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class AccountsRepositoryImpl extends Repository<AccountsEntity, Integer> {

    public AccountsRepositoryImpl(Connection connection) {
        this.tableName = "accounts";
        this.primaryKey = "number";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
        this.idGenerator = new AccountsIdGeneratorImpl(connection);
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
        return new AccountsEntity(Integer.valueOf((String) data.get(namesOfStrings.get(0))), Float.valueOf((String) data.get(namesOfStrings.get(1))), Integer.valueOf((String) data.get(namesOfStrings.get(2))), Integer.valueOf((String) data.get(namesOfStrings.get(3))));
    }

}

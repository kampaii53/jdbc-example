package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.AccountsEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsRepositoryImpl extends Repository<AccountsEntity, Integer> {

    public AccountsRepositoryImpl(Connection connection) {
        this.tableName = "accounts";
        this.primaryKey = "number";
        this.connection = connection;
        this.namesOfStrings = createNamesOfStrings();
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

    @Override
    Integer makeNewId() {
        List<java.lang.Integer> representList = new ArrayList();
        try (var statement = connection.createStatement()) {
            int numOfPrimaryKey = getNumOfLine(primaryKey);
            var results = statement.executeQuery("SELECT * FROM " + tableName);
            while (results.next()) {
                representList.add(Integer.valueOf(results.getInt(numOfPrimaryKey + 1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (representList.size() == 0) {
            return 1;
        }
        return (representList.get(representList.size() - 1) + 1);
    }
}

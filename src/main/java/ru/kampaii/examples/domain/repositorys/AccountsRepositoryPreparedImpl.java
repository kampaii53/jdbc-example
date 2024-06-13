package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class AccountsRepositoryPreparedImpl extends AccountsRepositoryImpl {
    PreparedStatement statement;

    public AccountsRepositoryPreparedImpl(Connection connection, IdGenerator<Integer> idGenerator) throws SQLException {
        super(connection, idGenerator);
        this.statement = connection.prepareStatement("INSERT INTO public.accounts(number, balance, type, user_id) VALUES (?, ?, ?, ?);");
    }

    @Override
    public AccountsEntity create(AccountsEntity object) throws SQLException {
        Integer id = makeNewId();
        Map<String, Object> data = getData(object);
        data.put(primaryKey, id);
        statement.setInt(1, (Integer) data.get(namesOfStrings.get(0)));
        statement.setFloat(2, (Float) data.get(namesOfStrings.get(1)));
        statement.setInt(3, (Integer) data.get(namesOfStrings.get(2)));
        statement.setInt(4, (Integer) data.get(namesOfStrings.get(3)));
        statement.executeUpdate();
        return makeT(data);
    }
}

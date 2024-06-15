package ru.kampaii.examples.repositories;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.repositories.id.generators.IdGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountsRepositoryPreparedImpl extends AccountsRepositoryImpl {
    PreparedStatement statement;

    public AccountsRepositoryPreparedImpl(Connection connection, IdGenerator<Integer> idGenerator) throws SQLException {
        super(connection, idGenerator);
        this.statement = connection.prepareStatement("INSERT INTO public.accounts(number, balance, type, user_id) VALUES (?, ?, ?, ?);");
    }

    @Override
    public AccountsEntity create(AccountsEntity object) throws SQLException {
        Integer id = makeNewId();
        statement.setInt(1, id);
        statement.setFloat(2, object.getBalance());
        statement.setInt(3, object.getType());
        statement.setInt(4, object.getUserId());
        statement.executeUpdate();
        return new AccountsEntity(id, object.getBalance(), object.getType(), object.getUserId());
    }
}

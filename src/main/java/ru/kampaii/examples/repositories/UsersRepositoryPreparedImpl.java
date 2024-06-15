package ru.kampaii.examples.repositories;

import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.IdGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


public class UsersRepositoryPreparedImpl extends UsersRepositoryImpl {
    PreparedStatement statement;

    public UsersRepositoryPreparedImpl(Connection connection, IdGenerator<Integer> idGenerator) throws SQLException {
        super(connection, idGenerator);
        this.statement = connection.prepareStatement("INSERT INTO public.users(id, fullname, total_balance) VALUES (?, ?, ?);");
    }

    @Override
    public UsersEntity create(UsersEntity object) throws SQLException {
        Integer id = makeNewId();
        Map<String, Object> data = getData(object);
        data.put(primaryKey, id);
        statement.setInt(1, id);
        statement.setString(2, object.getName());
        statement.setFloat(3, object.getTotalBalance());
        statement.executeUpdate();
        return new UsersEntity(id, object.getName(), object.getTotalBalance());
    }
}

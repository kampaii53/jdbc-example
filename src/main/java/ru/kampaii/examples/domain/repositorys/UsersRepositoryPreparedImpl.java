package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

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
        statement.setInt(1, (Integer) data.get(namesOfStrings.get(0)));
        statement.setString(2, (String) data.get(namesOfStrings.get(1)));
        statement.setFloat(3, (Float) data.get(namesOfStrings.get(2)));
        statement.executeUpdate();
        return makeT(data);
    }
}

package ru.kampaii.examples.repositories;

import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.PooledIdGeneratorImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UsersRepositoryImpl us =new UsersRepositoryImpl(DatabaseConnectorProvider.connect(), new PooledIdGeneratorImpl(DatabaseConnectorProvider.connect(), "users", "id", 1,100));
        List<UsersEntity> cls=new ArrayList<>();
        us.createBatch(cls);
        System.out.println(us);
    }
}

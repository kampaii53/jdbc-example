package ru.kampaii.examples.repositories;

import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.IdGeneratorIntegerImpl;
import ru.kampaii.examples.repositories.id.generators.PooledIdGeneratorImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UsersRepositoryImpl us =new UsersRepositoryImpl(DatabaseConnectorProvider.connect(), new PooledIdGeneratorImpl(DatabaseConnectorProvider.connect(), "users", "id", 1,100));
        List<UsersEntity> cls=new ArrayList<>();
        cls.add(new UsersEntity(null,"4",0F));
        cls.add(new UsersEntity(null,"5",0F));
        us.createBunch(cls);
        System.out.println(us);
    }
}

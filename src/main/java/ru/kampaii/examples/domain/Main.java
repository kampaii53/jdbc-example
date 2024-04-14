package ru.kampaii.examples.domain;

import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.domain.repositorys.UsersRepositoryImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UsersRepositoryImpl rep = new UsersRepositoryImpl(DatabaseConnectorProvider.connect());
        UsersEntity entity = new UsersEntity(null, "'blalal'", 0F);
        rep.create(entity);
    }
}

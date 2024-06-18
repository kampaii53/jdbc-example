package ru.kampaii.examples.repositories.servises;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.Entity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

public class UserServiceTransactional implements Service {
    private Repository<UsersEntity, Integer> usersRepository;
    private Repository<AccountsEntity, Integer> accountsRepository;
    private Random random;
    Connection connection;

    public UserServiceTransactional(Repository usersRepository, Repository accountsRepository, Connection connection) {
        this.usersRepository = usersRepository;
        this.accountsRepository = accountsRepository;
        this.random = new Random();
        this.connection = connection;
    }

    @Override
    public Entity createUser(String name, Integer numOfAcc) {
        try {
            connection.setAutoCommit(false);
            UsersEntity entity = new UsersEntity(null, name, 0F);
            try {
                entity = usersRepository.create(entity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int userId = entity.getId();
            for (int j = 0; j < numOfAcc; j++) {
                try {
                    accountsRepository.create(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, userId));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
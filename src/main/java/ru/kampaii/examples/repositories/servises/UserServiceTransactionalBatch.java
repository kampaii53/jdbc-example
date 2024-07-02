package ru.kampaii.examples.repositories.servises;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserServiceTransactionalBatch implements UserService {

    private final Repository<UsersEntity, Integer> usersRepository;
    private final Repository<AccountsEntity, Integer> accountsRepository;
    private final Random random;
    Connection connection;

    public UserServiceTransactionalBatch(Repository usersRepository, Repository accountsRepository, Connection connection) {
        this.usersRepository = usersRepository;
        this.accountsRepository = accountsRepository;
        this.random = new Random();
        this.connection = connection;
    }

    @Override
    public UsersEntity createUser(String name, Integer numOfAcc) throws SQLException {
        List<AccountsEntity> accounts = new ArrayList<>();
        UsersEntity entity = new UsersEntity(null, name, 0F);
        try {
            entity = usersRepository.create(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int userId = entity.getId();
        for (int j = 0; j < numOfAcc; j++) {
            accounts.add(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, userId));
        }
        accountsRepository.createBatch(accounts);
        return entity;
    }

}


package ru.kampaii.examples.repositories.servises;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class UserServiceCommon implements UserService {

    private final Repository<UsersEntity, Integer> usersRepository;
    private final Repository<AccountsEntity, Integer> accountsRepository;
    private final Random random;

    public UserServiceCommon(Repository usersRepository, Repository accountsRepository) {
        this.usersRepository = usersRepository;
        this.accountsRepository = accountsRepository;
        this.random = new Random();
    }

    @Override
    public UsersEntity createUser(String name, Integer numOfAcc) {
        UsersEntity entity = new UsersEntity(null, name, 0F, null);
        try {
            entity = usersRepository.create(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int userId = entity.getId();
        ArrayList<AccountsEntity> accounts = new ArrayList<>();
        for (int j = 0; j < numOfAcc; j++) {
            try {
                accounts.add(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, userId));
                accountsRepository.create(accounts.get(j));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return new UsersEntity(entity.getId(), entity.getName(), entity.getTotalBalance(), accounts);
    }
}


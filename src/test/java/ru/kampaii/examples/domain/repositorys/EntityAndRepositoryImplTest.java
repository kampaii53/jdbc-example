package ru.kampaii.examples.domain.repositorys;

import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.domain.idGenerators.IdGeneratorIntegerImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityAndRepositoryImplTest {

    Random random = new Random();

    // отрабатывает 11 минут у Тимура
    @Test
    void testInsertsWithIdGenerator() throws SQLException {

        Connection connection = DatabaseConnectorProvider.connect();
        UsersRepositoryImpl usersRepository = new UsersRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "users", "id", 1));
        AccountsRepositoryImpl accountsRepository = new AccountsRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "accounts", "number", 1));

        executeScripts(1000, 5, usersRepository, accountsRepository);
    }


    private void executeScripts(
            int userCount,
            int accountCount,
            Repository<UsersEntity, Integer> usersRepository,
            Repository<AccountsEntity, Integer> accountsRepository
    ) throws SQLException {
        ResultSet resultSet = accountsRepository.executeQuery("SELECT count(*) FROM public.accounts ");
        int firstAccountsCount = 0;
        if (resultSet.next()) {
            firstAccountsCount = resultSet.getInt(1);
        }
        resultSet = accountsRepository.executeQuery("SELECT count(*) FROM public.users ");
        int firstUsersCount = 0;
        if (resultSet.next()) {
            firstUsersCount = resultSet.getInt(1);
        }
        for (int i = 0; i < userCount; i++) {
            UsersEntity entity = new UsersEntity(null, String.valueOf(random.nextInt(0, 10000)), 0F);
            entity = usersRepository.create(entity);
            int userId = entity.getId();
            for (int j = 0; j < accountCount; j++) {
                accountsRepository.create(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, userId));
            }
        }
        resultSet = accountsRepository.executeQuery("SELECT count(*) FROM public.accounts ");
        if (resultSet.next()) {
            assertEquals(userCount * accountCount, resultSet.getInt(1) - firstAccountsCount);
        }
        resultSet = accountsRepository.executeQuery("SELECT count(*) FROM public.users ");
        if (resultSet.next()) {
            assertEquals(userCount, resultSet.getInt(1) - firstUsersCount);
        }
    }
}

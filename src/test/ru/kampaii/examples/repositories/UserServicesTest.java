package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.PooledIdGeneratorImpl;
import ru.kampaii.examples.repositories.servises.UserService;
import ru.kampaii.examples.repositories.servises.UserServiceCommon;
import ru.kampaii.examples.repositories.servises.UserServiceTransactional;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServicesTest {
    private Connection connection;
    private Repository<UsersEntity, Integer> usersRepository;
    private Repository<AccountsEntity, Integer> accountsRepository;

    @BeforeEach
    void setUp() throws SQLException {
        this.connection = DatabaseConnectorProvider.connect();
    }

    @AfterEach
    void tearDown() {
        this.usersRepository = null;
        this.accountsRepository = null;
    }

    @Test
    void userServiceTest() throws SQLException {
        String name = "TEST";
        usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        UserService service = new UserServiceCommon(usersRepository, accountsRepository);
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
        assertEquals(5, accountsRepository.count() - firstAccountsCount);
        assertEquals(1, usersRepository.count() - firstUsersCount);
    }

    @Test
    void userServiceTransTest() throws SQLException {
        String name = "TEST";
        usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        UserService service = new UserServiceTransactional(usersRepository, accountsRepository, connection);
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
        assertEquals(5, accountsRepository.count() - firstAccountsCount);
        assertEquals(1, usersRepository.count() - firstUsersCount);
    }
}

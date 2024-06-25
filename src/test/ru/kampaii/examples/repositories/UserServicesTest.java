package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
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
        usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        UserService service = new UserServiceCommon(usersRepository, accountsRepository);
        executeIntegrationTest(service);
    }

    @Test
    void userServiceTransTest() throws SQLException {
        usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        UserService service = new UserServiceTransactional(usersRepository, accountsRepository, connection);
        executeIntegrationTest(service);
    }

    @Test
    void userServiceUnitTest() throws SQLException {
        usersRepository = Mockito.mock(UsersRepositoryImpl.class);
        accountsRepository = Mockito.mock(AccountsRepositoryImpl.class);
        UserService service = new UserServiceCommon(usersRepository, accountsRepository);
        executeUnitTest(service);
    }

    @Test
    void userServiceTransUnitTest() throws SQLException {
        usersRepository = Mockito.mock(UsersRepositoryImpl.class);
        accountsRepository = Mockito.mock(AccountsRepositoryImpl.class);
        UserService service = new UserServiceTransactional(usersRepository, accountsRepository, connection);
        executeUnitTest(service);
    }

    void executeIntegrationTest(UserService service) {
        String name = "TEST";
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
        assertEquals(5, accountsRepository.count() - firstAccountsCount);
        assertEquals(1, usersRepository.count() - firstUsersCount);
    }

    void executeUnitTest(UserService service) throws SQLException {
        String name = "TEST";
        Mockito.when(usersRepository.create(any())).thenReturn(new UsersEntity(1, name, 0F));
        Mockito.when(accountsRepository.create(any())).thenReturn(new AccountsEntity(1, 0F, 1, 1));
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
    }
}

package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.Entity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.IdGeneratorIntegerImpl;
import ru.kampaii.examples.repositories.id.generators.PooledIdGeneratorImpl;
import ru.kampaii.examples.repositories.servises.Service;
import ru.kampaii.examples.repositories.servises.UserServiceCommon;
import ru.kampaii.examples.repositories.servises.UserServiceTransactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityAndRepositoryImplTest {

    static final int USERS_COUNT = 1000;
    static final int ACCS_PER_USER = 5;
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

    // отрабатывает 8 минут у Тимура
    @Test
    void testInsertsWithCommonIdGenerator() {
        usersRepository = new UsersRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "users", "id", 1));
        accountsRepository = new AccountsRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "accounts", "number", 1));
        executeTest(usersRepository, accountsRepository, new UserServiceCommon(usersRepository, accountsRepository));
    }

    // отрабатывает 5 минут у Тимура
    @Test
    void  entityAndRepositoryWithPooledGeneratorImpl() {
        usersRepository = new UsersRepositoryImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        executeTest(usersRepository, accountsRepository, new UserServiceCommon(usersRepository, accountsRepository));
    }

    @Test
    void entityAndPreparedRepositoryWithPooledGeneratorImpl() {
        try {
            usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
            accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
            executeTest(usersRepository, accountsRepository, new UserServiceCommon(usersRepository, accountsRepository));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void transactionalEntityAndPreparedRepositoryWithPooledGeneratorImpl() throws SQLException {
        usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        executeTest(usersRepository, accountsRepository, new UserServiceTransactional(usersRepository, accountsRepository, connection));
    }

    private void executeTest(Repository<UsersEntity, Integer> usersRepository, Repository<AccountsEntity, Integer> accountsRepository, Service<UsersEntity> userService) {
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        for (int i = 0; i < USERS_COUNT; i++) {
            userService.createUser(String.valueOf(i), ACCS_PER_USER);
        }
        assertEquals(USERS_COUNT * ACCS_PER_USER, accountsRepository.count() - firstAccountsCount);
        assertEquals(USERS_COUNT, usersRepository.count() - firstUsersCount);
    }

    @Test
    void userServiceTest() throws SQLException {
        String name = "TEST";
        usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        Service service=new UserServiceCommon(usersRepository, accountsRepository);
        UsersEntity entity= (UsersEntity) service.createUser(name,5);
        assertEquals(name,entity.getName());
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
        Service service=new UserServiceTransactional(usersRepository, accountsRepository,connection);
        UsersEntity entity= (UsersEntity) service.createUser(name,5);
        assertEquals(name,entity.getName());
        assertEquals(5, accountsRepository.count() - firstAccountsCount);
        assertEquals(1, usersRepository.count() - firstUsersCount);
    }

}

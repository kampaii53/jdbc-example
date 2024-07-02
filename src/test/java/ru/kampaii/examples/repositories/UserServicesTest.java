package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.IdGenerator;
import ru.kampaii.examples.repositories.id.generators.PooledIdGeneratorImpl;
import ru.kampaii.examples.repositories.servises.UserService;
import ru.kampaii.examples.repositories.servises.UserServiceCommon;
import ru.kampaii.examples.repositories.servises.UserServiceTransactional;
import ru.kampaii.examples.repositories.servises.UserServiceTransactionalBatch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServicesTest {
    private Connection connection;
    private Repository<UsersEntity, Integer> usersRepository;
    private Repository<AccountsEntity, Integer> accountsRepository;
    private IdGenerator<Integer> usersIdGenerator;
    private IdGenerator<Integer> accountsIdGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        this.connection = DatabaseConnectorProvider.connect();
        this.usersRepository = new UsersRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        this.accountsRepository = new AccountsRepositoryPreparedImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
    }

    @AfterEach
    void tearDown() {
        this.usersRepository = null;
        this.accountsRepository = null;
    }

    @Test
    void userServiceTest() throws SQLException {
        UserService service = new UserServiceCommon(usersRepository, accountsRepository);
        executeIntegrationTest(service);
    }

    @Test
    void userServiceTransTest() throws SQLException {
        UserService service = new UserServiceTransactional(usersRepository, accountsRepository, connection);
        executeIntegrationTest(service);
    }

    @Test
    void userServiceTransBatchTest() throws SQLException {
        UserService service = new UserServiceTransactionalBatch(usersRepository, accountsRepository, connection);
        executeIntegrationTest(service);
    }

    void executeIntegrationTest(UserService service) throws SQLException {
        String name = "TEST";
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
        assertEquals(5, accountsRepository.count() - firstAccountsCount);
        assertEquals(1, usersRepository.count() - firstUsersCount);
    }

    @Test
    void testWithMultiThreads() throws SQLException, InterruptedException {
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        usersIdGenerator = new PooledIdGeneratorImpl(DatabaseConnectorProvider.connect(), "users", "id", 1, 10000);
        accountsIdGenerator = new PooledIdGeneratorImpl(DatabaseConnectorProvider.connect(), "accounts", "number", 1, 10000);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Runnable> listForRunnable = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listForRunnable.add(this::executeTestForSingleThread);
        }
        listForRunnable.forEach(executor::submit);
        executor.awaitTermination(5, SECONDS);
        assertEquals(5000, accountsRepository.count() - firstAccountsCount);
        assertEquals(1000, usersRepository.count() - firstUsersCount);
    }

    public void executeTestForSingleThread() {
        try {
            Connection connectionForThread = DatabaseConnectorProvider.connect();
            UserService userService = new UserServiceTransactionalBatch(new UsersRepositoryImpl(connectionForThread, usersIdGenerator), new AccountsRepositoryImpl(connectionForThread, accountsIdGenerator), connectionForThread);
            for (int i = 0; i < 200; i++) {
                userService.createUser(String.valueOf(i), 5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

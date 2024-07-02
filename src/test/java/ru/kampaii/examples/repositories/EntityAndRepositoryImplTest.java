package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.id.generators.IdGeneratorIntegerImpl;
import ru.kampaii.examples.repositories.id.generators.PooledIdGeneratorImpl;
import ru.kampaii.examples.repositories.servises.UserService;
import ru.kampaii.examples.repositories.servises.UserServiceCommon;
import ru.kampaii.examples.repositories.servises.UserServiceTransactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void testInsertsWithCommonIdGenerator() throws SQLException {
        usersRepository = new UsersRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "users", "id", 1));
        accountsRepository = new AccountsRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "accounts", "number", 1));
        executeTest(usersRepository, accountsRepository, new UserServiceCommon(usersRepository, accountsRepository));
    }

    @Test
    void entityAndRepositoryWithPooledGeneratorImpl() throws SQLException {
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

    @Test
    void entityAndRepositoryCreateBatch() throws SQLException {
        Random random = new Random();
        usersRepository = new UsersRepositoryImpl(DatabaseConnectorProvider.connect(), new PooledIdGeneratorImpl(DatabaseConnectorProvider.connect(), "users", "id", 1, 10000));
        List<UsersEntity> usersData = new ArrayList<>();
        accountsRepository = new AccountsRepositoryImpl(DatabaseConnectorProvider.connect(), new PooledIdGeneratorImpl(DatabaseConnectorProvider.connect(), "accounts", "number", 1, 10000));
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        List<AccountsEntity> acsData = new ArrayList<>();
        for (int i = 0; i < USERS_COUNT; i++) {
            UsersEntity entity = new UsersEntity(null, String.valueOf(i), 0F);
            usersData.add(entity);
        }
        List<UsersEntity> listOfUsersEntities = usersRepository.createBatch(usersData);
        for (int i = 0; i < USERS_COUNT; i++) {
            UsersEntity entity = listOfUsersEntities.get(i);
            int userId = entity.getId();
            for (int j = 0; j < ACCS_PER_USER; j++) {
                acsData.add(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, userId));
            }
        }
        accountsRepository.createBatch(acsData);
        assertEquals(USERS_COUNT * ACCS_PER_USER, accountsRepository.count() - firstAccountsCount);
        assertEquals(USERS_COUNT, usersRepository.count() - firstUsersCount);
    }

    private void executeTest(Repository<UsersEntity, Integer> usersRepository, Repository<AccountsEntity, Integer> accountsRepository, UserService userService) throws SQLException {
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();
        for (int i = 0; i < USERS_COUNT; i++) {
            userService.createUser(String.valueOf(i), ACCS_PER_USER);
        }
        assertEquals(USERS_COUNT * ACCS_PER_USER, accountsRepository.count() - firstAccountsCount);
        assertEquals(USERS_COUNT, usersRepository.count() - firstUsersCount);
    }

}

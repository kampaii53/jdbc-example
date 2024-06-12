package ru.kampaii.examples.domain.repositorys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.domain.idGenerators.IdGeneratorIntegerImpl;
import ru.kampaii.examples.domain.idGenerators.PooledIdGeneratorImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityAndRepositoryImplTest {

    Random random = new Random();

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
    void testInsertsWithIdGenerator() {

        usersRepository = new UsersRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "users", "id", 1));
        accountsRepository = new AccountsRepositoryImpl(connection, new IdGeneratorIntegerImpl(connection, "accounts", "number", 1));
        executeTest(usersRepository, accountsRepository, this::singleThreadCreation);
    }

    // отрабатывает 5 минут у Тимура
    @Test
    void EntityAndRepositoryImpl() {
        usersRepository = new UsersRepositoryImpl(connection, new PooledIdGeneratorImpl(connection, "users", "id", 1, 1000));
        accountsRepository = new AccountsRepositoryImpl(connection, new PooledIdGeneratorImpl(connection, "accounts", "number", 1, 1000));
        executeTest(usersRepository, accountsRepository, this::singleThreadCreation);
    }

    private void executeTest(
            Repository<UsersEntity, Integer> usersRepository,
            Repository<AccountsEntity, Integer> accountsRepository,
            Runnable runnable
    ) {
        int firstAccountsCount = accountsRepository.count();
        int firstUsersCount = usersRepository.count();

        runnable.run();

        assertEquals(USERS_COUNT * ACCS_PER_USER, accountsRepository.count() - firstAccountsCount);
        assertEquals(USERS_COUNT, usersRepository.count() - firstUsersCount);
    }

    private void singleThreadCreation() {
        for (int i = 0; i < USERS_COUNT; i++) {
            UsersEntity entity = new UsersEntity(null, String.valueOf(random.nextInt(0, 10000)), 0F);
            entity = usersRepository.create(entity);
            int userId = entity.getId();
            for (int j = 0; j < ACCS_PER_USER; j++) {
                accountsRepository.create(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, userId));
            }
        }
    }

}

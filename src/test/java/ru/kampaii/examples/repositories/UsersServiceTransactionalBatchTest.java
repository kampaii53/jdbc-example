package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.servises.UserServiceTransactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTransactionalBatchTest {
    @Mock
    private Repository<UsersEntity, Integer> usersRepository;
    @Mock
    private Repository<AccountsEntity, Integer> accountsRepository;
    @Mock
    private Connection connection;
    @InjectMocks
    private UserServiceTransactional service;

    @Test
    void userServiceUnitTest() throws SQLException {
        String name = "TEST";
        Random random = new Random();
        ArrayList<AccountsEntity> accounts = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            try {
                accounts.add(new AccountsEntity(null, (float) random.nextInt(0, 10000), 1, 1));
                accountsRepository.create(accounts.get(j));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Mockito.when(usersRepository.create(any())).thenReturn(new UsersEntity(1, name, 0F, accounts));
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
    }

}

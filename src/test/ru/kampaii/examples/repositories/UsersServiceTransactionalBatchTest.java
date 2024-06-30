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
        Mockito.when(usersRepository.create(any())).thenReturn(new UsersEntity(1, name, 0F));
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
    }

}

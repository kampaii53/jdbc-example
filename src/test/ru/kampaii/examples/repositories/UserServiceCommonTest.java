package ru.kampaii.examples.repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.servises.UserService;
import ru.kampaii.examples.repositories.servises.UserServiceCommon;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceCommonTest {
    private Repository<UsersEntity, Integer> usersRepository;
    private Repository<AccountsEntity, Integer> accountsRepository;

    @Test
    void userServiceUnitTest() throws SQLException {
        usersRepository = Mockito.mock(UsersRepositoryImpl.class);
        accountsRepository = Mockito.mock(AccountsRepositoryImpl.class);
        UserService service = new UserServiceCommon(usersRepository, accountsRepository);
        String name = "TEST";
        Mockito.when(usersRepository.create(any())).thenReturn(new UsersEntity(1, name, 0F));
        Mockito.when(accountsRepository.create(any())).thenReturn(new AccountsEntity(1, 0F, 1, 1));
        UsersEntity entity = service.createUser(name, 5);
        assertEquals(name, entity.getName());
    }

}

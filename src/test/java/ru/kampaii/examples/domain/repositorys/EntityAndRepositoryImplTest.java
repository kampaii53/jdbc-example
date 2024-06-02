package ru.kampaii.examples.domain.repositorys;

import org.junit.jupiter.api.Test;
import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.UsersEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityAndRepositoryImplTest {
    @Test
    public void test() throws SQLException {
        Random random = new Random();
        UsersRepositoryImpl usersRepository = new UsersRepositoryImpl(DatabaseConnectorProvider.connect());
        AccountsRepositoryImpl accountsRepository = new AccountsRepositoryImpl(DatabaseConnectorProvider.connect());
        for (int i = 0; i < 1000; i++) {
            Float totalBalance = 0F;
            UsersEntity entity = new UsersEntity(null, String.valueOf(random.nextInt(0, 10000)), 0F);
            entity = usersRepository.create(entity);
            int userId = entity.getId();
            ArrayList<AccountsEntity> arrayList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                arrayList.add(new AccountsEntity(null, Float.valueOf(random.nextInt(0, 10000)), 1, userId));
                totalBalance += arrayList.get(j).getBalance();
                arrayList.set(j, accountsRepository.create(arrayList.get(j)));
                usersRepository.update(new UsersEntity(userId, usersRepository.getById(userId).getName(), totalBalance));
            }
            for (int j = 0; j < 5; j++) {
                assertEquals(accountsRepository.getById(arrayList.get(j).getNumber()).getBalance(), arrayList.get(j).getBalance());
            }
            assertEquals(usersRepository.getById(userId).getTotalBalance(), totalBalance);
            ResultSet resultSet = accountsRepository.executeQuery("SELECT count(*) FROM public.accounts WHERE user_id=" + userId);
            if (resultSet.next()) {
                assertEquals(5, resultSet.getInt(1));
            }
        }
    }
}

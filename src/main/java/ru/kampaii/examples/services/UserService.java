package ru.kampaii.examples.services;

import ru.kampaii.examples.domain.entities.AccountsEntity;
import ru.kampaii.examples.domain.entities.Entity;
import ru.kampaii.examples.domain.entities.UsersEntity;
import ru.kampaii.examples.repositories.Repository;

public interface UserService<T extends Entity> {
    public T createUser(String name, Integer numOfAcc);
}

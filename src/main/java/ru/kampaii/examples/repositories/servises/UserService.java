package ru.kampaii.examples.repositories.servises;

import ru.kampaii.examples.domain.entities.UsersEntity;

public interface UserService {
    UsersEntity createUser(String name, Integer numOfAcc);
}
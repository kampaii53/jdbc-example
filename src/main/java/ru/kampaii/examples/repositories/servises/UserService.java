package ru.kampaii.examples.repositories.servises;

import ru.kampaii.examples.domain.entities.UsersEntity;

import java.sql.SQLException;

public interface UserService {
    UsersEntity createUser(String name, Integer numOfAcc) throws SQLException;
}
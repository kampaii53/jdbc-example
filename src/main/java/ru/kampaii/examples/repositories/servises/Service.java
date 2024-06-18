package ru.kampaii.examples.repositories.servises;

import ru.kampaii.examples.domain.entities.Entity;

public interface Service <T extends Entity> {
    public T createUser(String name, Integer numOfAcc);
}
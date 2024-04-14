package ru.kampaii.examples.domain.idGenerators;

public interface IdGenerator<T> {
    T makeNewId();
}

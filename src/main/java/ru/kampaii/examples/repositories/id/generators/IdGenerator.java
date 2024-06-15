package ru.kampaii.examples.repositories.id.generators;

import java.sql.Connection;

public abstract class IdGenerator<ID> {

    Connection connection;
    String tableName;
    String primaryKey;
    Integer numOfPrimaryKey;

    public abstract ID makeNewId();
}

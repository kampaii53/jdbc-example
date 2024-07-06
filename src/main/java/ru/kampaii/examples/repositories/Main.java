package ru.kampaii.examples.repositories;

import ru.kampaii.examples.repositories.id.generators.AtomicPoolIdGenerator;
import ru.kampaii.examples.repositories.id.generators.IdGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static IdGenerator<Integer> usersIdGenerator;
    private static IdGenerator<Integer> accountsIdGenerator;

    public static void main(String[] args) throws SQLException, InterruptedException {
        usersIdGenerator = new AtomicPoolIdGenerator(null, "users", "id", 1,1000);
    }
}

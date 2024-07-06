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
        var threadPool = Executors.newFixedThreadPool(5);
        List<Runnable> listForRunnable = new ArrayList<>();
        Incrementer incrementer = new Incrementer(100);
        for (int i = 0; i < 5; i++) {
            listForRunnable.add(new Generator(incrementer));
        }
        listForRunnable.forEach(threadPool::submit);
    }
}

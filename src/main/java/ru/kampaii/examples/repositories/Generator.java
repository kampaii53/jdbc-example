package ru.kampaii.examples.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Generator implements Runnable{
    private Incrementer incrementer;

    public Generator(Incrementer incrementer) {
        this.incrementer = incrementer;
    }

    public void run() {
        for (int i = 0; i < 200; i++) {
            incrementer.run();
        }
    }
}

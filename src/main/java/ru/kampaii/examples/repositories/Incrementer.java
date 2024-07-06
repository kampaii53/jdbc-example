package ru.kampaii.examples.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Incrementer {
    private static final Logger logger = LoggerFactory.getLogger(Incrementer.class);
    private AtomicInteger idNum;
    Integer dequeSize;
    Integer finalNumber;

    public Incrementer(Integer dequeSize) {
        this.dequeSize = dequeSize;
        this.idNum = new AtomicInteger();
        refreshPool();
    }


    public void run() {
        Integer result = internalGetId();
        if (result == null) {
            refreshPool();

        }
        logger.info(String.valueOf(result));

    }

    protected void refreshPool() {
        int maxNum = 0;
        this.idNum = new AtomicInteger(maxNum);
        this.finalNumber = maxNum + dequeSize;
    }

    protected Integer internalGetId() {
        int expectedValue = idNum.intValue();
        if (expectedValue < finalNumber) {
            if (idNum.compareAndSet(expectedValue, expectedValue + 1)) {
               return idNum.intValue();
            }

        }
        return null;
    }
}


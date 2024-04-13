package ru.kampaii.examples.domain.entities;

public class AccountsEntity implements Entity {
    private final Integer number;
    private final float balance;
    private final Integer type;
    private final Integer userId;

    public AccountsEntity(Integer number, Float balance, Integer type, Integer userId) {
        this.number = number;
        this.balance = balance;
        this.type = type;
        this.userId = userId;
    }

    public float getBalance() {
        return balance;
    }

    public Integer getType() {
        return type;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getNumber() {
        return number;
    }
}

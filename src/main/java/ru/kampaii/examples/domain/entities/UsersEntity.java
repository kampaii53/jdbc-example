package ru.kampaii.examples.domain.entities;

import java.util.List;

public class UsersEntity implements Entity {
    private final String name;
    private final Integer Id;
    private final Float totalBalance;
    private final List<AccountsEntity> accounts;

    public UsersEntity(Integer Id, String name, Float totalBalance, List<AccountsEntity> accounts) {
        this.name = name;
        this.Id = Id;
        this.totalBalance = totalBalance;
        this.accounts = accounts;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Float getTotalBalance() {
        return totalBalance;
    }

    public List<AccountsEntity> getAccounts() {
        return accounts;
    }
}

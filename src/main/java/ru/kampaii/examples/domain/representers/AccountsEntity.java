package ru.kampaii.examples.domain.representers;

import java.util.List;

public class AccountsEntity implements Entity{
    private List data;

    public AccountsEntity(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}

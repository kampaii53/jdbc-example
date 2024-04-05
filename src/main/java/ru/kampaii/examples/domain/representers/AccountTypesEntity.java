package ru.kampaii.examples.domain.representers;

import java.util.List;

public class AccountTypesEntity implements Entity{
    private List data;

    public AccountTypesEntity(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}

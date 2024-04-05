package ru.kampaii.examples.domain.representers;

import java.util.List;

public class UsersEntity implements Entity {
    private List data;

    public UsersEntity(List data) {
        this.data = data;
    }

    @Override
    public List getData() {
        return data;
    }
}

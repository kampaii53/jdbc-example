package ru.kampaii.examples.domain.representers;

import java.util.List;

public class UsersSettingEntity implements Entity{
    private List data;

    public UsersSettingEntity(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}
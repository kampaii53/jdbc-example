package ru.kampaii.examples.domain.representers;

import java.util.List;

public class SettingsEntity implements Entity{
    private List data;

    public SettingsEntity(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}

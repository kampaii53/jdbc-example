package ru.kampaii.examples.domain.entities;

public class SettingsEntity implements Entity{
    private final String name;
    private final Integer Id;

    public SettingsEntity(Integer Id, String name) {
        this.name = name;
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}

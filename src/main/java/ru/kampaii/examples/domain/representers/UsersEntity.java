package ru.kampaii.examples.domain.representers;

public class UsersEntity implements Entity {
    private final String name;
    private final Integer Id;
    private final Float totalBalance;

    public UsersEntity(Integer Id, String name, Float totalBalance) {
        this.name = name;
        this.Id = Id;
        this.totalBalance = totalBalance;
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
}

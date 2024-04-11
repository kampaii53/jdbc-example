package ru.kampaii.examples.domain.representers;

public class UsersSettingEntity implements Entity{
    private final Integer userId;
    private final Integer settingId;

    public UsersSettingEntity(Integer userId, Integer settingId) {
        this.userId = userId;
        this.settingId = settingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getSettingId() {
        return settingId;
    }
}
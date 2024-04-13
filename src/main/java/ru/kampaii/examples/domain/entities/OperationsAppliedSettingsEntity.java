package ru.kampaii.examples.domain.entities;

public class OperationsAppliedSettingsEntity implements Entity{
    private final Integer operationId;
    private final Integer settingId;

    public OperationsAppliedSettingsEntity(Integer operationId, Integer settingId) {
        this.operationId = operationId;
        this.settingId = settingId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public Integer getSettingId() {
        return settingId;
    }
}

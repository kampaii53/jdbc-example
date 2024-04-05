package ru.kampaii.examples.domain.representers;

import java.util.List;

public class OperationsAppliedSettingsEntity implements Entity{
    private List data;

    public OperationsAppliedSettingsEntity(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}

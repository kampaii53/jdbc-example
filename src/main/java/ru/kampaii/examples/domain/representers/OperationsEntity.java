package ru.kampaii.examples.domain.representers;

import java.util.List;

public class OperationsEntity implements Entity{
    private List data;

    public OperationsEntity(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}
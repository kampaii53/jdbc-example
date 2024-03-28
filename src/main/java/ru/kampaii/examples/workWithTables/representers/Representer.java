package ru.kampaii.examples.workWithTables.representers;

import java.util.List;

public class Representer {
    private List data;

    public Representer(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }
}

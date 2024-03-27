package ru.kampaii.examples;

import ru.kampaii.examples.workWithTables.editors.Editor;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //Пример insert
        Editor editor = new Editor("users");
        HashMap<String, Object> hashMapInsert = new HashMap<>();
        hashMapInsert.put("id", 11);
        hashMapInsert.put("fullname", 11);
        hashMapInsert.put("total_balance", 11);
        editor.insert(hashMapInsert);
        //Пример update
        HashMap<String, Object> hashMapUpdate = new HashMap<>();
        hashMapUpdate.put("id", 111);
        hashMapUpdate.put("fullname", 111);
        hashMapUpdate.put("total_balance",111);
        hashMapUpdate.put("columnForUpdate", "id");
        hashMapUpdate.put("previous", 4);
        editor.update(hashMapUpdate);
        //пример для получения строки по id
        System.out.println(editor.getStringsByKey(111,"id"));
        //пример для получения всех строк
        System.out.println(editor.getAllString());
    }


}

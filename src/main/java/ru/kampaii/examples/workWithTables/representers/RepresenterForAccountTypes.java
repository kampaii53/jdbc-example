package ru.kampaii.examples.workWithTables.representers;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RepresenterForAccountTypes {
    public String getString(int id,String columnForSelect) {
        Map<Integer,String> representMap = new HashMap<>();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            var results = statement.executeQuery("SELECT id, name FROM public.account_types;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String name = results.getString(2);
                representMap.put(idOfLine,name);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representMap.get(id);
    }
    public Map<Integer, String> getAllString() {
        Map<Integer,String> representMap = new HashMap<>();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            var results = statement.executeQuery("SELECT id, name FROM public.account_types;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String name = results.getString(2);
                representMap.put(idOfLine,name);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representMap;
    }

}

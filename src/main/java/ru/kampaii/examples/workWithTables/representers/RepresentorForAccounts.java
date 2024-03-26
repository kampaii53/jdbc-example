package ru.kampaii.examples.workWithTables.representers;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepresentorForAccounts {
    public String getString(int id) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            int sum=0;
            var result = statement.executeQuery("SELECT \"number\", balance, type, user_id FROM public.accounts;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum+1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT \"number\", balance, type, user_id FROM public.accounts;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String balance = results.getString(2);
                String type = results.getString(3);
                String userId = results.getString(4);
                representList.add(idOfLine, "number-"+idOfLine+" "+"balance-"+balance+" "+"type-"+type+" "+"user_id-"+userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representList.get(id);
    }
    public List<String> getAllString() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            int sum=0;
            var result = statement.executeQuery("SELECT \"number\", balance, type, user_id FROM public.accounts;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum+1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT \"number\", balance, type, user_id FROM public.accounts;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String balance = results.getString(2);
                String type = results.getString(3);
                String userId = results.getString(4);
                representList.add(idOfLine, "number-"+idOfLine+" "+"balance-"+balance+" "+"type-"+type+" "+"user_id-"+userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representList;
    }

}


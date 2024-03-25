package ru.kampaii.examples.workWithTables.representers;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepresentorForUsers {
    public String getString(int id) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            int sum=0;
            var result = statement.executeQuery("SELECT id, fullname, total_balance FROM public.users;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum+1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT id, fullname, total_balance FROM public.users;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String fullname= results.getString(2);
                String total_balance= results.getString(2);
                representList.add(idOfLine,"id-"+idOfLine+" "+"fullname-"+fullname+" "+"total_balance-"+total_balance);
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
            int sum = 0;
            var result = statement.executeQuery("SELECT id, fullname, total_balance FROM public.users;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum + 1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT id, fullname, total_balance FROM public.users;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String fullname = results.getString(2);
                String total_balance = results.getString(2);
                representList.add(idOfLine, "id-" + idOfLine + " " + "fullname-" + fullname + " " + "total_balance-" + total_balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representList;
    }
}

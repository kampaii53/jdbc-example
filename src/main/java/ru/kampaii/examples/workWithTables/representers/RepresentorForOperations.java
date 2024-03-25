package ru.kampaii.examples.workWithTables.representers;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepresentorForOperations {
    public String getString(int id) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            int sum=0;
            var result = statement.executeQuery("SELECT from_account, to_account, sum, comment_for_operation, id FROM public.operations;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum+1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT from_account, to_account, sum, comment_for_operation, id FROM public.operations;");
            while (results.next()) {
                Integer idOfLine = results.getInt(5);
                String fromAccount = results.getString(1);
                String toAccount = results.getString(2);
                String sumFromTable = results.getString(3);
                String commentForOperation = results.getString(4);
                representList.add(idOfLine,"from_account-"+fromAccount+" "+"to_account-"+toAccount+" "+"sum-"+sumFromTable+" "+"comment_for_operation-"+commentForOperation+" "+"id-"+idOfLine );
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
            var result = statement.executeQuery("SELECT from_account, to_account, sum, comment_for_operation, id FROM public.operations;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum+1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT from_account, to_account, sum, comment_for_operation, id FROM public.operations;");
            while (results.next()) {
                Integer idOfLine = results.getInt(5);
                String fromAccount = results.getString(1);
                String toAccount = results.getString(2);
                String sumFromTable = results.getString(3);
                String commentForOperation = results.getString(4);
                representList.add(idOfLine,"fromAccount-"+fromAccount+" "+"toAccount-"+toAccount+" "+"sumFromTable-"+sumFromTable+" "+"commentForOperation-"+commentForOperation+" "+"id-"+idOfLine );
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representList;
    }

}

package ru.kampaii.examples.workWithTables.representers;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepresentorForUsersSettings {
    public String getString(int id) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            System.out.println("Connected successfully");
            int sum=0;
            var result = statement.executeQuery("SELECT user_id, setting_id FROM public.users_settings;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum+1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT user_id, setting_id FROM public.users_settings;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String settingId= results.getString(2);
                representList.add(idOfLine,"user_id-"+idOfLine+" "+"setting_id-"+settingId);
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
            var result = statement.executeQuery("SELECT user_id, setting_id FROM public.users_settings;");
            while (result.next()) {
                sum++;
            }
            for (int i = 0; i < sum + 1; i++) {
                representList.add("");
            }
            var results = statement.executeQuery("SELECT user_id, setting_id FROM public.users_settings;");
            while (results.next()) {
                Integer idOfLine = results.getInt(1);
                String settingId = results.getString(2);
                representList.add(idOfLine, "user_id-" + idOfLine + " " + "setting_id-" + settingId);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return representList;
    }
}

package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class EditorForUsersSettings {
    public void insert(int userId, int settingsId) {
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "INSERT INTO public.users_settings( user_id, setting_id) VALUES (" + userId + "," + settingsId + ");";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void update(int beforeId, int userId, int settingsId) {
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.users_settings" + " SET user_id=" + userId + ", setting_id=" + settingsId + " WHERE user_id=" + beforeId + ";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void delete(String condition) {
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.users_settings WHERE " + condition + ";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

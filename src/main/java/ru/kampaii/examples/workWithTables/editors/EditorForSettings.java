package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class EditorForSettings {
    public void insert(int Id,String name){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "INSERT INTO public.settings( id, name) VALUES ("+Id+","+ name+");";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void update(int beforeId ,int Id,String name){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.settings" + " SET id="+Id+", name="+name + " WHERE id="+beforeId+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void delete(String condition){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.settings WHERE " + condition+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class EditorForUsers {
    public static void insert(int id,String fullname,int totalBalance){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "INSERT INTO public.users(id, fullname, total_balance) VALUES ("+id+","+fullname+","+totalBalance+");";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void update(int beforeId ,int id,String fullname,int totalBalance){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.users" + " SET id="+id+", fullname="+fullname +", total_balance="+totalBalance+ " WHERE id="+beforeId+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void delete(String condition){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.users WHERE " + condition+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class EditorForOperations {
    public void insert(int fromAccount,int toAccount,int sum,String comment,int id){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "INSERT INTO public.operations(from_account, to_account, sum, comment_for_operation, id) VALUES ("+fromAccount+"," +toAccount+"," +sum+","+comment+","+id+");";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    public void update(int beforeId ,int fromAccount,int toAccount,int sum,String comment,int id){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.operations_applied_settings" + " SET from_account="+fromAccount+", to_account="+toAccount + ", sum="+sum +", comment_for_operation="+comment +", id="+id +" WHERE id="+beforeId+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void delete(String condition){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.operations WHERE " + condition+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

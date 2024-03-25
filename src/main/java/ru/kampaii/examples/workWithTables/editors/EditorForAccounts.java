package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class EditorForAccounts {
    public void insert(int number,int balance,int type,int userId){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "INSERT INTO public.accounts(\"number\", balance, type, user_id) VALUES ("+number+","+ balance+","+ type+","+ userId+");";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void update(int beforeNumber ,int number,int balance,int type,int userId){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.accounts SET \"number\"="+number+","+ "balance="+balance+","+ "type="+type+","+ "user_id="+userId+" WHERE number="+beforeNumber+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void delete(String condition){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.accounts WHERE " + condition+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

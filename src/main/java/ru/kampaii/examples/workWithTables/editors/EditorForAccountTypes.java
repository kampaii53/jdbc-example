package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditorForAccountTypes {
      public  void insert(int Id,String name){
          try (var connection = DatabaseConnectorProvider.connect();
               var statement = connection.createStatement();
          ) {
              String update = "INSERT INTO public.account_types( id, name) VALUES ("+Id+","+ name+");";
              statement.executeQuery(update);
          } catch (SQLException e) {
              e.printStackTrace();

          }
      }

    public void update(int beforeId ,int Id,String name){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.account_types" + " SET id="+Id+", name="+name + " WHERE id="+beforeId+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void delete(String condition){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.account_types WHERE " + condition+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;

import java.sql.SQLException;

public class EditorForOperationsAppliedSettings {
    public void insert(int operationId,int settingId){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "INSERT INTO public.operations_applied_settings(operation_id, setting_id) VALUES ("+operationId+","+ settingId+");";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    public void update(int beforeOperationId ,int operationId,int settingId){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "UPDATE public.operations_applied_settings" + " SET operation_id="+operationId+", setting_id="+settingId + " WHERE operation_id="+beforeOperationId+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void delete(String condition){
        try (var connection = DatabaseConnectorProvider.connect();
             var statement = connection.createStatement();
        ) {
            String update = "DELETE FROM public.operations_applied_settings WHERE " + condition+";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

package ru.kampaii.examples.workWithTables.editors;

import ru.kampaii.examples.config.DatabaseConnectorProvider;
import ru.kampaii.examples.workWithTables.representers.Representer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editor {
    private List<String> namesOfStrings;
    private String tableName;

    public Editor(String nameOfTable) {
        this.tableName = nameOfTable;
        this.namesOfStrings = new ArrayList<>();
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement();) {
            ResultSet results = statement.executeQuery("SELECT * FROM " + nameOfTable);
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                String name = metaData.getColumnName(column);
                namesOfStrings.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void insert(Map<String, Object> data) {
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement();) {
            String insert = "INSERT INTO " + tableName + " (";
            for (int i = 0; i < namesOfStrings.size(); i++) {
                if (i != namesOfStrings.size() - 1) {
                    insert += namesOfStrings.get(i) + ",";
                } else {
                    insert += namesOfStrings.get(i);
                }

            }
            insert += ") VALUES (";
            for (int i = 0; i < namesOfStrings.size(); i++) {
                if (i != namesOfStrings.size() - 1) {
                    insert += data.get(namesOfStrings.get(i)) + ",";
                } else {
                    insert += data.get(namesOfStrings.get(i)) + ");";
                }
            }
            System.out.println(insert);
            statement.execute(insert);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        //для работы этого метода нужно передать ему Map, в которой строки будут добавляться как Map.put(Столбец в который мы хотим добавить,значение) пример работы в Main
    }

    public void update(HashMap<String, Object> data) {
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement();) {
            String update = "UPDATE " + tableName + " SET ";
            for (int i = 0; i < namesOfStrings.size(); i++) {
                if (i != namesOfStrings.size() - 1) {
                    update += namesOfStrings.get(i) + "=" + data.get(namesOfStrings.get(i)) + ",";
                } else {
                    update += namesOfStrings.get(i) + "=" + data.get(namesOfStrings.get(i));
                }
            }
            update += " WHERE " + data.get("columnForUpdate") + "=" + data.get("previous") + ";";
            statement.execute(update);
            System.out.println(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        //все также как и в insert, но также нужно добавить
        //     Map.put("lineForUpdate",название столбца который выступает по которому мы будем выбирать предыдущее значение);
        //     Map.put("previous",значение столбца строки которую мы будем менять); пример также в main
    }

    public void delete(String condition) {
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement();) {
            String update = "DELETE FROM " + tableName + " WHERE " + condition + ";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        //Надо передать в метод строку условия по которой будем удалять
    }

    public Representer getStringsByKey(Object key, String columnForSelect) {
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement();) {
            var results = statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + columnForSelect + "=" + key + ";");
            while (results.next()) {
                String newData = "";
                for (int i = 0; i < namesOfStrings.size(); i++) {
                    newData += namesOfStrings.get(i) + "=" + results.getString(i + 1) + " ";
                }
                representList.add(newData);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return new Representer(representList);
    }

    public Representer getAllString() {
        List<String> representList = new ArrayList();
        try (var connection = DatabaseConnectorProvider.connect(); var statement = connection.createStatement();) {
            var results = statement.executeQuery("SELECT * FROM " + tableName);
            while (results.next()) {
                String newData = "";
                for (int i = 0; i < namesOfStrings.size(); i++) {
                    newData += namesOfStrings.get(i) + "=" + results.getString(i + 1) + " ";
                }
                representList.add(newData);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return new Representer(representList);
    }

    public List<String> getNamesOfStrings() {
        return namesOfStrings;
    }
}

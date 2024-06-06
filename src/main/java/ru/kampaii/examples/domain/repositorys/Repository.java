package ru.kampaii.examples.domain.repositorys;

import ru.kampaii.examples.domain.entities.Entity;
import ru.kampaii.examples.domain.idGenerators.IdGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Repository<T extends Entity, ID> {

    String tableName;
    String primaryKey;
    List<String> namesOfStrings;
    Connection connection;
    IdGenerator idGenerator;
    Integer numOfPrimaryKey;

    /**
     * Получение обьекта из базы по Id
     *
     * @param id идентификатор обьекта
     * @return обьект, null если обьекта не найдено
     */
    public T getById(ID id) {
        Map<String, Object> representList = new HashMap<>();
        ResultSet results = null;
        try {
            results = executeQuery("SELECT * FROM " + tableName + " WHERE " + primaryKey + "=" + id + ";");
            while (results.next()) {
                for (int i = 0; i < namesOfStrings.size(); i++) {
                    representList.put(namesOfStrings.get(i), results.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (representList.size() > 0) {
            return makeT(representList);
        } else {
            return null;
        }
    }

    /**
     * Создает обьект в базе данных(сохраняет строку в базе данных)
     *
     * @param object обьект без заданного id
     * @return результат сохранения с получившимся id
     */
    public T create(T object) {
        ID id = makeNewId();
        Map<String, Object> data = getData(object);
        data.put(primaryKey, id);
        try {
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
            execute(insert);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return getById(id);
    }

    /**
     * удаление по id
     *
     * @param id
     */
    public void delete(ID id) {
        String update = "DELETE FROM " + tableName + " WHERE " + primaryKey + "=" + id + ";";
        try {
            execute(update);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обновление строки в базе данных
     *
     * @param object
     * @return получившуюся строку
     */
    public T update(T object) {
        String update = "UPDATE " + tableName + " SET ";
        Map<String, Object> data = getData(object);
        for (int i = 0; i < namesOfStrings.size(); i++) {
            if (i != namesOfStrings.size() - 1) {
                update += namesOfStrings.get(i) + "=" + data.get(namesOfStrings.get(i)) + ",";
            } else {
                update += namesOfStrings.get(i) + "=" + data.get(namesOfStrings.get(i));
            }
        }
        update += " WHERE " + primaryKey + "=" + data.get(primaryKey) + ";";
        try {
            execute(update);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getById((ID) data.get(numOfPrimaryKey));
    }

    public ResultSet executeQuery(String request) throws SQLException {
        var statement = connection.createStatement();
        var result = statement.executeQuery(request);
        return result;
    }

    private void execute(String request) throws SQLException {
        var statement = connection.createStatement();
        statement.execute(request);
    }

    public List createNamesOfStrings() {
        List<String> names = new ArrayList();
        try (var statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                String name = metaData.getColumnName(column);
                names.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return names;
    }

    int getNumOfLine(String nameOfLine) {
        int numOfLine = 0;
        for (int i = 0; i < namesOfStrings.size(); i++) {
            if (namesOfStrings.get(i).equals(nameOfLine)) {
                numOfLine = i;
            }
        }
        return numOfLine;
    }

    ID makeNewId() {
        return (ID) idGenerator.makeNewId();
    }

    abstract Map<String, Object> getData(T object);

    abstract T makeT(Map<String, Object> data);


}
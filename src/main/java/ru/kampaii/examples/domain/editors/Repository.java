package ru.kampaii.examples.domain.editors;

import ru.kampaii.examples.domain.representers.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T extends Entity, ID> {

    String tableName;
    String primaryKey;
    List<String> namesOfStrings;
    Connection connection;

    /**
     * Получение обьекта из базы по Id
     *
     * @param id идентификатор обьекта
     * @return обьект, null если обьекта не найдено
     */
    public T getById(ID id) {
        List<String> representList = new ArrayList();
        try (var statement = connection.createStatement();) {
            var results = statement.executeQuery("SELECT * FROM " + tableName + " WHERE " + primaryKey + "=" + id + ";");
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
        List<String> dataFromT = object.getData();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < dataFromT.size() + 2; i++) {
            if (i == getNumOfLine(primaryKey)) {
                data.add(String.valueOf(id));
            } else {
                data.add(dataFromT.get(0));
                dataFromT.remove(0);
            }
        }
        try (var statement = connection.createStatement();) {
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
                    insert += data.get(i) + ",";
                } else {
                    insert += data.get(i) + ");";
                }

            }
            System.out.println(insert);
            statement.execute(insert);
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
        try (var statement = connection.createStatement();) {
            String update = "DELETE FROM " + tableName + " WHERE " + primaryKey + "=" + id + ";";
            statement.executeQuery(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Обновление строки в базе данных
     *
     * @param object
     * @return получившуюся строку
     */
    public T update(T object) {
        List<String> data = object.getData();
        try (var statement = connection.createStatement();) {
            String update = "UPDATE " + tableName + " SET ";
            for (int i = 0; i < namesOfStrings.size(); i++) {
                if (i != namesOfStrings.size() - 1) {
                    update += namesOfStrings.get(i) + "=" + data.get(i) + ",";
                } else {
                    update += namesOfStrings.get(i) + "=" + data.get(i);
                }
            }
            update += " WHERE " + primaryKey + "=" + data.get(getNumOfLine(primaryKey)) + ";";
            statement.execute(update);
            System.out.println(update);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return getById((ID) data.get(getNumOfLine(primaryKey)));
    }

    int getNumOfLine(String nameOfLine) {
        int numOfLine = 0;
        for (int i = 0; i < namesOfStrings.size(); i++) {
            if (namesOfStrings.get(i) == nameOfLine) {
                numOfLine = i;
            }
        }
        return numOfLine;
    }

    abstract T makeT(List list);

    abstract ID makeNewId();

}

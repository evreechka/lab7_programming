package Server.Commands;

import Client.CommandObject;
import Server.Collection.City;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.CollectionDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды remove_by_id-Удаление элементов по id
 */

public class RemoveByIdCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public RemoveByIdCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("remove_by_id",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(RemoveByIdCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject CO) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        long id = Long.parseLong(CO.getOption());
        if (coll.getSize() != 0) {
            for (City city : coll.getCollection()) {
                if (city.getIdOfCity() == id) {
                    CollectionDB.removeIDBD(id, CO.getLogin());
                    coll.remove_by_id(id, CO.getLogin());
                    return "Команда remove_by_id выполнена. Элемент из коллекции с id " + id + " удален";
                }
            }
            return "Элемента с таким id нет. Введите команду \"show\", чтобы увидеть элементы коллекции и их id.";
        } else {
            return "Команда remove_by_id не выполнена. Коллекция пуста";
        }
    }
}
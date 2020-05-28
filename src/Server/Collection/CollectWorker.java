package Server.Collection;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Класс, работающий с коллекцией объектов класса Server.Server.Server.Commands.AddCommand.City со свойствами date, collection, file, creationDate, area, population, metersAboveSeaLevel, capital, climate, government, governor.
 */
public class CollectWorker {
    /**
     * Поле дата создания коллекции
     */
    private static Date date;

    /** Статический блок инициализации*/
    static {
        date = new Date();
    }


    private static List<City> collection;
    public CollectWorker(List<City> collection){
        this.collection=collection;
    }

    public List<City> getCollection() {
        return collection;
    }

    /**
     * Функция очистки коллекции
     */
    public void clear(String user) {
        collection.removeIf(x -> x.getUser().equals(user));
    }//для clear

    /**
     * Функция удаления последнего элемента коллекции
     */
    public void remove_last(String user) throws SQLException {
        remove_by_id(get_id_last(),user);
    }//для remove_last

    /**
     * Функция удаления определенного элемента
     *
     * @param index- индекс элемента
     */
    public void removeElement(int index) {
        collection.remove(index);
    }//для update, remove_all_by_meters_above_sea_level,remove_by_id

    /**
     * Функция получения определенного элемента коллекции
     *
     * @param meters_above_sea_level- индекс элемента
     * @return объект класса Server.Server.Server.Commands.AddCommand.City
     */
    public String remove_by_meters_above_sea_level(int meters_above_sea_level,String user) {
        boolean checker=false;
        for (City c:collection){
            if (c.getMetersAboveSeaLevel()==meters_above_sea_level){
                checker=true;
            }
        }
        if (checker){
        collection.removeIf(city->city.getMetersAboveSeaLevel()==meters_above_sea_level&&city.getUser().equals(user));
        return "Команда remove_all_by_meters_above_sea_level выполнена, все объекты с полем meters_above_sea_level, равным "+meters_above_sea_level+" удалены";
    }
        else {
            return "Команда remove_all_by_meters_above_sea_level не выполнена, в коллекции нет объектов с полем meters_above_sea_level, равным " + meters_above_sea_level;
        }
        }

    /**
     * Функция группировки коллекции по полю population
     */
    public String group_counting_by_population() {
        if (collection.size()==0){
            return "Команда group_counting_by_population не выполнена. Коллекция пуста, групировка элементов невозможна";
        }
        else {
            StringBuilder sb=new StringBuilder();
            sb.append("Команда group_counting_by_population выполнена. Количество элементов полученных групп элементов коллекции:").append("\n");
            Map<Integer, Long> collectionPerPopulation = collection.stream().collect(groupingBy(city -> city.getPopulation(), counting()));
            sb.append(collectionPerPopulation.toString());
            return sb.toString();
        }
    }//for group_counting_by_population
//
//    /**
//     * Функция заполнения коллекции данными из файла
//     *
//     * @throws IOException if <code>filereader</code> does not exist
//     */
//    public void fromFileToColl(File file) throws IOException {
//        try {
//            Gson gson = new Gson();
//            FileReader filereader = new FileReader(file);
//            Scanner scanFile = new Scanner(filereader);
//            while (scanFile.hasNextLine()) {
//                collection.add(gson.fromJson(scanFile.nextLine(), City.class));
//            }
//            filereader.close();
//        } catch (IOException e) {
//            System.out.println("Файл не существует, заполнение коллекции невозможно, поэтому коллекция пуста");
//        }
//
//    }
//
//    /**
//     * Функция сохранения коллекции в файл
//     */
//    public String saveCollection(File file) throws IOException {
//        Gson gson = new Gson();
//        if (file.canWrite()) {
//            FileOutputStream filewriter = new FileOutputStream(file);
//            collection.forEach(city -> {
//                try {
//                    filewriter.write(gson.toJson(city).getBytes());
//                    filewriter.write("\n".getBytes());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            filewriter.close();
//            return "Коллекция сохранена в файл" + file.getName();
//        } else {
//            return "Запись файла невозможна. Поменяйте права доступа";
//        }
//    }//for save


    /**
     * Функция получения всех элементов коллекции
     */
    public void setCollection(LinkedList<City> newColl) {
        collection = newColl;
    }//show

    public String info() {
        return "Информация о коллекции (тип, дата инициализации, количество элементов):\n" + collection.getClass().getTypeName() + "\n" + date.toString() + "\n" + collection.size();
    }

    public String show() {
        if (collection.size() == 0) {
            return "Команда show не выполнена. Коллекция пустая";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Команда show выполнена. Список элементов коллекции:").append("\n");
            collection.stream().sorted(new NameComparator()).collect(Collectors.toCollection(LinkedList::new)).forEach(city -> {
                sb.append(city.toString()).append("\n");
            });
            return sb.toString();
        }
    }
    public String print_ascending(){
       if (collection.size()==0){
           return "Команда print_ascending не выполнена. Коллекция пуста, сортировка невозможна";
       }
       else{
           StringBuilder sb=new StringBuilder();
           sb.append("Команда print_ascending выполнена. Коллекция, отсортированная по возрастанию поля-население города:").append("\n");
           collection.stream().sorted(new CityComparator()).collect(Collectors.toCollection(LinkedList::new)).forEach(city -> {
               sb.append(city.toString()).append("\n");
           });
           return sb.toString();
       }
    }
    public String sort(){
        if (collection.size()==0){
            return "Команда sort не выполнена. Коллекция пуста, сортировка невозможна";
        }
        else{
            StringBuilder sb=new StringBuilder();
            Collections.sort(collection,new AreaComparartor());
            sb.append("Команда sort выполнена. Коллекция отсортирована по возрастанию значения поля-площадь:").append("\n");
            collection.forEach(city -> {
                sb.append(city.toString()).append("\n");
            });
            return sb.toString();
        }
    }
    public String history(ControlUnit pusk){
        return pusk.getListCommand();
    }
    public void add(City city){
        collection.add(city);
    }
    public int get_by_id(long id) {
        City city = null;
        for (City c : collection) {
            if (c.getIdOfCity() == id) {
                city = c;
            }
        }
        return collection.indexOf(city);
    }
    public int getSize(){
        return collection.size();
    }
    public void remove_by_id(long id,String user) throws SQLException {
        if (collection.size()!=0) {
            int index = get_by_id(id);
            if (collection.get(index).getUser().equals(user)) {
                collection.remove(index);
            }
        }
    }
    public long get_id_last(){
        long max=0;
        for (City c:collection){
            if (c.getIdOfCity()>max){
                max=c.getIdOfCity();
            }
        }
        return max;
    }
    public void update(City city,String user) throws SQLException {
        remove_by_id(city.getIdOfCity(),user);
        collection.add(city);
    }
    public static void end_clear(){
        collection.clear();
    }
}

package DEV130_4_2_Tekiev;

import java.sql.*;
import java.time.LocalDate;

public class DataBase {


    private static final String url = "jdbc:mysql://localhost:3306/store_on_sofa";
    private static final String user = "root";
    private static final String password = "root";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static DataBase dataBase;
    private static boolean isWriterConnected;
    private static boolean isReaderConnected;
    private DataBase() {
    }
    public synchronized static DataBase getInstance() {
        if (dataBase == null) dataBase = new DataBase();
        return dataBase;
    }
    public synchronized void readFromDataBase(int ID) throws InterruptedException {
        Thread.sleep((int) (Math.random() * 2000));
        System.out.println("Читатель " + Thread.currentThread().getName() + " желает подлючиться к базе данных");
        wait((int) (Math.random() * 1000));
        if (ID > 0 && !isWriterConnected) {
            try (Connection connection = DriverManager.getConnection(url, user, password); Statement statement = connection.createStatement()) {
                isReaderConnected = true;
                System.out.println("Читатель " + Thread.currentThread().getName() + " подключился к базе данных");
                wait((int) (Math.random() * 1000));
                resultSet = statement.executeQuery("select orders.id 'Идентификатор заказа', data_order 'Дата создания заказа', name_buyer 'ФИО заказчика', phone_number 'Контактный телефон', email 'Адрес эл. почты', adress 'Адрес доставки', status 'Статус заказа', data_ship 'Дата отгрузки товара', art_order 'Артикул', price_order 'Цена', values_order 'Количество'   from store_on_sofa.orders left join store_on_sofa.posisions on orders.id = cod_order where posisions.cod_order = '" + ID + "';");
                while (resultSet.next()) {
                    System.out.println("Читатель " + Thread.currentThread().getName() + " читает заказ из базы данных с ID = " + ID + " - " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + (resultSet.getString(4) == null ? "" : resultSet.getString(4)) + " " + (resultSet.getString(5) == null ? "" : resultSet.getString(5)) + " " + resultSet.getString(6) + " " + resultSet.getString(7) + " " + (resultSet.getString(8) == null ? "" : resultSet.getString(8)) + " " + resultSet.getString(9) + " " + resultSet.getInt(10) + " " + resultSet.getInt(11));
                }
                resultSet.close();
            } catch (SQLException sqlException) {
                System.out.println(sqlException);
            }
            isReaderConnected = false;
            System.out.println("Читатель " + Thread.currentThread().getName() + " отключился от базы данных");
        }
    }
    public synchronized void writeToDataBase() throws InterruptedException {
        Thread.sleep((int) (Math.random() * 2000));
        System.out.println("Писатель " + Thread.currentThread().getName() + " желает подлючиться к базе данных");
        wait((int) (Math.random() * 1000));
        if (!isWriterConnected && !isReaderConnected) {
            try (Connection connection = DriverManager.getConnection(url, user, password); Statement statement = connection.createStatement()) {
                System.out.println("Писатель " + Thread.currentThread().getName() + " подключился к базе данных");
                isWriterConnected = true;
                wait((int) (Math.random() * 1000));
                resultSet = statement.executeQuery("select  count(*) from store_on_sofa.orders;");
                int id = 0;
                while (resultSet.next()) {
                    id = resultSet.getInt(1) + 1;
                }
                String s = "INSERT INTO `store_on_sofa`.`orders` (`data_order`, `name_buyer`, `phone_number`,`email`, `adress`, `status`) VALUES ('" + LocalDate.now() + "','" + "Иванов Иван Иванович" + "', '" + " (812)001-32-43 " + "','" + "ivanov@yandex.ru" + "','" + "ул. Обручевых 9" + "', 'P');";
                statement.executeUpdate(s);
                statement.executeUpdate("INSERT INTO `store_on_sofa`.`posisions` (`cod_order`,`art_order`, `price_order`, `values_order`) VALUES ('" + id + " ','" + "3251617" + "', '" + 4000 + "', '" + 1 + "');");
                System.out.println("Писатель " + Thread.currentThread().getName() + " " + "создает заказ с ID = " + id + " " + LocalDate.now() + " " + "Иванов Иван Иванович" + " " + " (812)001-32-43 " + " " + "ivanov@yandex.ru" + " " + "ул. Обручевых 9" + " 'P' " + " в базе данных");
            } catch (SQLException sqlException) {
                System.out.println(sqlException);
            }
            isWriterConnected = false;
            System.out.println("Писатель " + Thread.currentThread().getName() + " отключился от базы данных");
        }
    }
    public void getAllOrders() {       // вывод всех заказов
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select orders.id 'Идентификатор заказа', data_order 'Дата создания заказа', name_buyer 'ФИО заказчика', phone_number 'Контактный телефон', email 'Адрес эл. почты', adress 'Адрес доставки', status 'Статус заказа', data_ship 'Дата отгрузки товара', art_order 'Артикул', price_order 'Цена', values_order 'Количество'   from store_on_sofa.orders left join store_on_sofa.posisions on orders.id = cod_order;");
            System.out.println("---------------------------Начало списка заказов базы данных-------------------------");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + (resultSet.getString(4) == null ? "" : resultSet.getString(4)) + " " + (resultSet.getString(5) == null ? "" : resultSet.getString(5)) + " " + resultSet.getString(6) + " " + resultSet.getString(7) + " " + (resultSet.getString(8) == null ? "" : resultSet.getString(8)) + " " + resultSet.getString(9) + " " + resultSet.getInt(10) + " " + resultSet.getInt(11));
            }
            System.out.println("---------------------------Конец списка заказов базы данных-------------------------");
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
    }

}

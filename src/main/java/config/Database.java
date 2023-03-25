package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к базе данных
 */
public class Database {

    // Поля для хранения параметров подключения
    private String url = "jdbc:postgresql://localhost:5432/BookingDb";
    private String user = "postgres";
    private String password = "123";

    // Конструктор без параметров
    public Database() {
    }

    // Метод для установки соединения с базой данных
    public Connection connect() {
        Connection connection = null;
        try {
            // Загружаем драйвер PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Получаем соединение с базой данных
            connection = DriverManager.getConnection(url, user, password);
            // Выводим сообщение об успешном подключении
            System.out.println("Connected to PostgreSQL database");
        } catch (ClassNotFoundException e) {
            // Обрабатываем исключение при отсутствии драйвера
            System.out.println("PostgreSQL JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            // Обрабатываем исключение при ошибке подключения
            System.out.println("Connection to PostgreSQL database failed");
            e.printStackTrace();
        }
        // Возвращаем соединение
        return connection;
    }

    // Метод для закрытия соединения с базой данных
    public void close(Connection connection) {
        if (connection != null) {
            try {
                // Закрываем соединение
                connection.close();
                // Выводим сообщение о закрытии соединения
                System.out.println("Connection to PostgreSQL database closed");
            } catch (SQLException e) {
                // Обрабатываем исключение при ошибке закрытия
                System.out.println("Closing connection to PostgreSQL database failed");
                e.printStackTrace();
            }
        }
    }
}
package service;

import config.Database;
import entity.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Класс для работы с таблицей Hotel
public class HotelService {

    // Поле для хранения объекта класса Database
    private Database database = new Database();

    // Конструктор без параметров
    public HotelService() {
    }

    // Метод для получения списка всех отелей из таблицы Hotel
    public List<Hotel> getAllHotel() {
        // Создаем пустой список для хранения результатов
        List<Hotel> hotels = new ArrayList<>();
        // Создаем строку с SQL-запросом
        String sql = "SELECT * FROM public.hotels;";
        // Получаем соединение с базой данных
        Connection connection = database.connect();
        // Проверяем, что соединение не равно null
        if (connection != null) {
            try {
                // Создаем объект PreparedStatement для выполнения SQL-запроса
                PreparedStatement statement = connection.prepareStatement(sql);
                // Выполняем SQL-запрос и получаем объект ResultSet с результатами
                ResultSet resultSet = statement.executeQuery();
                // Перебираем все строки в ResultSet
                while (resultSet.next()) {
                    // Получаем значения полей из текущей строки
                    int hotel_id = resultSet.getInt("hotel_id");
                    String hotel_name = resultSet.getString("hotel_name");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");
                    String country = resultSet.getString("country");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String website = resultSet.getString("website");
                    String description = resultSet.getString("description");
                    double rating = resultSet.getDouble("rating");
                    int num_rooms = resultSet.getInt("num_rooms");
                    String checkin_time = resultSet.getString("checkin_time");
                    String checkout_time = resultSet.getString("checkout_time");
                    String photo_link = resultSet.getString("photo_link");
                    // Создаем объект класса Hotel с полученными значениями
                    Hotel hotel = new Hotel(hotel_id, hotel_name, address, city, state, country, phone, email, website, description, rating, num_rooms, checkin_time, checkout_time, photo_link);
                    // Добавляем объект в список
                    hotels.add(hotel);
                }
                // Закрываем ResultSet
                resultSet.close();
                // Закрываем PreparedStatement
                statement.close();
            } catch (SQLException e) {
                // Обрабатываем исключение при работе с базой данных
                System.out.println("Error while executing SQL query");
                e.printStackTrace();
            } finally {
                // Закрываем соединение с базой данных
                database.close(connection);
            }
        }
        // Возвращаем список отелей
        return hotels;
    }
}

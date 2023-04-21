package repository;

import config.Database;
import entity.hotel.Hotel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс для отправки SQL запросов в базу данных для таблицы @hotels
 */
public class HotelRepository {
    private final Database database;

    public HotelRepository() {
        this.database = new Database();
    }

    public List<Hotel> getAllHotel() {
        List<Hotel> hotels = new ArrayList<>();
        try (Connection connection = database.connect();
             Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM hotels";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                UUID hotelId = UUID.fromString(rs.getString("hotel_id"));
                String country = rs.getString("country");
                String city = rs.getString("city");
                String description = rs.getString("description");
                String profileIcon = rs.getString("profile_icon");
                String phoneNumber = rs.getString("phone_number");
                String hotelName = rs.getString("hotel_name");
                String address = rs.getString("address");

                hotels.add(new Hotel(hotelId, hotelName, country, city, address, description, profileIcon, phoneNumber));
            }
        } catch (SQLException e) {
            // Обрабатываем исключение при работе с базой данных
            System.out.println("Error while executing SQL query");
            e.printStackTrace();
        }
        return hotels;
    }
}

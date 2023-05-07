package repository.hotel;

import config.ConnectionPool;
import entity.hotel.Hotel;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс отправки SQL запросов в таблицу @hotels
 */
public class HotelRepository {
    private static BasicDataSource dataSource = ConnectionPool.getDataSource();

    public List<Hotel> findAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels";

        try (Connection connection = ConnectionPool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                hotels.add(Hotel.fromResultSet(rs));
            }
        } catch (SQLException e) {
            // Обрабатываем исключение при работе с базой данных
            System.out.println("Error while executing SQL query");
            e.printStackTrace();
        }
        return hotels;
    }

    public List<Hotel> findHotelsByIds(List<UUID> hotelIds) {
        // создаем запрос, который выберет информацию об отелях с заданными идентификаторами
        String query = "SELECT * FROM hotels WHERE hotel_id IN (";
        for (int i = 0; i < hotelIds.size(); i++) {
            query += "?";
            if (i < hotelIds.size() - 1) {
                query += ",";
            }
        }
        query += ")";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // задаем параметры запроса
            for (int i = 0; i < hotelIds.size(); i++) {
                preparedStatement.setObject(i + 1, hotelIds.get(i));
            }

            List<Hotel> hotels = new ArrayList<>();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    hotels.add(Hotel.fromResultSet(rs));
                }
            }

            return hotels;
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return null;
    }

    public Hotel findHotelById(UUID hotelId) {
        String query = "SELECT * FROM hotels WHERE hotel_id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, hotelId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Hotel.fromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return null;
    }
}

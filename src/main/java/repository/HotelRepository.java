package repository;

import config.ConnectionPool;
import entity.hotel.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс отправки SQL запросов в таблицу @hotels
 */
public class HotelRepository {

    public List<Hotel> findAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM hotels";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                UUID hotelId = UUID.fromString(rs.getString("hotel_id"));
                UUID cityId = UUID.fromString(rs.getString("city_id"));
                String description = rs.getString("description");
                String profileIcon = rs.getString("profile_icon");
                String phoneNumber = rs.getString("phone_number");
                String hotelName = rs.getString("hotel_name");
                String address = rs.getString("address");

                hotels.add(new Hotel(hotelId, hotelName, address, description, profileIcon, phoneNumber, cityId));
            }
        } catch (SQLException e) {
            // Обрабатываем исключение при работе с базой данных
            System.out.println("Error while executing SQL query");
            e.printStackTrace();
        }
        return hotels;
    }

    public List<Hotel> findHotelsByIds(List<UUID> hotelIds) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {

            // создаем запрос, который выберет информацию об отелях с заданными идентификаторами
            String query = "SELECT * FROM hotels WHERE hotel_id IN (";
            for (int i = 0; i < hotelIds.size(); i++) {
                query += "?";
                if (i < hotelIds.size() - 1) {
                    query += ",";
                }
            }
            query += ")";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // задаем параметры запроса
            for (int i = 0; i < hotelIds.size(); i++) {
                preparedStatement.setObject(i + 1, hotelIds.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            // создаем список отелей и заполняем его результатами запроса
            List<Hotel> hotels = new ArrayList<>();
            while (resultSet.next()) {
                UUID hotelId = (UUID) resultSet.getObject("hotel_id");
                UUID cityId = (UUID) resultSet.getObject("city_id");
                String description = resultSet.getString("description");
                String profileIcon = resultSet.getString("profile_icon");
                String phoneNumber = resultSet.getString("phone_number");
                String hotelName = resultSet.getString("hotel_name");
                String address = resultSet.getString("address");
                Hotel hotel = new Hotel(hotelId, hotelName, address, description, profileIcon, phoneNumber, cityId);
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return null;
    }

    public Hotel findHotelById(UUID hotelId) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            String query = "SELECT * FROM hotels WHERE hotel_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UUID cityId = UUID.fromString(resultSet.getString("city_id"));
                String description = resultSet.getString("description");
                String profileIcon = resultSet.getString("profile_icon");
                String phoneNumber = resultSet.getString("phone_number");
                String hotelName = resultSet.getString("hotel_name");
                String address = resultSet.getString("address");
                return new Hotel(hotelId, hotelName, address, description, profileIcon, phoneNumber, cityId);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return null;
    }

}

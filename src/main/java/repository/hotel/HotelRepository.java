package repository.hotel;

import config.ConnectionPool;
import entity.hotel.Hotel;
import org.apache.commons.dbcp2.BasicDataSource;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс отправки SQL запросов в таблицу @hotels
 */
public class HotelRepository implements Repository<Hotel> {

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

    public void addWithConnection(Hotel hotel, Connection connection) {
        String query = "INSERT INTO hotels " +
                "(hotel_id, description, profile_icon_url, phone_number, hotel_name, address, city_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, hotel.getHotelId());
            statement.setString(2, hotel.getDescription());
            statement.setString(3, hotel.getProfileIconUrl());
            statement.setString(4, hotel.getPhoneNumber());
            statement.setString(5, hotel.getHotelName());
            statement.setString(6, hotel.getAddress());
            statement.setObject(7, hotel.getCityId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeById(UUID hotelId) {
        String query = "DELETE FROM hotels WHERE hotel_id = ?";
        // Подключаемся к базе данных и удаляем отель с указанным ID
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, hotelId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Hotel item) {
        String sql = "INSERT INTO public.hotels (description, profile_icon_url, phone_number, hotel_name, address, city_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getDescription());
            statement.setString(2, item.getProfileIconUrl());
            statement.setString(3, item.getPhoneNumber());
            statement.setString(4, item.getHotelName());
            statement.setString(5, item.getAddress());
            statement.setObject(6, item.getCityId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Hotel item) {
        String sql = "UPDATE public.hotels SET description = ?, profile_icon_url = ?, phone_number = ?, hotel_name = ?, address = ?, city_id = ? WHERE hotel_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getDescription());
            statement.setString(2, item.getProfileIconUrl());
            statement.setString(3, item.getPhoneNumber());
            statement.setString(4, item.getHotelName());
            statement.setString(5, item.getAddress());
            statement.setObject(6, item.getCityId());
            statement.setObject(7, item.getHotelId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Hotel item) {
        String sql = "DELETE FROM public.hotels WHERE hotel_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Hotel findById(UUID id) {
        Hotel hotel = null;
        String sql = "SELECT * FROM public.hotels WHERE hotel_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hotel = Hotel.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotel;
    }

    @Override
    public List<Hotel> findAll() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM public.hotels";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                hotels.add(Hotel.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }
}

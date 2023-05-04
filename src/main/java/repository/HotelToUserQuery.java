package repository;

import config.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HotelToUserQuery {

    public List<UUID> findHotelIdsByUserId(UUID userId) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {

            // создаем запрос, который выберет все hotel_id, соответствующие заданному user_id
            String query = "SELECT DISTINCT hotel_id FROM hotel_to_user WHERE user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // задаем параметры запроса
            preparedStatement.setObject(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<UUID> hotelIds = new ArrayList<>();
            // выводим результаты запроса
            while (resultSet.next()) {
                UUID hotelID = (UUID) resultSet.getObject("hotel_id");
                hotelIds.add(hotelID);
            }

            return hotelIds;
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return null;
    }
}

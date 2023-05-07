package repository.hotel;

import config.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HotelManagersQuery {

    public List<UUID> findHotelIdsByUserId(UUID userId) {
        String query = "SELECT DISTINCT hotel_id FROM hotel_managers WHERE user_id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<UUID> hotelIds = new ArrayList<>();
            preparedStatement.setObject(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    UUID hotelID = (UUID) resultSet.getObject("hotel_id");
                    hotelIds.add(hotelID);
                }
            }
            return hotelIds;
        } catch (SQLException e) {
            System.err.println("Ошибка выполнения запроса: " + e.getMessage());
        }
        return null;
    }
}

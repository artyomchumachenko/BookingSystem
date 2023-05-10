package repository.hotel;

import config.ConnectionPool;

import java.sql.*;
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

    public void addOwnerToHotelWithConnection(UUID ownerId, UUID hotelId, Connection connection) throws SQLException {
        String sql = "{call add_hotel_owner_to_managers (?, ?)}";
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setObject(1, hotelId);
            statement.setObject(2, ownerId);
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException("Failed to add owner to hotel", e);
        }
    }

}

package repository.user;

import config.ConnectionPool;
import entity.hotel.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserFavoriteHotelRepository {
    public void add(UUID userId, UUID hotelId) {
        String insertQuery = "INSERT INTO users_favorite_hotels (user_id, hotel_id, favorite_hotels_id) VALUES (?, ?, ?)";
        UUID favoriteHotelId = UUID.randomUUID();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setObject(1, userId);
            statement.setObject(2, hotelId);
            statement.setObject(3, favoriteHotelId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Hotel> findFavoriteHotelsByUserId(UUID userId) {
        List<Hotel> favoriteHotels = new ArrayList<>();
        String sql = "SELECT hotels.* FROM hotels " +
                "JOIN users_favorite_hotels ON hotels.hotel_id = users_favorite_hotels.hotel_id " +
                "WHERE users_favorite_hotels.user_id = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, userId);
            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    favoriteHotels.add(Hotel.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return favoriteHotels;
    }

    public void remove(UUID userId, UUID favoriteHotelId) {
        String sql = "DELETE FROM users_favorite_hotels WHERE user_id = ? AND hotel_id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, userId);
            stmt.setObject(2, favoriteHotelId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

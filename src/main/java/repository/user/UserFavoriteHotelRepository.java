package repository.user;

import config.ConnectionPool;
import entity.hotel.Hotel;
import entity.user.UserFavoriteHotel;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserFavoriteHotelRepository implements Repository<UserFavoriteHotel> {
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

    @Override
    public void add(UserFavoriteHotel item) {
        String sql = "INSERT INTO public.users_favorite_hotels (user_id, hotel_id) VALUES (?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getUserId());
            statement.setObject(2, item.getHotelId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserFavoriteHotel item) {
        String sql = "UPDATE public.users_favorite_hotels SET user_id = ?, hotel_id = ? WHERE favorite_hotels_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getUserId());
            statement.setObject(2, item.getHotelId());
            statement.setObject(3, item.getFavoriteHotelsId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(UserFavoriteHotel item) {
        String sql = "DELETE FROM public.users_favorite_hotels WHERE favorite_hotels_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getFavoriteHotelsId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserFavoriteHotel findById(UUID id) {
        UserFavoriteHotel userFavoriteHotel = null;
        String sql = "SELECT * FROM public.users_favorite_hotels WHERE favorite_hotels_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userFavoriteHotel = UserFavoriteHotel.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFavoriteHotel;
    }

    @Override
    public List<UserFavoriteHotel> findAll() {
        List<UserFavoriteHotel> userFavoriteHotels = new ArrayList<>();
        String sql = "SELECT * FROM public.users_favorite_hotels";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                userFavoriteHotels.add(UserFavoriteHotel.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFavoriteHotels;
    }
}

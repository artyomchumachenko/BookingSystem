package repository.hotel;

import config.ConnectionPool;
import entity.hotel.HotelManager;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HotelManagerRepository implements Repository<HotelManager> {

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

    /**
     * Добавление через SQL функцию нового менеджера для отеля с использованием транзакции (Connection)
     */
    public void addHotelManagerWithConnection(UUID managerId, UUID hotelId, Connection connection) throws SQLException {
        String sql = "{call add_hotel_owner_to_managers (?, ?)}";
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setObject(1, hotelId);
            statement.setObject(2, managerId);
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException("Failed to add owner to hotel", e);
        }
    }

    @Override
    public void add(HotelManager item) {
        String sql = "INSERT INTO public.hotel_managers (hotel_id, user_id, role_description) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.setObject(2, item.getUserId());
            statement.setString(3, item.getRoleDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(HotelManager item) {
        String sql = "UPDATE public.hotel_managers SET hotel_id = ?, user_id = ?, role_description = ? WHERE manager_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.setObject(2, item.getUserId());
            statement.setString(3, item.getRoleDescription());
            statement.setObject(4, item.getManagerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(HotelManager item) {
        String sql = "DELETE FROM public.hotel_managers WHERE manager_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getManagerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HotelManager findById(UUID id) {
        HotelManager hotelManager = null;
        String sql = "SELECT * FROM public.hotel_managers WHERE manager_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hotelManager = HotelManager.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelManager;
    }

    @Override
    public List<HotelManager> findAll() {
        List<HotelManager> hotelManagers = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_managers";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                hotelManagers.add(HotelManager.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelManagers;
    }
}

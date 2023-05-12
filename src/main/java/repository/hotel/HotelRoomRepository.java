package repository.hotel;

import config.ConnectionPool;
import entity.hotel.HotelRoom;
import repository.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class HotelRoomRepository implements Repository<HotelRoom> {

    /**
     * String название: Тип комнаты
     * Integer количество свободных мест
     * для @hotelId конкретного отеля
     */
    public Map<String, Integer> findFreeRoomsByHotelId(UUID hotelId) throws SQLException {
        String query = "SELECT r.name, hr.free_rooms " +
                "FROM hotel_rooms hr " +
                "JOIN rooms r ON hr.room_id = r.room_id " +
                "WHERE hr.hotel_id = ? AND hr.free_rooms > 0";

        try (PreparedStatement statement = ConnectionPool.getConnection().prepareStatement(query)) {
            Map<String, Integer> freeRoomsByRoomName = new HashMap<>();
            statement.setObject(1, hotelId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String roomName = resultSet.getString("name");
                    int freeRooms = resultSet.getInt("free_rooms");
                    freeRoomsByRoomName.put(roomName, freeRooms);
                }
            }
            return freeRoomsByRoomName;
        }
    }

    // метод для поиска цен на номера по ID отеля
    public Map<String, BigDecimal> findRoomPricesByHotelId(UUID hotelId) throws SQLException {
        Map<String, BigDecimal> prices = new HashMap<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getConnection();
            String query = "SELECT r.name, pr.adult_price_day " +
                    "FROM public.hotel_rooms hr " +
                    "JOIN public.rooms r ON r.room_id = hr.room_id " +
                    "JOIN public.price_rules pr ON pr.room_type_id = r.room_id " +
                    "WHERE hr.hotel_id = ? " +
                    "ORDER BY r.name ASC";
            stmt = connection.prepareStatement(query);
            stmt.setObject(1, hotelId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String roomName = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("adult_price_day");
                prices.put(roomName, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return prices;
    }

    public HotelRoom findFreeRoomsByHotelIdAndRoomId(UUID hotelId, UUID roomId) {
        String query = "SELECT * FROM hotel_rooms WHERE hotel_id = ? AND room_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, hotelId);
            statement.setObject(2, roomId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return HotelRoom.fromResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(HotelRoom item) {
        String sql = "INSERT INTO public.hotel_rooms (hotel_id, room_id, free_rooms) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.setObject(2, item.getRoomId());
            statement.setInt(3, item.getFreeRooms());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(HotelRoom item) {
        String sql = "UPDATE public.hotel_rooms SET hotel_id = ?, room_id = ?, free_rooms = ? WHERE hotel_room_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.setObject(2, item.getRoomId());
            statement.setInt(3, item.getFreeRooms());
            statement.setObject(4, item.getHotelRoomId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(HotelRoom item) {
        String sql = "DELETE FROM public.hotel_rooms WHERE hotel_room_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelRoomId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HotelRoom findById(UUID id) {
        HotelRoom hotelRoom = null;
        String sql = "SELECT * FROM public.hotel_rooms WHERE hotel_room_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hotelRoom = HotelRoom.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelRoom;
    }

    @Override
    public List<HotelRoom> findAll() {
        List<HotelRoom> hotelRooms = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_rooms";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                hotelRooms.add(HotelRoom.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelRooms;
    }
}

package repository.hotel;

import config.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotelRoomsQuery {

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
}

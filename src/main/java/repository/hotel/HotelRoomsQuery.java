package repository.hotel;

import config.ConnectionPool;

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
}

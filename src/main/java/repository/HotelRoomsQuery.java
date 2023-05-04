package repository;

import config.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotelRoomsQuery {

    public Map<String, Integer> findFreeRoomsByHotelId(UUID hotelId) throws SQLException {
        String query = "SELECT r.name, hr.free_rooms " +
                "FROM hotel_rooms hr " +
                "JOIN rooms r ON hr.room_id = r.room_id " +
                "WHERE hr.hotel_id = ? AND hr.free_rooms > 0";

        try (PreparedStatement statement = ConnectionPool.getDataSource().getConnection()
                                                         .prepareStatement(query)) {
            statement.setObject(1, hotelId);
            ResultSet resultSet = statement.executeQuery();

            Map<String, Integer> freeRoomsByRoomName = new HashMap<>();

            while (resultSet.next()) {
                String roomName = resultSet.getString("name");
                int freeRooms = resultSet.getInt("free_rooms");
                freeRoomsByRoomName.put(roomName, freeRooms);
            }

            return freeRoomsByRoomName;
        }
    }
}

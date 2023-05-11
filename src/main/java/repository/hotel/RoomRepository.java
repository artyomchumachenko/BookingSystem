package repository.hotel;

import config.ConnectionPool;
import entity.hotel.Room;

import java.sql.*;
import java.util.*;

public class RoomRepository {
//    private Connection connection;
//
//    public RoomRepository(Connection connection) {
//        this.connection = connection;
//    }

    public List<Room> all() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(Room.fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }
}

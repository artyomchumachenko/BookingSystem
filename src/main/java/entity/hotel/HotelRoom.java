package entity.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {
    private UUID hotelRoomId;
    private UUID hotelId;
    private UUID roomId;
    private int freeRooms;

    public static HotelRoom fromResultSet(ResultSet rs) throws SQLException {
        return HotelRoom.builder()
                .hotelRoomId(UUID.fromString(rs.getString("hotel_room_id")))
                .hotelId(UUID.fromString(rs.getString("hotel_id")))
                .roomId(UUID.fromString(rs.getString("room_id")))
                .freeRooms(rs.getInt("free_rooms"))
                .build();
    }
}

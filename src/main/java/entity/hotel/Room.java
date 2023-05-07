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
public class Room {
    private UUID roomId;
    private String name;
    private Integer peopleCapacity;
    private String description;

    public static Room fromResultSet(ResultSet rs) throws SQLException {
        return Room.builder()
                .roomId((UUID) rs.getObject("room_id"))
                .name(rs.getString("name"))
                .peopleCapacity(rs.getInt("people_capacity"))
                .description(rs.getString("description"))
                .build();
    }
}

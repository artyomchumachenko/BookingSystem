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
public class HotelManager {
    private UUID managerId;
    private UUID hotelId;
    private UUID userId;
    private String roleDescription;

    public static HotelManager fromResultSet(ResultSet rs) throws SQLException {
        return HotelManager.builder()
                .managerId(UUID.fromString(rs.getString("manager_id")))
                .hotelId(UUID.fromString(rs.getString("hotel_id")))
                .userId(UUID.fromString(rs.getString("user_id")))
                .roleDescription(rs.getString("role_description"))
                .build();
    }
}

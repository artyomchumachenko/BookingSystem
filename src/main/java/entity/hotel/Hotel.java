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
public class Hotel {
    private UUID hotelId;
    private String description;
    private String profileIconUrl;
    private String phoneNumber;
    private String hotelName;
    private String address;
    private UUID cityId;

    public static Hotel fromResultSet(ResultSet rs) throws SQLException {
        return Hotel.builder()
                .hotelId(UUID.fromString(rs.getString("hotel_id")))
                .description(rs.getString("description"))
                .profileIconUrl(rs.getString("profile_icon_url"))
                .phoneNumber(rs.getString("phone_number"))
                .hotelName(rs.getString("hotel_name"))
                .address(rs.getString("address"))
                .cityId(UUID.fromString(rs.getString("city_id")))
                .build();
    }
}

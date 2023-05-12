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
public class HotelFacility {
    private UUID hfId;
    private UUID hotelId;
    private UUID fId;
    private boolean payableService;

    public static HotelFacility fromResultSet(ResultSet rs) throws SQLException {
        return HotelFacility.builder()
                .hfId(UUID.fromString(rs.getString("h_f_id")))
                .hotelId(UUID.fromString(rs.getString("hotel_id")))
                .fId(UUID.fromString(rs.getString("f_id")))
                .payableService(rs.getBoolean("payable_service"))
                .build();
    }
}

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
public class HotelFacilities {
    private UUID hfId;
    private UUID hotelId;
    private UUID fId;
    private boolean payableService;

    public static HotelFacilities fromResultSet(ResultSet rs) throws SQLException {
        return HotelFacilities.builder()
                .hfId(UUID.fromString(rs.getString("h_f_id")))
                .hotelId(UUID.fromString(rs.getString("hotel_id")))
                .fId(UUID.fromString(rs.getString("f_id")))
                .payableService(rs.getBoolean("payable_service"))
                .build();
    }
}

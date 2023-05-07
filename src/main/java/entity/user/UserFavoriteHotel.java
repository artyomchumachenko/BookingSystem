package entity.user;

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
public class UserFavoriteHotel {
    private UUID favoriteHotelsId;
    private UUID userId;
    private UUID hotelId;

    public static UserFavoriteHotel fromResultSet(ResultSet rs) throws SQLException {
        return UserFavoriteHotel.builder()
                .favoriteHotelsId((UUID) rs.getObject("favorite_hotels_id"))
                .userId((UUID) rs.getObject("user_id"))
                .hotelId((UUID) rs.getObject("hotel_id"))
                .build();
    }
}

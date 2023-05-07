package entity.hotel;

import entity.booking.Booking;
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
public class Facility {
    private UUID id;
    private String name;
    private String description;

    /**
     * Создает объект Facility из ResultSet.
     *
     * @param rs ResultSet, содержащий результаты запроса.
     * @throws SQLException если произошла ошибка при работе с ResultSet.
     */
    public static Facility fromResultSet(ResultSet rs) throws SQLException {
        return Facility.builder()
                .id(UUID.fromString(rs.getString("f_id")))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}

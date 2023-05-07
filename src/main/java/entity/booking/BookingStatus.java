package entity.booking;

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
public class BookingStatus {
    private UUID statusId;
    private String name;

    /**
     * Создает объект BookingStatus из ResultSet.
     *
     * @param rs ResultSet, содержащий результаты запроса.
     * @return Объект BookingStatus, заполненный значениями из ResultSet.
     * @throws SQLException если возникает ошибка при чтении значений из ResultSet.
     */
    public static BookingStatus fromResultSet(ResultSet rs) throws SQLException {
        return BookingStatus.builder()
                .statusId(UUID.fromString(rs.getString("status_id")))
                .name(rs.getString("name"))
                .build();
    }
}

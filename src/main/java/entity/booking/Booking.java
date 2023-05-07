package entity.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private UUID bookingId;
    private UUID userId;
    private UUID hotelId;
    private UUID roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private int numGuests;
    private LocalDateTime bookingDate;
    private UUID statusId;

    /**
     * Создает объект Booking из ResultSet.
     *
     * @param rs ResultSet, содержащий результаты запроса.
     * @return Объект Booking, заполненный значениями из ResultSet.
     * @throws SQLException если возникает ошибка при чтении значений из ResultSet.
     */
    public static Booking fromResultSet(ResultSet rs) throws SQLException {
        return Booking.builder()
                .bookingId(UUID.fromString(rs.getString("booking_id")))
                .userId(UUID.fromString(rs.getString("user_id")))
                .hotelId(UUID.fromString(rs.getString("hotel_id")))
                .roomType(UUID.fromString(rs.getString("room_type")))
                .checkInDate(rs.getDate("check_in_date").toLocalDate())
                .checkOutDate(rs.getDate("check_out_date").toLocalDate())
                .totalPrice(rs.getBigDecimal("total_price"))
                .numGuests(rs.getInt("num_guests"))
                .bookingDate(rs.getTimestamp("booking_date").toLocalDateTime())
                .statusId(UUID.fromString(rs.getString("status_id")))
                .build();
    }
}

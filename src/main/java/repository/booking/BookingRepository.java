package repository.booking;

import config.ConnectionPool;
import entity.booking.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingRepository {
    public void save(Booking booking) {
        String sql = "INSERT INTO bookings (booking_id, user_id, hotel_id, room_type, check_in_date, check_out_date, " +
                "total_price, num_guests, status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, booking.getBookingId());
            stmt.setObject(2, booking.getUserId());
            stmt.setObject(3, booking.getHotelId());
            stmt.setObject(4, booking.getRoomType());
            stmt.setDate(5, Date.valueOf(booking.getCheckInDate()));
            stmt.setDate(6, Date.valueOf(booking.getCheckOutDate()));
            stmt.setBigDecimal(7, booking.getTotalPrice());
            stmt.setInt(8, booking.getNumGuests());
            stmt.setObject(9, booking.getStatusId());
            // Выполнение запроса
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> findByUserId(UUID userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(Booking.fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            // Обработка исключения
            System.out.println(e.getMessage());
        }
        return bookings;
    }
}

package repository.booking;

import config.ConnectionPool;
import entity.booking.Booking;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingRepository implements Repository<Booking> {

    @Override
    public void add(Booking item) {
        String sql = "INSERT INTO bookings (booking_id, user_id, hotel_id, room_type, check_in_date, check_out_date, " +
                "total_price, num_guests, status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, item.getBookingId());
            stmt.setObject(2, item.getUserId());
            stmt.setObject(3, item.getHotelId());
            stmt.setObject(4, item.getRoomType());
            stmt.setDate(5, Date.valueOf(item.getCheckInDate()));
            stmt.setDate(6, Date.valueOf(item.getCheckOutDate()));
            stmt.setBigDecimal(7, item.getTotalPrice());
            stmt.setInt(8, item.getNumGuests());
            stmt.setObject(9, item.getStatusId());
            // Выполнение запроса
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Booking item) {
        String query = "UPDATE bookings SET user_id = ?, hotel_id = ?, room_type = ?, check_in_date = ?, check_out_date = ?, total_price = ?, num_guests = ?, booking_date = ?, status_id = ? WHERE booking_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, item.getUserId());
            statement.setObject(2, item.getHotelId());
            statement.setObject(3, item.getRoomType());
            statement.setDate(4, Date.valueOf(item.getCheckInDate()));
            statement.setDate(5, Date.valueOf(item.getCheckOutDate()));
            statement.setBigDecimal(6, item.getTotalPrice());
            statement.setInt(7, item.getNumGuests());
            statement.setTimestamp(8, Timestamp.valueOf(item.getBookingDate()));
            statement.setObject(9, item.getStatusId());
            statement.setObject(10, item.getBookingId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Booking item) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, item.getBookingId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Booking findById(UUID id) {
        Booking booking = null;
        String query = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    booking = Booking.fromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                bookings.add(Booking.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
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

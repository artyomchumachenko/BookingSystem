package service.booking;

import entity.booking.Booking;
import repository.booking.BookingRepository;

import java.util.List;
import java.util.UUID;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService() {
        this.bookingRepository = new BookingRepository();
    }

    public void save(Booking booking) {
        bookingRepository.add(booking);
    }

    public List<Booking> getByUserId(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }
}

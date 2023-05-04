package service.hotel;

import entity.hotel.Hotel;
import repository.HotelRepository;
import repository.HotelToUserQuery;

import java.util.List;
import java.util.UUID;

public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService() {
        this.hotelRepository = new HotelRepository();
    }

    public List<Hotel> getAllHotel() {
        return hotelRepository.findAllHotels();
    }

    public List<UUID> getMyHotelsIdsByUserId(UUID userId) {
        HotelToUserQuery hotelToUserQuery = new HotelToUserQuery();
        return hotelToUserQuery.findHotelIdsByUserId(userId);
    }

    public List<Hotel> getMyHotelsByIds(List<UUID> uuids) {
        return hotelRepository.findHotelsByIds(uuids);
    }
}

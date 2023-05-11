package service.hotel;

import entity.hotel.Hotel;
import repository.hotel.HotelRepository;
import repository.hotel.HotelManagersQuery;

import java.sql.Connection;
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
        HotelManagersQuery hotelManagersQuery = new HotelManagersQuery();
        return hotelManagersQuery.findHotelIdsByUserId(userId);
    }

    public List<Hotel> getMyHotelsByIds(List<UUID> uuids) {
        return hotelRepository.findHotelsByIds(uuids);
    }

    public void create(Hotel hotel) {
        hotelRepository.add(hotel);
    }

    public void createWithConnection(Hotel hotel, Connection connection) {
        hotelRepository.addWithConnection(hotel, connection);
    }

    public void deleteById(UUID hotelId) {
        hotelRepository.remove(hotelId);
    }

    public Hotel getById(UUID hotelId) {
        return hotelRepository.findHotelById(hotelId);
    }

    public void updateHotel(Hotel hotel) {
        hotelRepository.update(hotel);
    }
}

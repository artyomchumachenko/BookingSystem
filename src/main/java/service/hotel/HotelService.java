package service.hotel;

import entity.hotel.Hotel;
import repository.hotel.HotelRepository;
import repository.hotel.HotelManagerRepository;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService() {
        this.hotelRepository = new HotelRepository();
    }

    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    public List<UUID> getMyHotelsIdsByUserId(UUID userId) {
        HotelManagerRepository hotelManagerRepository = new HotelManagerRepository();
        return hotelManagerRepository.findHotelIdsByUserId(userId);
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
        hotelRepository.removeById(hotelId);
    }

    public Hotel getById(UUID hotelId) {
        return hotelRepository.findById(hotelId);
    }

    public void updateHotel(Hotel hotel) {
        hotelRepository.update(hotel);
    }
}

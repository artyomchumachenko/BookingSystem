package service.hotel;

import entity.hotel.Hotel;
import repository.HotelRepository;

import java.util.List;

public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService() {
        this.hotelRepository = new HotelRepository();
    }

    public List<Hotel> getAllHotel() {
        return hotelRepository.getAllHotel();
    }
}

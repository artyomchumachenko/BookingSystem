package service;

import entity.Hotel;
import repository.HotelRepository;

import java.util.List;

public class HotelService {
    private HotelRepository hotelRepository;

    public HotelService() {
        this.hotelRepository = new HotelRepository();
    }

    public List<Hotel> getAllHotel() {
        return hotelRepository.getAllHotel();
    }
}

package service.user;

import entity.hotel.Hotel;
import entity.user.UserFavoriteHotel;
import repository.user.UserFavoriteHotelRepository;

import java.util.List;
import java.util.UUID;

public class UserFavoriteHotelService {
    private final UserFavoriteHotelRepository userFavoriteHotelRepository;

    public UserFavoriteHotelService() {
        this.userFavoriteHotelRepository = new UserFavoriteHotelRepository();
    }

    public void create(UserFavoriteHotel userFavoriteHotel) {
        userFavoriteHotelRepository.add(userFavoriteHotel);
    }

    public List<Hotel> getFavoriteHotelsByUserId(UUID userId) {
        return userFavoriteHotelRepository.findFavoriteHotelsByUserId(userId);
    }

    public void removeFavoriteHotelByUserId(UUID userId, UUID favoriteHotelId) {
        userFavoriteHotelRepository.remove(userId, favoriteHotelId);
    }

    public void delete(UserFavoriteHotel userFavoriteHotel) {
        userFavoriteHotelRepository.remove(userFavoriteHotel);
    }
}

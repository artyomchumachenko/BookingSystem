package service.user;

import entity.hotel.Hotel;
import repository.user.UserFavoriteHotelRepository;
import repository.user.WalletRepository;

import java.util.List;
import java.util.UUID;

public class UserFavoriteHotelService {
    private final UserFavoriteHotelRepository userFavoriteHotelRepository;

    public UserFavoriteHotelService() {
        this.userFavoriteHotelRepository = new UserFavoriteHotelRepository();
    }

    public void addFavoriteHotelByUserId(UUID userId, UUID hotelId) {
        userFavoriteHotelRepository.add(userId, hotelId);
    }

    public List<Hotel> getFavoriteHotelsByUserId(UUID userId) {
        return userFavoriteHotelRepository.findFavoriteHotelsByUserId(userId);
    }

    public void removeFavoriteHotelByUserId(UUID userId, UUID favoriteHotelId) {
        userFavoriteHotelRepository.remove(userId, favoriteHotelId);
    }
}

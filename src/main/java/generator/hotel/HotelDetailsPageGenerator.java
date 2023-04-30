package generator.hotel;

import entity.hotel.Hotel;
import repository.HotelRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Класс динамической генерации hotel-details.html
 */
public class HotelDetailsPageGenerator {

    /**
     * Подробности об отеле
     * TODO доделать
     */
    public String getHotelDetailsPage(String hotelId) throws IOException {
        // Get Hotel from Repository by Hotel UUID
        HotelRepository hotelRepository = new HotelRepository();
        Hotel hotel = hotelRepository.findHotelById(UUID.fromString(hotelId));

        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/hotel-details.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", hotel.getCountry());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить описание отеля.-->", hotel.getDescription());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить информацию о ценах и бронировании.-->", hotel.getCountry());

        // TODO добавить список платных и бесплатных удобств предлагаемых отелем

        return pageTemplate.replace("<!--Ссылка на фото отеля-->", hotel.getProfileIcon());
    }
}

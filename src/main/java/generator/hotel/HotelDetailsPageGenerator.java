package generator.hotel;

import entity.hotel.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HotelDetailsPageGenerator {
    /**
     * Подробности об отеле
     * TODO доделать
     */
    public String getHotelDetailsPage(List<Hotel> hotels, String buttonId) throws IOException {
        Hotel hotel = getHotelByButtonId(hotels, buttonId);
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/hotel-details.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", hotel.getName());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить описание отеля.-->", hotel.getDescription());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить информацию о ценах и бронировании.-->", hotel.getCountry());
        return pageTemplate.replace("<!--Ссылка на фото отеля-->", hotel.getProfileIcon());
    }

    private Hotel getHotelByButtonId(List<Hotel> hotels, String buttonId) {
        String digits = buttonId.replaceAll("\\D+", "");
        int id = Integer.parseInt(digits);
        return hotels.get(id - 2);
    }
}

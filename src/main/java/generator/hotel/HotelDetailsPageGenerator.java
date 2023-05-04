package generator.hotel;

import entity.country.Country;
import entity.hotel.Hotel;
import repository.CountryRepository;
import repository.HotelFacilitiesQuery;
import repository.HotelRepository;
import repository.HotelRoomsQuery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

/**
 * Класс динамической генерации hotel-details.html
 */
public class HotelDetailsPageGenerator {

    /**
     * Подробности об отеле
     * TODO доделать вывод страны в зависимости от города в котором расположен отель
     */
    public String getHotelDetailsPage(String hotelId) throws IOException {
        // Get Hotel from Repository by Hotel UUID
        HotelRepository hotelRepository = new HotelRepository();
        CountryRepository cr = new CountryRepository();

        Hotel hotel = hotelRepository.findHotelById(UUID.fromString(hotelId));
        Country country = cr.findCountryByCityId(hotel.getCityId());

        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/hotel-details.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", country.getName());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить описание отеля.-->", hotel.getDescription());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить информацию о ценах и бронировании.-->", country.getName());

        pageTemplate = pageTemplate.replace("// Удобства", getFacilities(hotel.getHotelId()));
        pageTemplate = pageTemplate.replace("// Данные об отеле", getHotelInfo(hotel));
        pageTemplate = pageTemplate.replace("// Номера", getInfoAboutRooms(hotel.getHotelId()));

        return pageTemplate.replace("<!--Ссылка на фото отеля-->", hotel.getProfileIcon());
    }

    private String getFacilities(UUID hotelId) {
        HotelFacilitiesQuery hfq = new HotelFacilitiesQuery();
        StringBuilder sb = new StringBuilder();

        Map<String, Boolean> facilities = hfq.findFacilitiesByHotelId(hotelId);

        for (String facilityKey : facilities.keySet()) {
            sb.append("<tr>\n");
            sb.append("<td>").append(facilityKey).append("</td>\n");
            sb.append("<td>");
            if (facilities.get(facilityKey)) sb.append("Да");
            else                             sb.append("Нет");
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }

        return sb.toString();
    }

    private String getInfoAboutRooms(UUID hotelId) {
        HotelRoomsQuery hrq = new HotelRoomsQuery();
        StringBuilder sb = new StringBuilder();

        Map<String, Integer> hotelRooms = null;
        try {
            hotelRooms = hrq.findFreeRoomsByHotelId(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (String roomName : hotelRooms.keySet()) {
            sb.append("<tr>\n");
            sb.append("<td>").append(roomName).append("</td>\n");
            sb.append("<td>");
            sb.append(hotelRooms.get(roomName));
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }

        return sb.toString();
    }

    private String getHotelInfo(Hotel hotel) {
        StringBuilder sb = new StringBuilder();
        CountryRepository cr = new CountryRepository();

        Country country = cr.findCountryByCityId(hotel.getCityId());

        sb.append("<li><strong>Страна:</strong> ").append(country.getName()).append("</li>\n");
        // TODO доделать getCityByCityId
        sb.append("<li><strong>Город:</strong> ").append(hotel.getCityId()).append("</li>\n");
        sb.append("<li><strong>Адрес:</strong> ").append(hotel.getAddress()).append("</li>\n");
        sb.append("<li><strong>Номер телефона:</strong> ").append(hotel.getPhoneNumber()).append("</li>\n");

        return sb.toString();
    }
}

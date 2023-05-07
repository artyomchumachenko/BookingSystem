package generator.hotel;

import entity.country.City;
import entity.country.Country;
import entity.hotel.Hotel;
import repository.country.CityRepository;
import repository.country.CountryRepository;
import repository.hotel.HotelFacilitiesQuery;
import repository.hotel.HotelRepository;
import repository.hotel.HotelRoomsQuery;

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

    private HotelRepository hotelRepository = new HotelRepository();
    private CountryRepository countryRepository = new CountryRepository();
    private CityRepository cityRepository = new CityRepository();
    private HotelFacilitiesQuery hotelFacilitiesQuery = new HotelFacilitiesQuery();
    private HotelRoomsQuery hotelRoomsQuery = new HotelRoomsQuery();

    /**
     * Подробности об отеле
     * TODO доделать вывод страны в зависимости от города в котором расположен отель
     */
    public String getHotelDetailsPage(String hotelId) throws IOException {
        Hotel hotel = hotelRepository.findHotelById(UUID.fromString(hotelId));
        Country country = countryRepository.findCountryByCityId(hotel.getCityId());

        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/hotel-details.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", hotel.getHotelName());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить описание отеля.-->", hotel.getDescription());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить информацию о ценах и бронировании.-->", country.getName());

        pageTemplate = pageTemplate.replace("// Удобства", getFacilities(hotel.getHotelId()));
        pageTemplate = pageTemplate.replace("// Данные об отеле", getHotelInfo(hotel));
        pageTemplate = pageTemplate.replace("// Номера", getInfoAboutRooms(hotel.getHotelId()));

        return pageTemplate.replace("<!--Ссылка на фото отеля-->", hotel.getProfileIconUrl());
    }

    private String getFacilities(UUID hotelId) {
        StringBuilder sb = new StringBuilder();

        Map<String, Boolean> facilities = hotelFacilitiesQuery.findFacilitiesByHotelId(hotelId);

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
        StringBuilder sb = new StringBuilder();

        Map<String, Integer> hotelRooms = null;
        try {
            hotelRooms = hotelRoomsQuery.findFreeRoomsByHotelId(hotelId);
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

        Country country = countryRepository.findCountryByCityId(hotel.getCityId());
        City city = cityRepository.findCityById(hotel.getCityId());

        sb.append("<li><strong>Страна:</strong> ").append(country.getName()).append("</li>\n");
        sb.append("<li><strong>Город:</strong> ").append(city.getName()).append("</li>\n");
        sb.append("<li><strong>Адрес:</strong> ").append(hotel.getAddress()).append("</li>\n");
        sb.append("<li><strong>Номер телефона:</strong> ").append(hotel.getPhoneNumber()).append("</li>\n");

        return sb.toString();
    }
}

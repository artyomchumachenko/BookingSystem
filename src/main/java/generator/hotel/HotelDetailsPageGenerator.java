package generator.hotel;

import config.CookieHelper;
import entity.country.City;
import entity.country.Country;
import entity.hotel.Hotel;
import repository.country.CityRepository;
import repository.country.CountryRepository;
import repository.hotel.HotelFacilitiesQuery;
import repository.hotel.HotelRepository;
import repository.hotel.HotelRoomsQuery;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

/**
 * Класс динамической генерации hotel-details.html
 */
public class HotelDetailsPageGenerator {

    private Hotel hotel;
    private Country country;
    private City city;
    private Map<String, Boolean> facilities;
    private Map<String, Integer> hotelRooms;
    private Map<String, BigDecimal> hotelRoomsPrices;
    private HttpServletRequest request;

    public HotelDetailsPageGenerator(Hotel hotel, Country country, City city,
                                     Map<String, Boolean> facilities, Map<String, Integer> hotelRooms,
                                     Map<String, BigDecimal> hotelRoomsPrices,
                                     HttpServletRequest request) {
        this.hotel = hotel;
        this.country = country;
        this.city = city;
        this.facilities = facilities;
        this.hotelRooms = hotelRooms;
        this.hotelRoomsPrices = hotelRoomsPrices;
        this.request = request;
    }

    public String getHotelDetailsPage() throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/page/html/hotel-details.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", hotel.getHotelName());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить описание отеля.-->", hotel.getDescription());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить информацию о ценах и бронировании.-->", country.getName());

        pageTemplate = pageTemplate.replace("// Удобства", getFacilities());
        pageTemplate = pageTemplate.replace("// Данные об отеле", getHotelInfo(hotel));
        pageTemplate = pageTemplate.replace("// Номера", getInfoAboutRooms());

        pageTemplate = pageTemplate.replace("<!--    Кнопка \"Забронировать\"-->", bookingButton(request));
        return pageTemplate.replace("<!--Ссылка на фото отеля-->", hotel.getProfileIconUrl());
    }

    private String bookingButton(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        CookieHelper cookieHelper = new CookieHelper();
        String role = cookieHelper.getTargetFromCookie(request, "role");
        if (role != null && role.equals("CLIENT")) {
            sb.append("<button id=\"book-button\" class=\"book-button\">Забронировать</button>");
        } else return "<!--    Кнопка \"Забронировать\"-->";
        return sb.toString();
    }

    private String getFacilities() {
        StringBuilder sb = new StringBuilder();

        for (String facilityKey : facilities.keySet()) {
            sb.append("<tr>\n");
            sb.append("<td>").append(facilityKey).append("</td>\n");
            sb.append("<td>");
            if (facilities.get(facilityKey)) sb.append("Да");
            else sb.append("Нет");
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }

        return sb.toString();
    }

    private String getInfoAboutRooms() {
        StringBuilder sb = new StringBuilder();

        for (String roomName : hotelRooms.keySet()) {
            sb.append("<tr>\n");
            sb.append("<td>").append(roomName).append("</td>\n");
            sb.append("<td>").append(hotelRooms.get(roomName)).append("</td>\n");
            if (hotelRoomsPrices.containsKey(roomName)) {
                sb.append("<td>").append(hotelRoomsPrices.get(roomName)).append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        return sb.toString();
    }

    private String getHotelInfo(Hotel hotel) {
        StringBuilder sb = new StringBuilder();

        sb.append("<li><strong>Страна:</strong> ").append(country.getName()).append("</li>\n");
        sb.append("<li><strong>Город:</strong> ").append(city.getName()).append("</li>\n");
        sb.append("<li><strong>Адрес:</strong> ").append(hotel.getAddress()).append("</li>\n");
        sb.append("<li><strong>Номер телефона:</strong> ").append(hotel.getPhoneNumber()).append("</li>\n");

        return sb.toString();
    }
}

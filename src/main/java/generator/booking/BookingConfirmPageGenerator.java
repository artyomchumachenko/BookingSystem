package generator.booking;

import entity.country.City;
import entity.country.Country;
import entity.hotel.Hotel;
import entity.hotel.Room;
import repository.country.CityRepository;
import repository.country.CountryRepository;
import repository.hotel.RoomRepository;
import service.hotel.HotelService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

public class BookingConfirmPageGenerator {

    public String getPage(HttpServletRequest request) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/page/html/booking-confirm.html"));
        pageTemplate = getBookingInfoFromRequest(pageTemplate, request);
        return pageTemplate;
    }

    private String getBookingInfoFromRequest(String page, HttpServletRequest request) {
        int numGuests = Integer.parseInt(request.getParameter("numGuests"));
        UUID roomTypeId = UUID.fromString(request.getParameter("roomId"));
        LocalDate checkInDate = LocalDate.parse(request.getParameter("checkInDate"));
        LocalDate checkOutDate = LocalDate.parse(request.getParameter("checkOutDate"));
        UUID hotelId = UUID.fromString(request.getParameter("hotelId"));
        String priceBuffer = request.getParameter("totalPrice");
        BigDecimal totalPrice = BigDecimal.valueOf(Double.parseDouble(priceBuffer));

        // Достаём все данные для формы "Информация о бронировании" Отель, Страна, Город, Номер
        HotelService hotelService = new HotelService();
        Hotel hotel = hotelService.getById(hotelId);
        CountryRepository countryRepository = new CountryRepository();
        Country country = countryRepository.findByCityId(hotel.getCityId());
        CityRepository cityRepository = new CityRepository();
        City city = cityRepository.findById(hotel.getCityId());
        RoomRepository roomRepository = new RoomRepository();
        Room room = roomRepository.findById(roomTypeId);

        StringBuilder sb = new StringBuilder();

        sb.append("value=\"").append(hotel.getHotelName()).append("\"");
        page = page.replace("value=\"Отель\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(country.getName()).append("\"");
        page = page.replace("value=\"Страна\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(city.getName()).append("\"");
        page = page.replace("value=\"Город\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(hotel.getAddress()).append("\"");
        page = page.replace("value=\"Адрес\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(hotel.getPhoneNumber()).append("\"");
        page = page.replace("value=\"Телефон\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"single\">").append(room.getName());
        page = page.replace("value=\"single\">Тип номера", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append((int) numGuests).append("\"");
        page = page.replace("value=\"Гостей\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(checkInDate.toString()).append("\"");
        page = page.replace("value=\"Дата заезда\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(checkOutDate.toString()).append("\"");
        page = page.replace("value=\"Дата отъезда\"", sb.toString());
        sb = new StringBuilder();

        sb.append("value=\"").append(totalPrice).append(" руб.").append("\"");
        page = page.replace("value=\"Итоговая цена\"", sb.toString());

        return page;
    }
}

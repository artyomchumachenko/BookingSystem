package servlet.hotel;

import entity.country.City;
import entity.country.Country;
import entity.hotel.Hotel;
import generator.hotel.HotelDetailsPageGenerator;
import repository.country.CityRepository;
import repository.country.CountryRepository;
import repository.hotel.HotelFacilitiesQuery;
import repository.hotel.HotelRepository;
import repository.hotel.HotelRoomsQuery;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

/**
 * "Подробнее" для какого-либо отеля
 */
@WebServlet(name = "DetailsServlet", urlPatterns = {"/details"})
public class DetailsServlet extends HttpServlet {

    private HotelRepository hotelRepository = new HotelRepository();
    private CountryRepository countryRepository = new CountryRepository();
    private CityRepository cityRepository = new CityRepository();
    private HotelFacilitiesQuery hotelFacilitiesQuery = new HotelFacilitiesQuery();
    private HotelRoomsQuery hotelRoomsQuery = new HotelRoomsQuery();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UUID hotelId = UUID.fromString(request.getParameter("hotelId"));

        Hotel hotel = hotelRepository.findHotelById(hotelId);
        Country country = countryRepository.findCountryByCityId(hotel.getCityId());
        City city = cityRepository.findCityById(hotel.getCityId());
        Map<String, Boolean> facilities = hotelFacilitiesQuery.findFacilitiesByHotelId(hotelId);
        Map<String, Integer> hotelRooms;
        Map<String, BigDecimal> hotelRoomsPrices;
        try {
            hotelRooms = hotelRoomsQuery.findFreeRoomsByHotelId(hotelId);
            hotelRoomsPrices = hotelRoomsQuery.findRoomPricesByHotelId(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HotelDetailsPageGenerator hotelDetailsPageGenerator = new HotelDetailsPageGenerator(
                hotel, country, city, facilities, hotelRooms, hotelRoomsPrices, request
        );

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelDetailsPageGenerator.getHotelDetailsPage());
    }
}

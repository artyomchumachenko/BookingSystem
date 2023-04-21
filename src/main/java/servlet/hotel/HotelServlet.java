package servlet.hotel;

import config.CookieHelper;
import entity.hotel.Hotel;
import generator.hotel.HotelPageGenerator;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * URL стартовой страницы
 * https://localhost:8443/BookingSystem_war/
 *
 * Главная страница сайта
 */
@WebServlet("")
public class HotelServlet extends HttpServlet {

    private final HotelService hotelService;

    public HotelServlet() {
        this.hotelService = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HotelPageGenerator hotelPageGenerator = new HotelPageGenerator();
        CookieHelper cookieHelper = new CookieHelper();

        List<Hotel> hotels = hotelService.getAllHotel();
        String usernameFromCookie = cookieHelper.getTargetFromCookie(request, "username");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelPageGenerator.getMainPage(
                hotels,
                usernameFromCookie
        ));
    }
}

package servlet;

import config.CookieHelper;
import entity.Hotel;
import generator.HotelPageGenerator;
import service.HotelService;

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
        String usernameFromCookie = cookieHelper.getUsernameFromCookie(request);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelPageGenerator.getMainPage(
                hotels,
                usernameFromCookie
        ));
    }
}

package servlet;

import entity.Hotel;
import generator.HotelPageGenerator;
import service.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * URL стартовой страницы
 * https://localhost:8443/BookingSystem_war/
 */
@WebServlet("")
public class HotelServlet extends HttpServlet {
    HotelPageGenerator hotelPageGenerator = new HotelPageGenerator();
    private HotelService hotelService = new HotelService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Hotel> hotels = hotelService.getAllHotel();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelPageGenerator.getMainPage(hotels));
    }
}

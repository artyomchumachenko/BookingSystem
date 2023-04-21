package servlet.hotel;

import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Мои отели
 * P.S. для пользователя с ролью "HOTELS"
 */
@WebServlet("/my-hotels")
public class MyHotelsServlet extends HttpServlet {
    private final HotelService hotelService;

    public MyHotelsServlet() {
        this.hotelService = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("html/my-hotels.html");
    }
}

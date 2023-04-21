package servlet.hotel;

import entity.hotel.Hotel;
import generator.hotel.HotelDetailsPageGenerator;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * "Подробнее" для какого-либо отеля
 */
@WebServlet(name = "DetailsServlet", urlPatterns = {"/details"})
public class DetailsServlet extends HttpServlet {

    private final HotelService hotelService;

    public DetailsServlet() {
        this.hotelService = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HotelDetailsPageGenerator hotelDetailsPageGenerator = new HotelDetailsPageGenerator();

        List<Hotel> hotels = hotelService.getAllHotel();
        String buttonId = request.getParameter("buttonId");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelDetailsPageGenerator.getHotelDetailsPage(hotels, buttonId));
    }
}

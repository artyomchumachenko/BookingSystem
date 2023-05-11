package servlet.booking;

import entity.hotel.Hotel;
import generator.booking.BookingPageGenerator;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BookingPageGenerator bookingPageGenerator = new BookingPageGenerator();
        UUID hotelId = UUID.fromString(request.getParameter("hotelId"));
        UUID userId = UUID.fromString(request.getParameter("userId"));

        HotelService hotelService = new HotelService();
        Hotel hotel = hotelService.getById(hotelId);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(bookingPageGenerator.getPage(hotel));
    }
}

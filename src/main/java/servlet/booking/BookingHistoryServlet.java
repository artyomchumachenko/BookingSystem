package servlet.booking;

import generator.booking.BookingHistoryPageGenerator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/booking-history")
public class BookingHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BookingHistoryPageGenerator bookingHistoryPageGenerator = new BookingHistoryPageGenerator();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(bookingHistoryPageGenerator.getPage());
    }
}

package servlet.booking;

import generator.booking.BookingConfirmPageGenerator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/booking-confirm")
public class BookingConfirmServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BookingConfirmPageGenerator bookingConfirmPageGenerator = new BookingConfirmPageGenerator();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(bookingConfirmPageGenerator.getPage(request));
    }
}

package servlet;

import entity.Hotel;
import generator.HotelPageGenerator;
import service.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DetailsServlet", urlPatterns = {"/details"})
public class DetailsServlet extends HttpServlet {
    HotelPageGenerator hotelPageGenerator = new HotelPageGenerator();
    HotelService hotelService = new HotelService();
    List<Hotel> hotels = hotelService.getAllHotel();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String buttonId = request.getParameter("buttonId");
        System.out.println(buttonId);
        Hotel hotel = hotelPageGenerator.getHotelByButtonId(hotels, buttonId);
        // здесь можно использовать buttonId для определения какая кнопка была нажата
        // и получить соответствующую информацию об отеле
        // Отправляем ответ
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelPageGenerator.getHotelDetails(hotel));
    }
}

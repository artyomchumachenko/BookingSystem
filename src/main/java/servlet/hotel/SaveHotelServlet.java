package servlet.hotel;

import entity.hotel.Hotel;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/save-hotel")
public class SaveHotelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем параметры запроса из formData
        String hotelName = request.getParameter("hotelName");
        String hotelAddress = request.getParameter("hotelAddress");
        String hotelDescription = request.getParameter("hotelDescription");
        String hotelIconUrl = request.getParameter("hotelIconUrl");
        String hotelPhone = request.getParameter("hotelPhone");
        String hotelCity = request.getParameter("hotelCity");
        String hotelId = request.getParameter("hotelId");

        // Обрабатываем полученные данные
        Hotel hotel = new Hotel(UUID.fromString(hotelId), hotelDescription, hotelIconUrl, hotelPhone, hotelName, hotelAddress, UUID.fromString(hotelCity));
        HotelService hotelService = new HotelService();
        hotelService.save(hotel);

        // Отправляем ответ клиенту
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Данные успешно сохранены в БД.");
    }
}

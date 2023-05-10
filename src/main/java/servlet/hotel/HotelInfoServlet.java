package servlet.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.hotel.Hotel;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/hotel-info")
public class HotelInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Получаем ID отеля, который нужно удалить из параметров запроса
        UUID hotelId = UUID.fromString(request.getParameter("hotelId"));
        System.out.println(hotelId);

        // Получаем список городов из базы данных
        HotelService hotelService = new HotelService();
        Hotel hotel = hotelService.getById(hotelId);

        // Преобразуем список городов в формат JSON и отправляем его клиенту
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), hotel);
        System.out.println("something");
    }
}

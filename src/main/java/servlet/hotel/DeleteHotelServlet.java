package servlet.hotel;

import service.hotel.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/delete")
public class DeleteHotelServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        // Получаем ID отеля, который нужно удалить из параметров запроса
        String hotelId = request.getParameter("hotelId");

        HotelService hotelService = new HotelService();
        hotelService.deleteById(UUID.fromString(hotelId));

        // Перенаправляем пользователя на страницу со списком отелей
        response.sendRedirect("/BookingSystem_war/my-hotels");
    }
}

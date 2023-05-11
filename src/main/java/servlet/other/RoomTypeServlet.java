package servlet.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.hotel.Room;
import repository.hotel.RoomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/room-types")
public class RoomTypeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Получаем список городов из базы данных
        RoomRepository roomRepository = new RoomRepository();
        List<Room> rooms = roomRepository.all();

        // Преобразуем список городов в формат JSON и отправляем его клиенту
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), rooms);
    }
}

package servlet.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.CookieHelper;
import config.LocalDateAdapter;
import config.LocalDateTimeAdapter;
import entity.booking.Booking;
import entity.hotel.Hotel;
import entity.hotel.Room;
import repository.hotel.RoomRepository;
import service.booking.BookingService;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Создайте новый объект, который содержит все списки
        Map<String, Object> data = new HashMap<>();

        // Получите данные из базы данных и сохраните их в списке
        BookingService bookingService = new BookingService();
        CookieHelper cookieHelper = new CookieHelper();
        UUID userId = UUID.fromString(cookieHelper.getTargetFromCookie(request, "user_uuid"));
        List<Booking> bookings = bookingService.getByUserId(userId);

        List<Hotel> hotels = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        HotelService hotelService = new HotelService();
        RoomRepository roomRepository = new RoomRepository();
        for (Booking booking : bookings) {
            hotels.add(hotelService.getById(booking.getHotelId()));
            rooms.add(roomRepository.findById(booking.getRoomType()));
        }

        data.put("bookings", bookings);
        data.put("hotels", hotels);
        data.put("rooms", rooms);
        // Конвертируйте список в JSON
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String json = gson.toJson(data);

        // Отправьте ответ клиенту
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}

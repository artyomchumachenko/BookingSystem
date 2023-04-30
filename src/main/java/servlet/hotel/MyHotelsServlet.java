package servlet.hotel;

import config.CookieHelper;
import entity.hotel.Hotel;
import generator.hotel.MyHotelsPageGenerator;
import service.hotel.HotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Мои отели
 * P.S. для пользователя с ролью "HOTELS"
 */
@WebServlet("/my-hotels")
public class MyHotelsServlet extends HttpServlet {
    private final HotelService hotelService;

    public MyHotelsServlet() {
        this.hotelService = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CookieHelper cookieHelper = new CookieHelper();
        MyHotelsPageGenerator pageGenerator = new MyHotelsPageGenerator();

        // Получить UUID пользователя
        System.out.println(cookieHelper.getTargetFromCookie(request, "user_uuid"));
        System.out.println(cookieHelper.getTargetFromCookie(request, "username"));
        UUID userId = UUID.fromString(cookieHelper.getTargetFromCookie(request, "user_uuid"));
        // Получить из таблицы hotel_to_user все hotel_id соответствующие user_id (UUID)
        List<UUID> hotelIds = hotelService.getMyHotelsIdsByUserId(userId);
        // По списоку UUID получить все отели из таблицы hotels
        List<Hotel> hotels = hotelService.getMyHotelsByIds(hotelIds);
        // Поместить их в список hotels и вывести на странице my-hotels

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(pageGenerator.getPage(hotels));
    }


}

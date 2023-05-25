package servlet.hotel;

import config.CookieHelper;
import entity.hotel.Hotel;
import entity.user.UserFavoriteHotel;
import generator.hotel.StartPageGenerator;
import service.hotel.HotelService;
import service.user.UserFavoriteHotelService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * URL стартовой страницы
 * https://localhost:8443/BookingSystem_war/
 * Главная страница сайта
 */
@WebServlet("")
public class StartPageServlet extends HttpServlet {

    private final HotelService hotelService;
    private StartPageGenerator pageGenerator = new StartPageGenerator();

    public StartPageServlet() {
        this.hotelService = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CookieHelper cookieHelper = new CookieHelper();
        String usernameFromCookie = cookieHelper.getTargetFromCookie(request, "username");
        UUID userIdFromCookie = null;
        HashMap<Hotel, Boolean> favoriteHotelsMap = new HashMap<>();
        List<Hotel> hotels = hotelService.getAllHotel();
        for (Hotel hotel : hotels) {
            favoriteHotelsMap.put(hotel, Boolean.FALSE);
        }
        if (usernameFromCookie != null) {
            userIdFromCookie = UUID.fromString(cookieHelper.getTargetFromCookie(request, "user_uuid"));
            UserFavoriteHotelService userFavoriteHotelService = new UserFavoriteHotelService();
            List<Hotel> favorites = userFavoriteHotelService.getFavoriteHotelsByUserId(userIdFromCookie);
            for (Hotel hotel : favorites) {
                favoriteHotelsMap.replace(hotel, Boolean.TRUE);
            }
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(pageGenerator.getPage(
                favoriteHotelsMap,
                usernameFromCookie
        ));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String hotelId = request.getParameter("hotelId").replace("favorite-", "");
        UUID favoriteHotelId = UUID.fromString(hotelId);
        System.out.println("FHid " + favoriteHotelId);
        UserFavoriteHotelService userFavoriteHotelService = new UserFavoriteHotelService();
        CookieHelper cookieHelper = new CookieHelper();
        UUID userId = UUID.fromString(cookieHelper.getTargetFromCookie(request, "user_uuid"));

        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            UserFavoriteHotel userFavoriteHotel = new UserFavoriteHotel(UUID.randomUUID(), userId, favoriteHotelId);
            userFavoriteHotelService.create(userFavoriteHotel);
        } else if (action != null && action.equals("remove")) {
            userFavoriteHotelService.removeFavoriteHotelByUserId(userId, favoriteHotelId);
        }

        // Отправить ответ на клиент, что действие выполнено успешно
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

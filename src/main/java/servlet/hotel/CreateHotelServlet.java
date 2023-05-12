package servlet.hotel;

import config.ConnectionPool;
import config.CookieHelper;
import entity.country.City;
import entity.hotel.Hotel;
import repository.country.CityRepository;
import repository.hotel.HotelManagerRepository;
import service.hotel.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/create-hotel")
public class CreateHotelServlet extends HttpServlet {
    private CityRepository cityRepository;
    private HotelService hotelRepository;

    public void init() {
        cityRepository = new CityRepository();
        hotelRepository = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<City> cities = cityRepository.all();

        request.setAttribute("cities", cities);

        request.getRequestDispatcher("page/html/create-hotel.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        UUID hotelId = UUID.randomUUID();
        String description = request.getParameter("description");
        String profileIconUrl = request.getParameter("profile_icon_url");
        String phoneNumber = request.getParameter("phone_number");
        String hotelName = request.getParameter("hotel_name");
        String address = request.getParameter("address");
        UUID cityId = UUID.fromString(request.getParameter("city"));
        Hotel hotel = new Hotel(hotelId, description, profileIconUrl, phoneNumber, hotelName, address, cityId);

        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            hotelRepository.createWithConnection(hotel, connection);

            // Подтверждение транзакции и завершение операции добавления отеля
            connection.commit();

            // Открытие новой транзакции для добавления менеджера отеля
            connection.setAutoCommit(false);

            CookieHelper cookieHelper = new CookieHelper();
            UUID managerId = UUID.fromString(cookieHelper.getTargetFromCookie(request, "user_uuid"));
            HotelManagerRepository hotelManagerRepository = new HotelManagerRepository();
            System.out.println("HotelId " + hotelId);
            System.out.println("ManagerId " + managerId);
            hotelManagerRepository.addHotelManagerWithConnection(managerId, hotelId, connection);

            // Подтверждение транзакции и завершение операции добавления менеджера отеля
            connection.commit();
        } catch (SQLException ex) {
            // Отмена всех операций в случае ошибки
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }
            }
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }


        response.sendRedirect(request.getContextPath() + "/my-hotels");
    }
}

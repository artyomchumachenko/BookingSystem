package servlet.hotel;

import entity.country.City;
import entity.hotel.Hotel;
import entity.hotel.PriceRule;
import repository.country.CityRepository;
import service.hotel.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/create-hotel")
public class CreateHotelServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CityRepository cityDAO;
    private HotelService hotelDAO;

    public void init() {
        cityDAO = new CityRepository();
        hotelDAO = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<City> cities = cityDAO.all();

        request.setAttribute("cities", cities);

        request.getRequestDispatcher("html/create-hotel.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String description = request.getParameter("description");
        String profileIconUrl = request.getParameter("profileIconUrl");
        String phoneNumber = request.getParameter("phoneNumber");
        String hotelName = request.getParameter("hotelName");
        String address = request.getParameter("address");
        UUID cityId = UUID.fromString(request.getParameter("cityId"));
        UUID priceRuleId = UUID.fromString(request.getParameter("priceRuleId"));

        Hotel hotel = new Hotel(UUID.randomUUID(), description, profileIconUrl, phoneNumber, hotelName, address, cityId, priceRuleId);

        hotelDAO.create(hotel);

        response.sendRedirect(request.getContextPath() + "/my-hotels");
    }
}

package servlet.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.country.City;
import repository.country.CityRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cities")
public class CityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Получаем список городов из базы данных
        CityRepository cityRepository = new CityRepository();
        List<City> cities = cityRepository.all();

        // Преобразуем список городов в формат JSON и отправляем его клиенту
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), cities);
    }
}

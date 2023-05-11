package servlet.booking;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculation-price")
public class CalculationPriceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем параметры из запроса
        String adults = request.getParameter("adults");
        System.out.println(adults);
        String children = request.getParameter("children");
        System.out.println(children);
        String roomType = request.getParameter("room-type");
        System.out.println(roomType);
        String checkInDate = request.getParameter("check-in-date");
        System.out.println(checkInDate);
        String checkOutDate = request.getParameter("check-out-date");
        System.out.println(checkOutDate);

        // Проверяем валидность данных (здесь можно реализовать свою логику)
        boolean isValid = true;
        if (checkInDate == null || checkInDate.isEmpty() || checkOutDate == null || checkOutDate.isEmpty()) {
            isValid = false;
        }

        // Если данные корректны, перенаправляем на страницу подтверждения бронирования
        if (isValid) {
//            response.sendRedirect("/BookingSystem_war/confirm");
            System.out.println("Information valid");
        } else {
            // В случае некорректных данных выводим ошибку пользователю
            response.getWriter().println("Invalid data");
        }
    }
}
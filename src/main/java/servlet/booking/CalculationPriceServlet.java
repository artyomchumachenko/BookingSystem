package servlet.booking;

import config.DateUtils;
import entity.hotel.HotelRoom;
import entity.hotel.PriceRule;
import repository.hotel.HotelRoomRepository;
import repository.hotel.PriceRuleRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/calculation-price")
public class CalculationPriceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем параметры из запроса
        UUID hotelId = UUID.fromString(request.getParameter("hotelId"));
        Integer adults = Integer.valueOf(request.getParameter("adults"));
        System.out.println(adults);
        Integer children = Integer.valueOf(request.getParameter("children"));
        System.out.println(children);
        UUID roomType = UUID.fromString(request.getParameter("room-type"));
        System.out.println(roomType);
        LocalDate checkInDate = LocalDate.parse(request.getParameter("check-in-date"));
        System.out.println(checkInDate);
        LocalDate checkOutDate = LocalDate.parse(request.getParameter("check-out-date"));
        System.out.println(checkOutDate);

        // Проверяем валидность данных (здесь можно реализовать свою логику)
        boolean isValid = true;
        HotelRoomRepository hotelRoomRepository = new HotelRoomRepository();
        PriceRuleRepository priceRuleRepository = new PriceRuleRepository();
        HotelRoom hotelRoom = hotelRoomRepository.findFreeRoomsByHotelIdAndRoomId(hotelId, roomType);
        PriceRule priceRule = priceRuleRepository.findPriceRuleByHotelIdAndRoomId(hotelId, roomType);
        int amountDays = DateUtils.getDaysBetween(checkInDate, checkOutDate);
        if (hotelRoom == null) {
            isValid = false;
        } else if (hotelRoom.getFreeRooms() <= 0) {
            isValid = false;
        }

        // Если данные корректны, перенаправляем на страницу подтверждения бронирования
        response.setCharacterEncoding("UTF-8");
        if (isValid) {
            response.getWriter().println(calculationTotalPrice(
                    priceRule.getAdultPricePerDay(),
                    priceRule.getKidPricePerDay(),
                    amountDays
            ));
        } else {
            // В случае некорректных данных выводим ошибку пользователю
            response.setStatus(201);
            response.getWriter().println("нет свободных комнат :(");
        }
    }

    private BigDecimal calculationTotalPrice(BigDecimal adultDayPrice, BigDecimal childrenDayPrice, int amountDays) {
        BigDecimal adultPrice = adultDayPrice.multiply(BigDecimal.valueOf(amountDays));
        BigDecimal childrenPrice = childrenDayPrice.multiply(BigDecimal.valueOf(amountDays));
        return adultPrice.add(childrenPrice);
    }
}
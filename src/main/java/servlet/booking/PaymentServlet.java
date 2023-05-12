package servlet.booking;

import entity.booking.Booking;
import entity.user.Wallet;
import service.booking.BookingService;
import service.user.WalletService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        UUID userId = UUID.fromString(request.getParameter("userId"));
        UUID hotelId = UUID.fromString(request.getParameter("hotelId"));
        UUID roomId = UUID.fromString(request.getParameter("roomId"));
        LocalDate checkInDate = LocalDate.parse(request.getParameter("checkInDate"));
        LocalDate checkOutDate = LocalDate.parse(request.getParameter("checkOutDate"));
        BigDecimal totalPrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("totalPrice")));
        int numGuests = Integer.parseInt(request.getParameter("numGuests"));

        WalletService walletService = new WalletService();
        Wallet userWallet = walletService.getWalletByUserId(userId);
        if (userWallet != null) {
            if (!userWallet.isFrozen() && userWallet.getBalanceRub().compareTo(totalPrice) >= 0) {
                // Снимаем деньги с кошелька пользователя и сохраняем сущность нового кошелька
                userWallet.setBalanceRub(userWallet.getBalanceRub().subtract(totalPrice));
                walletService.save(userWallet);
                // Отправляем данные о бронировании в таблицу bookings
                UUID statusPaid = UUID.fromString("813e874e-1fcc-4e0e-8f81-fed99d4a2d0a");
                Booking booking = new Booking(UUID.randomUUID(), userId, hotelId, roomId, checkInDate, checkOutDate, totalPrice, numGuests, LocalDateTime.now(), statusPaid);
                BookingService bookingService = new BookingService();
                bookingService.save(booking);
                response.getWriter().print("All is good!");
            } else response.getWriter().print("Not money!");
        } else response.getWriter().print("User wallet is not exist!");
    }
}

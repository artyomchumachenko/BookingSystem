package servlet.user;

import config.CookieHelper;
import service.user.WalletService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@WebServlet("/replenish")
public class ReplenishServlet extends HttpServlet {

    private final WalletService walletService;

    public ReplenishServlet() {
        this.walletService = new WalletService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("page/html/replenish.html").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cardNumber = request.getParameter("cardNumber");
        String cvcCode = request.getParameter("cvcCode");
        String amount = request.getParameter("amount");

        System.out.println("Card number: " + cardNumber);
        System.out.println("CVC code: " + cvcCode);
        System.out.println("Amount: " + amount);

        // Далее можно написать код, который будет обрабатывать данные и отправлять ответ клиенту
        CookieHelper cookieHelper = new CookieHelper();
        walletService.replenishBalance(UUID.fromString(cookieHelper.getTargetFromCookie(request, "user_uuid")), BigDecimal.valueOf(Double.parseDouble(amount)));
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("Операция выполнена успешно");
    }
}

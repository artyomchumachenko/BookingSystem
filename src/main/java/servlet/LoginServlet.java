package servlet;

import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Войти в личный кабинет
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("html/login.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем значения полей логина и пароля из запроса
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        tryToAuth(username, password, response);

        // Отправляем ответ
        response.setContentType("text/plain");
        response.getWriter().println("You is " + username + "?");
    }

    private void tryToAuth(String username, String password, HttpServletResponse response) {
        // Здесь можно добавить код для проверки логина и пароля
        User user = null;
        try {
            user = userService.authenticate(username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user != null) {
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(86400); // Установка времени жизни в 24 часа
            response.addCookie(cookie); // Добавление Cookie в ответ сервера
        } else {
            System.out.println("Drop message with Login Error");
        }
    }
}

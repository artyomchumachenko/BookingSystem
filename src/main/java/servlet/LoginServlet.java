package servlet;

import entity.User;
import repository.UserRepository;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
        this.userService = new UserService(new UserRepository());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("html/login.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем значения полей логина и пароля из запроса
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);

        // Здесь можно добавить код для проверки логина и пароля
        User user = null;
        try {
            user = userService.authenticate(username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user != null) {
            System.out.println("Send User to Cookie");
        } else {
            System.out.println("Drop message with Login Error");
        }

        // Отправляем ответ
        response.setContentType("text/plain");
        response.getWriter().println("You enter to system how " + username);
    }
}

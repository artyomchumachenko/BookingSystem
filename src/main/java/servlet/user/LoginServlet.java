package servlet.user;

import config.CookieHelper;
import entity.user.Role;
import entity.user.User;
import repository.user.RoleRepository;
import service.user.UserService;

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
        CookieHelper cookieHelper = new CookieHelper();
        cookieHelper.clearAllCookies(request, response);
        request.getRequestDispatcher("page/html/login.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем значения полей логина и пароля из запроса
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        tryToAuth(username, password, response);
    }

    private void tryToAuth(String username, String password,
                           HttpServletResponse response) throws IOException {
        // Здесь можно добавить код для проверки логина и пароля
        User user = null;
        try {
            user = userService.authenticate(username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/plain");
        if (user != null) {
            try {
                User currUser = userService.getUserByLogin(username);
                RoleRepository roleRepository = new RoleRepository();
                Role role = roleRepository.findById(user.getRoleId());
                Cookie cookieUsername = new Cookie("username", currUser.getLogin());
                Cookie cookieUserId = new Cookie("user_uuid", currUser.getUserId().toString());
                Cookie cookieRoleName = new Cookie("role", role.getName());
                cookieUsername.setMaxAge(86400); // Установка времени жизни в 24 часа
                cookieUserId.setMaxAge(86400); // Установка времени жизни в 24 часа
                cookieRoleName.setMaxAge(86400); // Установка времени жизни в 24 часа
                response.addCookie(cookieUsername); // Добавление Cookie в ответ сервера
                response.addCookie(cookieUserId); // Добавление Cookie в ответ сервера
                response.addCookie(cookieRoleName); // Добавление Cookie в ответ сервера
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Отправляем ответ
            response.getWriter().println("" + username + " enter to system");
        } else {
            // Отправляем ответ
            response.setStatus(204);
            response.getWriter().println("" + username + " - user is null");
        }
    }
}

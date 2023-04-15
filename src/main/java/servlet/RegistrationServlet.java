package servlet;

import entity.User;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Регистрация
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService;

    public RegistrationServlet() {
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("html/registration.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        tryToRegistration(username, request, response);
    }

    private void tryToRegistration(
            String username,
            HttpServletRequest request, HttpServletResponse response
    ) {
        response.setContentType("text/plain");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        try {
            if (userService.isLoginExist(username)) {
                // Отправляем ответ
                response.setStatus(204);
                response.getWriter().println("This user already exist");
            } else if (email.equals("")) {
                response.setStatus(205);
                response.getWriter().println("Email is null");
            }

            User user = new User(UUID.randomUUID(), username, password, email);
            userService.createUser(user);

            // Отправляем ответ
            response.getWriter().println("You registration with username = " + username);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
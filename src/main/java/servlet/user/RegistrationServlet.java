package servlet.user;

import entity.user.UserCredentials;
import service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        tryToRegistration(request, response);
    }

    private void tryToRegistration(
            HttpServletRequest request, HttpServletResponse response
    ) {
        response.setContentType("text/plain");
        UserCredentials userCredentials = new UserCredentials(
                request.getParameter("username"),
                request.getParameter("password"),
                request.getParameter("confirm")
        );
        String email = request.getParameter("email");
        try {
            if (!userService.isUserCreated(userCredentials, email)) {
                response.setStatus(204);
                response.getWriter().println("Error");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
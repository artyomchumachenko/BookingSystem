package servlet;

import config.CookieHelper;
import entity.User;
import generator.ProfilePageGenerator;
import service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Профиль
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    // TODO Доделать возможность выхода из профиля и очистки Cookie
    // Дальше по плану, описанному в файле Notepad

    private final UserService userService;

    public ProfileServlet() {
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProfilePageGenerator profilePageGenerator = new ProfilePageGenerator();
        CookieHelper cookieHelper = new CookieHelper();

        String usernameFromCookie = cookieHelper.getUsernameFromCookie(request);
        User user = tryToFindUserByLogin(usernameFromCookie);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(profilePageGenerator.getProfilePage(user));
//        request.getRequestDispatcher("html/profile.html").forward(request, response);
    }

    private User tryToFindUserByLogin(String username) {
        User user;
        try {
            user = userService.findByLogin(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

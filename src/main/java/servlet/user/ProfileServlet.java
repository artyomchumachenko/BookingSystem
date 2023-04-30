package servlet.user;

import config.CookieHelper;
import entity.user.User;
import generator.user.ProfilePageGenerator;
import service.user.UserService;

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
    private final UserService userService;

    public ProfileServlet() {
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProfilePageGenerator profilePageGenerator = new ProfilePageGenerator();
        CookieHelper cookieHelper = new CookieHelper();

        String usernameFromCookie = cookieHelper.getTargetFromCookie(request, "username");
        User user = tryToFindUserByLogin(usernameFromCookie);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(profilePageGenerator.getProfilePage(user));
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

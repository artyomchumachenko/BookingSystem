package servlet.user;

import config.CookieHelper;
import entity.user.User;
import generator.user.ProfilePageGenerator;
import repository.user.RoleRepository;
import service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String username = request.getParameter("username");

        // Обработка полученного имени пользователя
        User user = tryToFindUserByLogin(username);

        // Отправка ответа клиенту
        PrintWriter out = response.getWriter();
        if (user == null) {
            response.setStatus(201);
            out.println("notExist");
        } else {
            response.setStatus(200);
            RoleRepository roleRepository = new RoleRepository();
            String roleName = roleRepository.findRoleById(user.getRoleId()).getName();
            System.out.println(roleName);
            out.println(roleName);
        }
    }

    private User tryToFindUserByLogin(String username) {
        User user;
        try {
            user = userService.getUserByLogin(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

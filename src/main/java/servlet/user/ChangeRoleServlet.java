package servlet.user;

import config.CookieHelper;
import entity.user.Role;
import entity.user.User;
import repository.user.RoleRepository;
import service.user.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/change-role")
public class ChangeRoleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String username = request.getParameter("username");
        String role = request.getParameter("role");

        // Обработка полученных данных
        User user = null;
        try {
            UserService userService = new UserService();
            user = userService.getUserByLogin(username);
            if (user != null) {
                CookieHelper cookieHelper = new CookieHelper();
                RoleRepository roleRepository = new RoleRepository();
                Role roleEntity = roleRepository.findRoleByName(role);
                user.setRoleId(roleEntity.getRoleId());
                roleRepository.addLogAboutChangeRole(cookieHelper.getTargetFromCookie(request, "username"), user.getLogin(), roleEntity.getName());
                userService.save(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Отправка ответа клиенту
        PrintWriter out = response.getWriter();
        if (user == null) {
            response.setStatus(201);
            out.println("Пользователя нет в системе");
        } else {
            out.println("Роль пользователя " + username + " успешно изменена на " + role);
        }
    }
}

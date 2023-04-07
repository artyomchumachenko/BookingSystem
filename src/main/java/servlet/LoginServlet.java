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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;
        try {
            user = userService.authenticate(login, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user != null) {
            System.out.println("User is good!");
        } else {
            System.out.println("User is not good ;((");
        }
    }
}

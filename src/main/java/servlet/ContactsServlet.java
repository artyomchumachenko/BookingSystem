package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ContactsServlet", urlPatterns = {"/contacts"})
public class ContactsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Перенаправление на страницу contacts.html
        response.sendRedirect("html/contacts.html");
    }
}

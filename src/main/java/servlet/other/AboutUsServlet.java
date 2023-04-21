package servlet.other;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * "О нас"
 */
@WebServlet(name = "AboutUsServlet", urlPatterns = {"/info"})
public class AboutUsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("html/info.html");
    }
}

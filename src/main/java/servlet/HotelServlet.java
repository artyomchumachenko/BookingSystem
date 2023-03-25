package servlet;

import generator.HotelPageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class HotelServlet extends HttpServlet {
    HotelPageGenerator hotelPageGenerator = new HotelPageGenerator();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String filename="main-page.html";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath());*/

        // Отправляем ответ
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(hotelPageGenerator.getMainPage());
    }
}

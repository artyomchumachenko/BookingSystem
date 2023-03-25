package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hotels")
public class HotelServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/html/main-page.html");
        dispatcher.forward(request, response);
    }

    /*@WebServlet("/details")
    public static class HandleButtonClickServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // получение значения buttonId из запроса
            String buttonId = request.getParameter("buttonId");

            // здесь можно использовать значение buttonId и передать его в нужный класс Java
            System.out.println("Получено значение buttonId: " + buttonId);

            // отправка ответа обратно на клиент
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Запрос обработан успешно");
        }
    }*/
}

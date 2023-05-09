package config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CacheControlFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // Ничего не делаем при инициализации
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // Добавляем заголовок Cache-Control
        chain.doFilter(request, response);
    }

    public void destroy() {
        // Ничего не делаем при уничтожении
    }
}

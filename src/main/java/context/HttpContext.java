package context;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpContext {
    private final HttpServer httpServer;

    public HttpContext(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public void initNewContext(String path, String pageInStringForm) {
        httpServer.createContext(path, httpExchange -> {
            httpExchange.sendResponseHeaders(200, pageInStringForm.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(pageInStringForm.getBytes());
            os.close();
        });
    }
}

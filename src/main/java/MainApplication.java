import com.sun.net.httpserver.HttpServer;
import context.HttpContext;
import generator.HotelPageGenerator;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        HttpContext httpContext = new HttpContext(server);
        HotelPageGenerator hotelPageGenerator = new HotelPageGenerator();

        httpContext.initNewContext("/", hotelPageGenerator.getMainPage());
        httpContext.initNewContext("/details", hotelPageGenerator.getHotelDetails());
        server.setExecutor(null);
        System.out.println("Start HTTP Server");
        server.start();
    }
}

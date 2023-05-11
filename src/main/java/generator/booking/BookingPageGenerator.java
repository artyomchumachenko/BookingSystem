package generator.booking;

import entity.hotel.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BookingPageGenerator {

    public String getPage(Hotel hotel) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/page/html/booking.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", hotel.getHotelName());
        return pageTemplate;
    }
}

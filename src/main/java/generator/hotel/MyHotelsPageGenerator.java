package generator.hotel;

import entity.hotel.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс динамической генерации my-hotels.html
 */
public class MyHotelsPageGenerator {

    public String getPage(List<Hotel> hotels) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/my-hotels.html"));
        StringBuilder sb = new StringBuilder();
        for (Hotel hotel : hotels) {
            sb.append("{ \n")
                .append("name: \"").append(hotel.getCountry()).append("\",\n")
                .append("id: \"").append(hotel.getHotelId()).append("\",\n")
            .append("},").append("\n");
        }
        pageTemplate = pageTemplate.replace("// Объекты отелей", sb.toString());
        return pageTemplate;
    }
}

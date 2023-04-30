package generator.hotel;

import entity.hotel.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MyHotelsPageGenerator {

    public String getPage(List<Hotel> hotels) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/my-hotels.html"));
        StringBuilder sb = new StringBuilder();
        for (Hotel hotel : hotels) {
            sb.append("{ name: \"").append(hotel.getName()).append("\" },").append("\n");
        }
        pageTemplate = pageTemplate.replace("// { name: \"Отель 1\" },", sb.toString());
        return pageTemplate;
    }
}

package generator;

import entity.Hotel;
import service.HotelService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HotelPageGenerator {
    public String getMainPage() throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("src/main/webapp/html/main-page.html"));
        StringBuilder hotelListHtml = new StringBuilder();
        HotelService hotelService = new HotelService();
        List<Hotel> hotels = hotelService.getAllHotel();
        for (Hotel hotel : hotels) {
            hotelListHtml
                    .append("<li>")
                    .append(hotel.getHotel_name()).append(";\t")
                    .append(hotel.getCountry()).append(";\t")
                    .append(hotel.getCity()).append(";\t")
                    .append(hotel.getAddress()).append(";\t")
                    .append(hotel.getPhone()).append("\t")
                    .append("<button onclick=\"window.open('")
                    .append("http://localhost:8081/details")
                    .append("', '_blank')\">Подробнее</button>")
                    .append("</li>");
        }
        return mainPageTemplate.replace("<!-- HOTEL_LIST -->", hotelListHtml.toString());
    }

    public String getHotelDetails() throws IOException {
        return Files.readString(Paths.get("src/main/webapp/html/hotel-details.html"));
    }
}

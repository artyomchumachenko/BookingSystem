package generator;

import entity.Hotel;
import service.HotelService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Не работает должны образом
 */
public class HotelPageGenerator {
    HotelService hotelService = new HotelService();
    List<Hotel> hotels = hotelService.getAllHotel();

    public String getMainPage() throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/main-page.html"));
        StringBuilder hotelListHtml = new StringBuilder();
        int buttonId = 1;
        for (Hotel hotel : hotels) {
            hotelListHtml
                    .append("<li>")
                    .append(hotel.getHotel_name()).append(";\t")
                    .append(hotel.getCountry()).append(";\t")
                    .append(hotel.getCity()).append(";\t")
                    .append(hotel.getAddress()).append(";\t")
                    .append(hotel.getPhone()).append("\t")
                    .append("<button id=\"buttonId")
                    .append(buttonId)
                    .append("\" onclick=\"handleButtonClick(this.id)\">Подробнее</button>")
                    .append("</li>");
            buttonId++;
        }
        return mainPageTemplate.replace("<!-- HOTEL_LIST -->", hotelListHtml.toString());
    }

    public String getHotelDetails() throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/hotel-details.html"));
        StringBuilder hotelListHtml = new StringBuilder();
        return pageTemplate;
    }
}

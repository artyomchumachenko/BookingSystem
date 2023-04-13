package generator;

import entity.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс для динамической генерации главной страницы сайта
 */
public class HotelPageGenerator {
    public String getMainPage(List<Hotel> hotels) throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/main-page.html"));
        StringBuilder hotelListHtml = new StringBuilder();
        int buttonId = 2;
        for (Hotel hotel : hotels) {
            hotelListHtml
                    .append("<li>")
                    .append(hotel.getName()).append(";\t")
                    .append(hotel.getCountry()).append(";\t")
                    .append(hotel.getCity()).append(";\t")
                    .append(hotel.getAddress()).append(";\t")
                    .append(hotel.getPhoneNumber()).append("\t")
                    .append("<button id=\"buttonId")
                    .append(buttonId)
                    .append("\" onclick=\"handleButtonClick(this.id)\">Подробнее</button>")
                    .append("</li>");
            buttonId++;
        }
        return mainPageTemplate.replace("<!-- HOTEL_LIST -->", hotelListHtml.toString());
    }

    public String getHotelDetails(Hotel hotel) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/hotel-details.html"));
        pageTemplate = pageTemplate.replace("<!--        Название отеля-->", hotel.getName());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить описание отеля.-->", hotel.getDescription());
        pageTemplate = pageTemplate.replace("<!--        Здесь вы можете добавить информацию о ценах и бронировании.-->", hotel.getCountry());
        return pageTemplate.replace("<!--Ссылка на фото отеля-->", hotel.getProfileIcon());
    }

    public Hotel getHotelByButtonId(List<Hotel> hotels, String buttonId) {
        String digits = buttonId.replaceAll("\\D+", "");
        int id = Integer.parseInt(digits);
        return hotels.get(id - 2);
    }
}

package generator;

import entity.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс для динамической генерации главной страницы сайта
 * TODO перенести генерацию страницы "Подробнее об отеле" в другой класс
 */
public class HotelPageGenerator {

    /**
     * Генерации главной страницы сайта
     */
    public String getMainPage(List<Hotel> hotels, String usernameFromCookie) throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/main-page.html"));
        mainPageTemplate = hotelButtonsAllGenerate(hotels, mainPageTemplate);
        mainPageTemplate = profileButtonGenerate(usernameFromCookie, mainPageTemplate);
        return mainPageTemplate;
    }

    private String hotelButtonsAllGenerate(List<Hotel> hotels, String pageTemplate) {
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
        pageTemplate = pageTemplate.replace("<!-- HOTEL_LIST -->", hotelListHtml.toString());
        return pageTemplate;
    }

    private String profileButtonGenerate(String username, String pageTemplate) {
        if (username != null) {
            pageTemplate = pageTemplate.replace("<!--                Профиль-->", username);
            pageTemplate = pageTemplate.replace("href=\"/BookingSystem_war/login\"",
                    "href=\"/BookingSystem_war/profile\"");
        } else {
            pageTemplate = pageTemplate.replace("<!--                Профиль-->", "Войти");
        }
        return pageTemplate;
    }
}

package generator.hotel;

import entity.hotel.Hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Класс динамической генерации start-page.html
 */
public class StartPageGenerator {

    /**
     * Генерация главной страницы сайта
     */
    public String getPage(HashMap<Hotel, Boolean> hotels, String login) throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/page/html/start-page.html"));
        mainPageTemplate = hotelButtonsAllGenerate(hotels, mainPageTemplate);
        mainPageTemplate = profileButtonGenerate(login, mainPageTemplate);
        return mainPageTemplate;
    }

    private String hotelButtonsAllGenerate(HashMap<Hotel, Boolean> hotels, String pageTemplate) {
        StringBuilder hotelListHtml = new StringBuilder();

        for (Hotel hotel : hotels.keySet()) {
            hotelListHtml
                    .append("<li>")
                    .append("<div class=\"hotel\">")
                    .append("<img src=\"").append(hotel.getProfileIconUrl()).append("\" alt=\"Hotel image\" width=\"100\">")
                    .append("<div class=\"hotel-details\">")
                    .append("<h2 class=\"hotel-title\">").append(hotel.getHotelName()).append("</h2>")
                    .append("<div class=\"hotel-buttons\">")
                    .append("<button class=\"favorite-button\" ")
                    .append(" id=\"").append(hotel.getHotelId()).append("\" ")
                    .append("onclick=\"addToFavoriteHotelHandler(this.id)\"");

            if (!hotels.get(hotel)) {
                hotelListHtml.append(">").append("Добавить в избранное").append("</button>");
            } else {
                hotelListHtml.append(">").append("Удалить из избранного").append("</button>");
            }

            hotelListHtml
                    .append("<button")
                    .append(" id=\"").append(hotel.getHotelId()).append("\" ")
                    .append("onclick=\"hotelDetailsHandler(this.id)\">Подробнее</button>")
                    .append("</div>")
                    .append("</div>")
                    .append("</div>")
                    .append("</li>");

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

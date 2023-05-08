package generator.hotel;

import entity.hotel.Hotel;
import repository.country.CityRepository;
import repository.country.CountryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс динамической генерации start-page.html
 */
public class StartPageGenerator {

    /**
     * Генерация главной страницы сайта
     */
    public String getPage(List<Hotel> hotels, String usernameFromCookie) throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/start-page.html"));
        mainPageTemplate = hotelButtonsAllGenerate(hotels, mainPageTemplate);
        mainPageTemplate = profileButtonGenerate(usernameFromCookie, mainPageTemplate);
        return mainPageTemplate;
    }

    private String hotelButtonsAllGenerate(List<Hotel> hotels, String pageTemplate) {
        StringBuilder hotelListHtml = new StringBuilder();

        for (Hotel hotel : hotels) {
            hotelListHtml
                    .append("<li>")
                    .append("<div class=\"hotel\">")
                    .append("<img src=\"").append(hotel.getProfileIconUrl()).append("\" alt=\"Hotel image\" width=\"100\">")
                    .append("<div class=\"hotel-details\">")
                    .append("<h2 class=\"hotel-title\">").append(hotel.getHotelName()).append("</h2>")
                    .append("<div class=\"hotel-buttons\">")
                    .append("<button class=\"favorite-button\" ")
                    .append(" id=\"").append(hotel.getHotelId()).append("\" ")
                    .append("onclick=\"addToFavoriteHotelHandler(this.id)\"")
                    .append(">").append("Добавить в избранное").append("</button>")
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

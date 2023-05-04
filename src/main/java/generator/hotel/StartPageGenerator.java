package generator.hotel;

import entity.country.Country;
import entity.hotel.Hotel;
import repository.CountryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс динамической генерации main-page.html
 */
public class StartPageGenerator {

    /**
     * Генерация главной страницы сайта
     */
    public String getPage(List<Hotel> hotels, String usernameFromCookie) throws IOException {
        String mainPageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/main-page.html"));
        mainPageTemplate = hotelButtonsAllGenerate(hotels, mainPageTemplate);
        mainPageTemplate = profileButtonGenerate(usernameFromCookie, mainPageTemplate);
        return mainPageTemplate;
    }

    private String hotelButtonsAllGenerate(List<Hotel> hotels, String pageTemplate) {
        StringBuilder hotelListHtml = new StringBuilder();
        CountryRepository cr = new CountryRepository();

        for (Hotel hotel : hotels) {
            Country country = cr.findCountryByCityId(hotel.getCityId());
            hotelListHtml
                    .append("<li>")
//                    .append(hotel.getName()).append(";\t")
                    .append(country.getName()).append(";\t")
                    // TODO доделать cityByCityId
                    .append(hotel.getCityId()).append(";\t")
                    .append(hotel.getAddress()).append(";\t")
                    .append(hotel.getPhoneNumber()).append("\t")
                    .append("<button id=\"")
                    .append(hotel.getHotelId())
                    .append("\" onclick=\"handleButtonClick(this.id)\">Подробнее</button>")
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

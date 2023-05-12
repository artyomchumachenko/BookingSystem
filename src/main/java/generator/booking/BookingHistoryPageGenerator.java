package generator.booking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BookingHistoryPageGenerator {

    public String getPage() throws IOException {
        // Поднимаем образ страницы
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/page/html/booking-history.html"));
        // Обрабатываем его
        return pageTemplate;
    }
}

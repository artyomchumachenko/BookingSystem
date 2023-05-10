package generator.user;

import entity.user.User;
import entity.user.Wallet;
import service.user.UserService;
import service.user.WalletService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Класс динамической генерации profile.html
 */
public class ProfilePageGenerator {

    public String getProfilePage(User user) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/page/html/profile.html"));
        pageTemplate = profilePrivateInfoGenerate(pageTemplate, user);
        pageTemplate = infoAboutUserWallet(pageTemplate, user.getUserId());
        return pageTemplate;
    }

    private String profilePrivateInfoGenerate(String pageTemplate, User user) {
        UserService userService = new UserService();
        pageTemplate = pageTemplate.replace("<!--            John Doe-->", user.getLogin());
        pageTemplate = pageTemplate.replace("<!--            john.doe@gmail.com-->", user.getEmail());
        pageTemplate = pageTemplate.replace("<!--            +1 (555) 123-4567-->", user.getPassword());
        pageTemplate = pageTemplate.replace("<!--            Роль-->", userService.getRoleById(user.getRoleId()).getName());
        if (userService.getRoleById(user.getRoleId()).getName().equals("HOTEL")) {
            pageTemplate = pageTemplate.replace("<!--        Кнопка \"Мои отели\"-->",
                    "        <p>\n<button class=\"my-hotels\" id=\"my-hotels\" >Мои отели</button>\n</p>");
            pageTemplate = pageTemplate.replace("<!--        Добавить отель-->",
                    "        <p>\n<button class=\"add-my-hotel\" id=\"add-my-hotel\" >Добавить отель</button>\n</p>");
        } else if (userService.getRoleById(user.getRoleId()).getName().equals("ADMIN")) {
            StringBuilder sb = new StringBuilder();
            sb.append("<div class=\"change-role\">\n");
            sb.append("  <p>\n");
            sb.append("    <label>Пользователь:</label>\n");
            sb.append("    <input type=\"text\" id=\"username\" placeholder=\"Введите логин пользователя\">\n");
            sb.append("    <button class=\"check-user\" id=\"check-user\">Проверить</button>\n");
            sb.append("  </p>\n");
            sb.append("  <p>\n");
            sb.append("    <label>Роль:</label>\n");
            sb.append("    <select id=\"role\">\n");
            sb.append("      <option value=\"CLIENT\">Клиент</option>\n");
            sb.append("      <option value=\"HOTEL\">Отель</option>\n");
//            sb.append("      <option value=\"ADMIN\">Администратор</option>\n");
            sb.append("    </select>\n");
            sb.append("    <button class=\"change-role-button\" id=\"change-role-button\">Изменить роль</button>\n");
            sb.append("  </p>\n");
            sb.append("</div>\n");
            pageTemplate = pageTemplate.replace("<!--    change-role-->", sb.toString());
        }
        return pageTemplate;
    }

    private String infoAboutUserWallet(String pageTemplate, UUID userId) {
        WalletService walletService = new WalletService();
        Wallet wallet = walletService.getWalletByUserId(userId);
        String frozen;
        if (wallet.isFrozen()) frozen = "Заморожен";
        else frozen = "Активен";

        pageTemplate = pageTemplate.replace("неизвестен", frozen);
        pageTemplate = pageTemplate.replace("0 руб.", wallet.getBalanceRub().toString());
        return pageTemplate;
    }
}

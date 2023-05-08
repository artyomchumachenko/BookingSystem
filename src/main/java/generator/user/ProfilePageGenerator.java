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
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/profile.html"));
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
        }
        return pageTemplate;
    }

    private String infoAboutUserWallet(String pageTemplate, UUID userId) {
        WalletService walletService = new WalletService();
        Wallet wallet = walletService.getWalletByUserId(userId);
        String frozen;
        if (wallet.isFrozen()) frozen = "Заморожен"; else frozen = "Активен";

        pageTemplate = pageTemplate.replace("неизвестен", frozen);
        pageTemplate = pageTemplate.replace("0 руб.", wallet.getBalanceRub().toString());
        return pageTemplate;
    }
}

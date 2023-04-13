package generator;

import entity.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProfilePageGenerator {

    public String getProfilePage(User user) throws IOException {
        String pageTemplate = Files.readString(Paths.get("../webapps/BookingSystem_war/html/profile.html"));
        pageTemplate = profilePrivateInfoGenerate(pageTemplate, user);
        return pageTemplate;
    }

    private String profilePrivateInfoGenerate(String pageTemplate, User user) {
        pageTemplate = pageTemplate.replace("<!--            John Doe-->", user.getLogin());
        pageTemplate = pageTemplate.replace("<!--            john.doe@gmail.com-->", user.getEmail());
        pageTemplate = pageTemplate.replace("<!--            +1 (555) 123-4567-->", user.getPassword());
        return pageTemplate;
    }
}

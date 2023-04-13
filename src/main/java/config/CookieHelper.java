package config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieHelper {

    public String getUsernameFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String usernameFromCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    usernameFromCookie = cookie.getValue();
                    System.out.println(usernameFromCookie);
                }
            }
        }
        return usernameFromCookie;
    }
}

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

    public String getUserRoleNameFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String role = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    role = cookie.getValue();
                    System.out.println(role);
                }
            }
        }
        return role;
    }
}

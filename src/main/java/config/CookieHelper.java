package config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieHelper {

    public String getTargetFromCookie(HttpServletRequest request, String targetCookie) {
        Cookie[] cookies = request.getCookies();
        String target = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(targetCookie)) {
                    target = cookie.getValue();
                    System.out.println(target);
                }
            }
        }
        return target;
    }
}

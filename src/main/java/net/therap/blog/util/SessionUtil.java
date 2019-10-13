package net.therap.blog.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class SessionUtil implements Constants {

    public static boolean parameterManagement(HttpSession session, Model model) {
        long userId = (Long) session.getAttribute(USER_ID_PARAMETER);
        String userEmail = (String) session.getAttribute(USER_EMAIL_PARAMETER);
        String userFirstName = (String) session.getAttribute(USER_FIRST_NAME_PARAMETER);
        String userRole = (String) session.getAttribute(USER_ROLE_PARAMETER);
        if (userId != 0 || Objects.nonNull(userEmail) || Objects.nonNull(userFirstName) || Objects.nonNull(userRole)) {
            model.addAttribute(USER_ID_PARAMETER, userId);
            model.addAttribute(USER_EMAIL_PARAMETER, userEmail);
            model.addAttribute(USER_ROLE_PARAMETER, userRole);
            model.addAttribute(USER_FIRST_NAME_PARAMETER, userFirstName);
            return true;
        }
        return false;
    }

    public static String getUserRole(HttpSession session) {
        if (Objects.nonNull(session) && session.getAttribute(USER_ID_PARAMETER) != null) {
            long userId = (Long) session.getAttribute(USER_ID_PARAMETER);
            if (userId == 0) {
                return ACCESS_GUEST;
            } else {
                return (String) session.getAttribute(USER_ROLE_PARAMETER);
            }
        } else {
            return ACCESS_GUEST;
        }
    }
}

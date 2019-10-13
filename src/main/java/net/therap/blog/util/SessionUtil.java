package net.therap.blog.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class SessionUtil {

    public static boolean parameterManagement(HttpSession session, Model model) {
        long userId = (Long) session.getAttribute(Constants.USER_ID_PARAMETER);
        String userEmail = (String) session.getAttribute(Constants.USER_EMAIL_PARAMETER);
        String userFirstName = (String) session.getAttribute(Constants.USER_FIRST_NAME_PARAMETER);
        String userRole = (String) session.getAttribute(Constants.USER_ROLE_PARAMETER);
        if (userId != 0 || Objects.nonNull(userEmail) || Objects.nonNull(userFirstName) || Objects.nonNull(userRole)) {
            model.addAttribute(Constants.USER_ID_PARAMETER, userId);
            model.addAttribute(Constants.USER_EMAIL_PARAMETER, userEmail);
            model.addAttribute(Constants.USER_ROLE_PARAMETER, userRole);
            model.addAttribute(Constants.USER_FIRST_NAME_PARAMETER, userFirstName);
            return true;
        }
        return false;
    }

    public static String getUserRole(HttpSession session) {
        if (Objects.nonNull(session) && session.getAttribute(Constants.USER_ID_PARAMETER) != null) {
            long userId = (Long) session.getAttribute(Constants.USER_ID_PARAMETER);
            if (userId == 0) {
                return Constants.ACCESS_GUEST;
            } else {
                return (String) session.getAttribute(Constants.USER_ROLE_PARAMETER);
            }
        } else {
            return Constants.ACCESS_GUEST;
        }
    }
}

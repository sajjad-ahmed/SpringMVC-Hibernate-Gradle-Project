package net.therap.blog.util;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class SessionUtil implements Constants {

    public static String getUserRole(HttpSession session) {
        Object userIdInSession = session.getAttribute(USER_ID_PARAMETER);
        if (Objects.nonNull(session) && userIdInSession != null) {
            long userId = (Long) session.getAttribute(USER_ID_PARAMETER);
            return userId == 0 ? ACCESS_GUEST : (String) session.getAttribute(USER_ROLE_PARAMETER);
        } else {
            return ACCESS_GUEST;
        }
    }

    private boolean hasAccess(HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        return userRole.equals(ROLES.ADMIN.name());
    }
}

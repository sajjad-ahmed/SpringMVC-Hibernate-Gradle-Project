package net.therap.blog.util;

import net.therap.blog.domain.Post;
import net.therap.blog.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class SessionUtil implements Constants {

    public static String getUserRole(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        if (Objects.nonNull(session) && Objects.nonNull(user)) {
            long userId = user.getId();
            return userId == 0 ? ACCESS_GUEST : user.getRole().name();
        } else {
            return ACCESS_GUEST;
        }
    }

    public static List<Post> filterPostByRole(HttpSession session, List<Post> posts) {
        String userRole = SessionUtil.getUserRole(session);
        if (userRole.equals(ROLES.ADMIN.name())) {
            return posts;
        } else if (userRole.equals(ROLES.AUTHOR.name())) {
            posts.removeIf(i -> i.getStatus().name().equals("RESTRICT_AUTHOR"));
        } else if (userRole.equals(ROLES.SUBSCRIBER.name())) {
            posts.removeIf(i -> i.getStatus().name().equals("DRAFT"));
        } else if (userRole.equals(ACCESS_GUEST)) {
            posts.removeIf(i -> i.getStatus().name().equals("SUBSCRIBER_ONLY")
                    || i.getStatus().name().equals("DRAFT"));

        }
        return posts;
    }

    public static boolean isAdmin(HttpSession session) {
        return getUserRole(session).equals(ROLES.ADMIN.name());
    }
}

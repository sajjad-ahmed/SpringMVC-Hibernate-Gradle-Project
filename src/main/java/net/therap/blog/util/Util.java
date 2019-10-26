package net.therap.blog.util;

import net.therap.blog.domain.Category;
import net.therap.blog.domain.Post;
import net.therap.blog.domain.User;
import net.therap.blog.exception.NotFoundException;
import net.therap.blog.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class Util implements Constants {

    private static Map<String, List<Status>> roleStatusMap;

    static {
        Map<String, List<Status>> roleStatus = new HashMap<>();

        roleStatus.put(Role.ADMIN.name(),
                Arrays.asList(Status.PUBLIC,
                        Status.DRAFT,
                        Status.RESTRICT_AUTHOR,
                        Status.SUBSCRIBER_ONLY));

        roleStatus.put(Role.AUTHOR.name(),
                Arrays.asList(Status.PUBLIC,
                        Status.DRAFT,
                        Status.SUBSCRIBER_ONLY));

        roleStatus.put(Role.SUBSCRIBER.name(),
                Arrays.asList(Status.PUBLIC,
                        Status.SUBSCRIBER_ONLY));

        roleStatus.put(ACCESS_GUEST,
                Arrays.asList(Status.PUBLIC));

        roleStatusMap = Collections.unmodifiableMap(roleStatus);
    }

    public static String getUserRole(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        if (Objects.nonNull(session) && Objects.nonNull(user)) {
            long userId = user.getId();
            return userId == 0 ? ACCESS_GUEST : user.getRole().name();
        } else {
            return ACCESS_GUEST;
        }
    }

    public static List<Post> getPostByRole(HttpSession session, PostService postService) {
        String role = Util.getUserRole(session);
        List<Status> postStatues = roleStatusMap.get(role);
        return postService.findByStatus(postStatues);
    }

    public static boolean isAdmin(HttpSession session) {
        return getUserRole(session).equals(Role.ADMIN.name());
    }

    public static List<Post> getPostByRoleAndCategory(HttpSession session, PostService postService, long categoryId) {
        String role = Util.getUserRole(session);
        return postService.findByRoleAndCategory(categoryId, roleStatusMap.get(role));
    }
}

package red.niloy.blog.util;

import red.niloy.blog.domain.Post;
import red.niloy.blog.domain.User;
import red.niloy.blog.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class Util implements Constants {

    private static Map<Role, List<Status>> roleStatusMap;

    static {
        Map<Role, List<Status>> roleStatus = new HashMap<>();

        roleStatus.put(Role.ADMIN,
                Arrays.asList(Status.PUBLIC,
                        Status.DRAFT,
                        Status.RESTRICT_AUTHOR,
                        Status.SUBSCRIBER_ONLY));

        roleStatus.put(Role.AUTHOR,
                Arrays.asList(Status.PUBLIC,
                        Status.DRAFT,
                        Status.SUBSCRIBER_ONLY));

        roleStatus.put(Role.SUBSCRIBER,
                Arrays.asList(Status.PUBLIC,
                        Status.SUBSCRIBER_ONLY));

        roleStatus.put(Role.GUEST,
                Arrays.asList(Status.PUBLIC));

        roleStatusMap = Collections.unmodifiableMap(roleStatus);
    }

    public static Role getUserRole(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        if (Objects.nonNull(session) && Objects.nonNull(user)) {
            long userId = user.getId();
            return userId == 0 ? Role.GUEST : user.getRole();
        } else {
            return Role.GUEST;
        }
    }

    public static List<Post> getPostByRole(HttpSession session, PostService postService) {
        Role role = Util.getUserRole(session);
        List<Status> postStatues = roleStatusMap.get(role);
        return postService.findByStatus(postStatues);
    }

    public static boolean isAdmin(HttpSession session) {
        return getUserRole(session).name().equals(Role.ADMIN.name());
    }

    public static List<Post> getPostByRoleAndCategory(HttpSession session, PostService postService, long categoryId) {
        Role role = Util.getUserRole(session);
        return postService.findByRoleAndCategory(categoryId, roleStatusMap.get(role));
    }
}

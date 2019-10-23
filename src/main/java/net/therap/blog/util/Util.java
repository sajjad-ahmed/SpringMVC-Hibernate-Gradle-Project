package net.therap.blog.util;

import net.therap.blog.domain.Post;
import net.therap.blog.domain.User;
import net.therap.blog.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
public class Util implements Constants {

    private static Map<String, List<STATUS>> roleStatusMap;

    static {
        roleStatusMap = new HashMap<>();

        List<STATUS> statusList = new ArrayList<>();
        statusList.add(STATUS.PUBLIC);
        statusList.add(STATUS.DRAFT);
        statusList.add(STATUS.RESTRICT_AUTHOR);
        statusList.add(STATUS.SUBSCRIBER_ONLY);
        roleStatusMap.put(ROLES.ADMIN.name(), statusList);

        statusList = new ArrayList<>();
        statusList.add(STATUS.PUBLIC);
        statusList.add(STATUS.DRAFT);
        statusList.add(STATUS.SUBSCRIBER_ONLY);
        roleStatusMap.put(ROLES.AUTHOR.name(), statusList);

        statusList = new ArrayList<>();
        statusList.add(STATUS.PUBLIC);
        statusList.add(STATUS.SUBSCRIBER_ONLY);
        roleStatusMap.put(ROLES.SUBSCRIBER.name(), statusList);

        statusList = new ArrayList<>();
        statusList.add(STATUS.PUBLIC);
        roleStatusMap.put("GUEST", statusList);
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
        return postService.findByStatus(roleStatusMap.get(role));
    }

    public static boolean isAdmin(HttpSession session) {
        return getUserRole(session).equals(ROLES.ADMIN.name());
    }

    public static List<Post> getPostByRoleAndCategory(HttpSession session, PostService postService, long categoryId) {
        String role = Util.getUserRole(session);
        return postService.findByRoleAndCategory(categoryId, roleStatusMap.get(role));
    }
}

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

    private static Map<String, List<Status>> roleStatusMap;

    static {
        roleStatusMap = new HashMap<>();

        List<Status> statusList = new ArrayList<>();
        statusList.add(Status.PUBLIC);
        statusList.add(Status.DRAFT);
        statusList.add(Status.RESTRICT_AUTHOR);
        statusList.add(Status.SUBSCRIBER_ONLY);
        roleStatusMap.put(Role.ADMIN.name(), statusList);

        statusList = new ArrayList<>();
        statusList.add(Status.PUBLIC);
        statusList.add(Status.DRAFT);
        statusList.add(Status.SUBSCRIBER_ONLY);
        roleStatusMap.put(Role.AUTHOR.name(), statusList);

        statusList = new ArrayList<>();
        statusList.add(Status.PUBLIC);
        statusList.add(Status.SUBSCRIBER_ONLY);
        roleStatusMap.put(Role.SUBSCRIBER.name(), statusList);

        statusList = new ArrayList<>();
        statusList.add(Status.PUBLIC);
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

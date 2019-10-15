package net.therap.blog.web.controller;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.domain.Post;
import net.therap.blog.service.PostService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.ROLES;
import net.therap.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

import static net.therap.blog.util.URL.HOME_VIEW;
import static net.therap.blog.util.URL.ROOT;

/**
 * @author sajjad.ahmed
 * @since 9/19/19.
 */
@Controller
public class HomeController implements Constants {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PostService postService;

    @RequestMapping(value = ROOT, method = RequestMethod.GET)
    public String showHomePage(Model model, HttpSession session) {
        model.addAttribute(AVAILABLE_CATEGORIES, categoryDao.findAll());
        List<Post> posts = getPostByAccess(session);
        model.addAttribute("posts", posts);
        return HOME_VIEW;
    }

    private List<Post> getPostByAccess(HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        List<Post> posts = postService.findAll();
        if (userRole.equals(ROLES.ADMIN.name())) {
            return posts;
        } else if (userRole.equals(ROLES.AUTHOR.name())) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(0) == ACCESS_DENY);
        } else if (userRole.equals(ROLES.SUBSCRIBER.name())) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(1) == ACCESS_DENY);
        } else if (userRole.equals(ACCESS_GUEST)) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(2) == ACCESS_DENY);
        }
        return posts;
    }
}

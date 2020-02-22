package red.niloy.blog.web.controller;

import red.niloy.blog.dao.CategoryDao;
import red.niloy.blog.domain.Post;
import red.niloy.blog.service.PostService;
import red.niloy.blog.util.Constants;
import red.niloy.blog.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

import static red.niloy.blog.util.URL.HOME_VIEW;
import static red.niloy.blog.util.URL.ROOT;

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
        List<Post> posts = Util.getPostByRole(session, postService);
        model.addAttribute(AVAILABLE_CATEGORIES, categoryDao.findAll());
        model.addAttribute("posts", posts);
        return HOME_VIEW;
    }
}

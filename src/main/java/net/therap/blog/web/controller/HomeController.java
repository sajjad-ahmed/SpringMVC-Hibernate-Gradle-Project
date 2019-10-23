package net.therap.blog.web.controller;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.domain.Post;
import net.therap.blog.service.PostService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.Util;
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
        List<Post> posts = Util.getPostByRole(session, postService);
        model.addAttribute(AVAILABLE_CATEGORIES, categoryDao.findAll());
        model.addAttribute("posts", posts);
        return HOME_VIEW;
    }
}

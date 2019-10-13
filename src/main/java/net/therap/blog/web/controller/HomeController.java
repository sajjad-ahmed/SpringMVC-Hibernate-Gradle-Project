package net.therap.blog.web.controller;

import net.therap.blog.domain.Post;
import net.therap.blog.service.CategoryService;
import net.therap.blog.service.PostService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author sajjad.ahmed
 * @since 9/19/19.
 */
@Controller
public class HomeController implements Constants {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @RequestMapping(value = URL.ROOT, method = RequestMethod.GET)
    public String showHomePage(ModelMap modelMap, HttpSession session) {
        modelMap.put(AVAILABLE_CATEGORIES, categoryService.getAll());
        List<Post> posts = this.postService.getPostByAccess(session);
        modelMap.put("posts", posts);
        return URL.HOME_VIEW;
    }
}

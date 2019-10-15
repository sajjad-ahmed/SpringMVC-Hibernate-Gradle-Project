package net.therap.blog.web.controller;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.dao.CommentDao;
import net.therap.blog.domain.Category;
import net.therap.blog.domain.Comment;
import net.therap.blog.domain.Post;
import net.therap.blog.domain.User;
import net.therap.blog.service.PostService;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.ROLES;
import net.therap.blog.util.SessionUtil;
import net.therap.blog.web.editor.CategoryEditor;
import net.therap.blog.web.editor.PostEditor;
import net.therap.blog.web.editor.UserEditor;
import net.therap.blog.web.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static net.therap.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class PostController implements Constants {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private PostValidator postValidator;

    @InitBinder
    protected void initBinderMenu(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(List.class, "categories", new CategoryEditor(List.class));
        binder.registerCustomEditor(User.class, new UserEditor(userService));
        binder.registerCustomEditor(Post.class, new PostEditor(postService));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = POST_CREATE, method = RequestMethod.GET)
    public String cratePostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryDao.findAll());
        return POST_CREATE_VIEW;
    }

    @RequestMapping(value = POST_CREATE, method = RequestMethod.POST)
    public String createPostHandler(@Valid @ModelAttribute Post post,
                                    Errors errors,
                                    Model model,
                                    HttpSession session,
                                    @RequestParam("file") MultipartFile picture) {
        String userRole = SessionUtil.getUserRole(session);
        if (!(userRole.equals(ROLES.ADMIN.name()) || userRole.equals(ROLES.AUTHOR.name()))) {
            return ACCESS_ERROR_VIEW;
        }
        postValidator.validate(post, errors);
        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryDao.findAll());
            return POST_CREATE_VIEW;
        }
        if (!picture.isEmpty()) {
            try {
                byte[] bytes = picture.getBytes();
                post.setPicture(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        post.getCategories().forEach(i -> {
            Category category = categoryDao.find(i.getId());
            i.setName(category.getName());
        });
        postService.add(post);
        return "redirect:" + POST_MANAGE;
    }

    @RequestMapping(value = POST_MANAGE, method = RequestMethod.GET)
    public String postManagementHandler(Model model, HttpSession session) {
        List<Post> posts = getAccessiblePost(session);
        model.addAttribute("posts", posts);
        return POST_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = POST_SHOW)
    public String showSinglePost(@PathVariable("id") long id,
                                 Model model) {
        Post post = postService.find(id);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = POST_DELETE)
    public String postDeleteHandler(@PathVariable("id") long id) {
        Post post = postService.find(id);
        postService.delete(post.getId());
        return "redirect:" + POST_MANAGE;
    }

    @RequestMapping(value = POST_UPDATE)
    public String postUpdateHandler(@PathVariable("id") long id,
                                    Model model) {
        Post post = postService.find(id);
        model.addAttribute("post", post);
        model.addAttribute("roles", ROLES.values());
        return POST_CREATE_VIEW;
    }

    @RequestMapping(value = COMMENT_ADD, method = RequestMethod.POST)
    public String addCommentForm(@Valid @ModelAttribute Comment comment,
                                 Errors errors,
                                 Model model) {
        if (errors.hasErrors()) {
            Post post = comment.getPostId();
            model.addAttribute("post", post);
            model.addAttribute("comment", new Comment());
            model.addAttribute("comments", post.getComments());
            return SINGLE_POST_VIEW;
        }
        Post post = comment.getPostId();
        comment.setPostId(post);
        commentDao.save(comment);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = COMMENT_DELETE)
    public String commentDeleteHandler(@PathVariable("id") long id,
                                       Model model) {
        Comment comment = commentDao.find(id);
        long postId = comment.getPostId().getId();
        commentDao.delete(comment.getId());
        Post post = postService.find(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = COMMENT_UPDATE)
    public String commentUpdateHandler(@PathVariable("id") long id,
                                       Model model) {
        Comment comment = commentDao.find(id);
        long postId = comment.getPostId().getId();
        Post post = postService.find(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
        List<Comment> comments = post.getComments();
        comments.removeIf(i -> i.getId() == comment.getId());
        model.addAttribute("comments", comments);
        return SINGLE_POST_VIEW;
    }

    private List<Post> getAccessiblePost(HttpSession session) {
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


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
import java.util.Objects;

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
        postService.save(post);
        return "redirect:" + POST_MANAGE;
    }

    @RequestMapping(value = POST_MANAGE, method = RequestMethod.GET)
    public String postManagementHandler(Model model, HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        if (userRole.equals(ROLES.SUBSCRIBER.name())) {
            return ACCESS_ERROR_VIEW;
        }
        List<Post> posts = postService.findAll();
        if (Objects.nonNull(posts)) {
            posts = SessionUtil.filterPostByRole(session, posts);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        return POST_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = POST_SHOW, method = RequestMethod.GET)
    public String showSinglePost(@PathVariable("uri") String uri,
                                 Model model) {
        Post post = postService.findBy(uri);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = POST_DELETE, method = RequestMethod.POST)
    public String postDeleteHandler(@ModelAttribute Post post) {
        post = postService.find(post.getId());
        postService.delete(post.getId());
        return "redirect:" + POST_MANAGE;
    }

    @RequestMapping(value = POST_UPDATE, method = RequestMethod.POST)
    public String postUpdateHandler(@ModelAttribute Post post,
                                    Model model) {
        post = postService.find(post.getId());
        model.addAttribute("post", post);
        model.addAttribute("roles", ROLES.values());
        model.addAttribute("categories", categoryDao.findAll());
        return POST_CREATE_VIEW;
    }

    @RequestMapping(value = COMMENT_ADD, method = RequestMethod.POST)
    public String addCommentForm(@Valid @ModelAttribute Comment comment,
                                 Errors errors,
                                 Model model) {
        if (errors.hasErrors()) {
            Post post = comment.getPostId();
            model.addAttribute("post", post);
            model.addAttribute("comments", post.getComments());
            return SINGLE_POST_VIEW;
        }
        Post post = comment.getPostId();
        if (comment.getId() == 0) {
            post.getComments().add(comment);
        } else {
            post.getComments().forEach(i -> {
                if (i.getId() == comment.getId()) {
                    i.setBody(comment.getBody());
                }
            });
        }
        postService.save(post);
        post = postService.find(post.getId());
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = COMMENT_DELETE, method = RequestMethod.POST)
    public String commentDeleteHandler(@ModelAttribute Comment comment,
                                       Model model) {
        Comment targetComment = commentDao.find(comment.getId());
        Post post = postService.find(targetComment.getPostId().getId());
        post.getComments().removeIf(i -> i.getId() == targetComment.getId());
        postService.save(post);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", postService.find(post.getId()).getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = COMMENT_UPDATE, method = RequestMethod.POST)
    public String commentUpdateHandler(@ModelAttribute Comment comment,
                                       Model model) {
        Comment targetComment = commentDao.find(comment.getId());
        long postId = targetComment.getPostId().getId();
        Post post = postService.find(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", targetComment);
        List<Comment> comments = post.getComments();
        comments.removeIf(i -> i.getId() == targetComment.getId());
        model.addAttribute("comments", comments);
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = SHOW_POST_BY_CATEGORY, method = RequestMethod.GET)
    public String showPostByCategory(@PathVariable("id") long id,
                                     Model model,
                                     HttpSession session) {
        Category category = categoryDao.find(id);
        List<Post> posts = category.getPosts();
        if (Objects.nonNull(posts)) {
            posts = SessionUtil.filterPostByRole(session, posts);
        }
        model.addAttribute("posts", posts);
        model.addAttribute(AVAILABLE_CATEGORIES, categoryDao.findAll());
        return HOME_VIEW;
    }
}


package net.therap.blog.web.controller;

import net.therap.blog.domain.Category;
import net.therap.blog.domain.Comment;
import net.therap.blog.domain.Post;
import net.therap.blog.domain.User;
import net.therap.blog.service.CategoryService;
import net.therap.blog.service.CommentService;
import net.therap.blog.service.PostService;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.ROLES;
import net.therap.blog.util.URL;
import net.therap.blog.web.editor.CategoryEditor;
import net.therap.blog.web.editor.PostEditor;
import net.therap.blog.web.editor.UserEditor;
import net.therap.blog.web.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class PostController implements Constants {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostValidator postValidator;

    @InitBinder
    protected void initBinderMenu(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(List.class, "categories", new CategoryEditor(List.class));
        binder.registerCustomEditor(User.class, new UserEditor(userService));
        binder.registerCustomEditor(Post.class, new PostEditor(postService));
    }

    @RequestMapping(value = URL.POST_CREATE_VIEW, method = RequestMethod.GET)
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.findAll());
        return URL.POST_CREATE_VIEW;
    }

    @RequestMapping(value = URL.POST_CREATE, method = RequestMethod.GET)
    public String cratePostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.findAll());
        return URL.POST_CREATE_VIEW;
    }

    @RequestMapping(value = URL.POST_CREATE, method = RequestMethod.POST)
    public String createPostHandler(@ModelAttribute @Valid Post post,
                                    @RequestParam("file") MultipartFile picture,
                                    Model model,
                                    BindingResult error) {

        postValidator.validate(post, error);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for (ConstraintViolation<Post> violation : validator.validate(post)) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            error.addError(new FieldError("post", propertyPath, message));
        }
        if (error.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return URL.POST_CREATE_VIEW;
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
            Category category = categoryService.find(i.getId());
            i.setName(category.getName());
        });
        postService.add(post);
        return "redirect:" + URL.POST_MANAGE;
    }

    @RequestMapping(value = URL.POST_MANAGE, method = RequestMethod.GET)
    public String postManagementHandler(Model model, HttpSession session) {
        List<Post> posts = this.postService.getPostByAccess(session);
        model.addAttribute("posts", posts);
        return URL.POST_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = URL.POST_SHOW)
    public String showSinglePost(@PathVariable("id") long id,
                                 Model model) {
        Post post = postService.find(id);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return URL.SINGLE_POST_VIEW;
    }

    @RequestMapping(value = URL.POST_DELETE)
    public String postDeleteHandler(@PathVariable("id") long id) {
        Post post = postService.find(id);
        postService.delete(post.getId());
        return "redirect:" + URL.POST_MANAGE;
    }

    @RequestMapping(value = URL.POST_UPDATE)
    public String postUpdateHandler(@PathVariable("id") long id,
                                    Model model) {
        Post post = postService.find(id);
        model.addAttribute("post", post);
        model.addAttribute("roles", ROLES.values());
        return URL.POST_CREATE_VIEW;
    }

    @RequestMapping(value = URL.COMMENT_ADD, method = RequestMethod.POST)
    public String addCommentForm(@ModelAttribute @Valid Comment comment,
                                 Model model) {
        Post post = comment.getPostId();
        comment.setPostId(post);
        commentService.add(comment);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return URL.SINGLE_POST_VIEW;
    }

    @RequestMapping(value = URL.COMMENT_DELETE)
    public String commentDeleteHandler(@PathVariable("id") long id,
                                       Model model) {
        Comment comment = commentService.find(id);
        long postId = comment.getPostId().getId();
        commentService.delete(comment.getId());
        Post post = postService.find(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return URL.SINGLE_POST_VIEW;

    }

    @RequestMapping(value = URL.COMMENT_UPDATE, method = RequestMethod.GET)
    public String commentUpdateHandler(@PathVariable("id") long id,
                                       Model model) {
        Comment comment = commentService.find(id);
        long postId = comment.getPostId().getId();
        Post post = postService.find(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
        List<Comment> comments = post.getComments();
        comments.removeIf(i -> i.getId() == comment.getId());
        model.addAttribute("comments", comments);
        return URL.SINGLE_POST_VIEW;

    }
}


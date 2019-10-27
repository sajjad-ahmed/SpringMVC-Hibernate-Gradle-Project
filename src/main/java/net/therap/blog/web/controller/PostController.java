package net.therap.blog.web.controller;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.dao.CommentDao;
import net.therap.blog.domain.Category;
import net.therap.blog.domain.Comment;
import net.therap.blog.domain.Post;
import net.therap.blog.domain.User;
import net.therap.blog.exception.NotFoundException;
import net.therap.blog.exception.WebSecurityException;
import net.therap.blog.service.PostService;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.Role;
import net.therap.blog.util.Status;
import net.therap.blog.util.Util;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public String showPostForm(@ModelAttribute Post post,
                               Model model) {
        post = post.isNew() ? post : postService.find(post.getId()).get();
        post.checkNull();
        model.addAttribute("post", post);
        model.addAttribute("roles", Role.values());
        model.addAttribute("status", Status.getMap());
        model.addAttribute("categories", categoryDao.findAll());
        return POST_CREATE_VIEW;
    }

    @RequestMapping(value = POST_CREATE, method = RequestMethod.POST)
    public String createPostHandler(@Valid @ModelAttribute Post post,
                                    Errors errors,
                                    Model model,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam("file") MultipartFile picture) {
        Role userRole = Util.getUserRole(session);
        if (!(userRole.equals(Role.ADMIN) || userRole.equals(Role.AUTHOR))) {
            throw new WebSecurityException();
        }
        postValidator.validate(post, errors);
        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryDao.findAll());
            model.addAttribute("status", Status.getMap());
            return POST_CREATE_VIEW;
        }
        redirectAttributes.addFlashAttribute(CONFIRMATION, post.isNew() ? "ADDED" : "UPDATED");
        if (!setPostCover(post, picture)) {
            throw new NotFoundException("image");
        }
        setCategoryNames(post);
        postService.save(post);
        return "redirect:" + POST_MANAGE;
    }

    @RequestMapping(value = POST_MANAGE, method = RequestMethod.GET)
    public String showPostManagementPage(Model model, HttpSession session) {
        if (!Util.isAdmin(session)) {
            throw new WebSecurityException();
        }
        List<Post> posts = Util.getPostByRole(session, postService);
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        return POST_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = POST_SHOW, method = RequestMethod.GET)
    public String showSinglePost(@PathVariable("uri") String uri,
                                 Model model) {
        Post post = postService.findBy(uri).get();
        post.checkNull();
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = POST_DELETE, method = RequestMethod.POST)
    public String postDeleteHandler(@ModelAttribute Post post, HttpSession session) {
        Role userRole = Util.getUserRole(session);
        if (!(userRole.equals(Role.ADMIN) || userRole.equals(Role.AUTHOR))) {
            throw new WebSecurityException();
        }
        Optional<Post> postOptional = postService.find(post.getId());
        post.checkOptionalIsPresent(postOptional);
        post = postService.find(post.getId()).get();
        postService.delete(post.getId());
        return "redirect:" + POST_MANAGE;
    }

    @RequestMapping(value = COMMENT_ADD, method = RequestMethod.POST)
    public String addComment(@Valid @ModelAttribute Comment comment,
                             Errors errors,
                             Model model,
                             HttpSession session) {
        Role userRole = Util.getUserRole(session);
        if (userRole.equals(Role.GUEST)) {
            throw new WebSecurityException();
        }
        if (errors.hasErrors()) {
            Post post = comment.getPost();
            model.addAttribute("post", post);
            model.addAttribute("comments", post.getComments());
            return SINGLE_POST_VIEW;
        }
        Post post = comment.getPost();
        if (comment.isNew()) {
            post.getComments().add(comment);
            model.addAttribute(CONFIRMATION, "ADDED");
        } else {
            model.addAttribute(CONFIRMATION, "UPDATED");
            post.getComments().forEach(i -> {
                if (i.getId() == comment.getId()) {
                    i.setBody(comment.getBody());
                }
            });
        }
        post = postService.save(post);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = COMMENT_DELETE, method = RequestMethod.POST)
    public String commentDeleteHandler(@ModelAttribute Comment comment,
                                       Model model) {
        Comment targetComment = commentDao.find(comment.getId()).get();
        Post post = postService.find(targetComment.getPost().getId()).get();
        List<Comment> comments = post.getComments();
        comments.removeIf(i -> i.getId() == targetComment.getId());
        post.setComments(comments);
        post = postService.save(post);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", post.getComments());
        return SINGLE_POST_VIEW;
    }

    @RequestMapping(value = SHOW_POST_BY_CATEGORY, method = RequestMethod.GET)
    public String showPostByCategory(@PathVariable("id") long id,
                                     Model model,
                                     HttpSession session) {
        model.addAttribute("posts", Util.getPostByRoleAndCategory(session, postService, id));
        model.addAttribute(AVAILABLE_CATEGORIES, categoryDao.findAll());
        return HOME_VIEW;
    }

    @RequestMapping(value = COMMENT_UPDATE_FORM, method = RequestMethod.GET)
    public String showCommentUpdateBox(@ModelAttribute Comment comment,
                                       Model model) {
        Optional<Comment> commentOptional = commentDao.find(comment.getId());
        comment.checkOptionalIsPresent(commentOptional);
        Comment targetComment = commentOptional.get();
        Post post = targetComment.getPost();
        List<Comment> comments = post.getComments();
        comments.removeIf(i -> i.getId() == targetComment.getId());
        model.addAttribute("post", post);
        model.addAttribute("comment", targetComment);
        model.addAttribute("comments", comments);
        return SINGLE_POST_VIEW;
    }

    private void setCategoryNames(Post post) {
        post.getCategories().forEach(i -> {
            Category category = categoryDao.find(i.getId()).get();
            i.setName(category.getName());
        });
    }

    private boolean setPostCover(Post post, MultipartFile picture) {
        if (!picture.isEmpty()) {
            try {
                byte[] bytes = picture.getBytes();
                post.setPicture(bytes);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}


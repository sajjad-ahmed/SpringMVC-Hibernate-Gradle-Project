package net.therap.blog.web.controller;

import net.therap.blog.domain.User;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.ROLES;
import net.therap.blog.util.SessionUtil;
import net.therap.blog.web.validator.UniqueEmailValidator;
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

import static net.therap.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class UserController implements Constants {

    @Autowired
    private UniqueEmailValidator uniqueEmailValidator;

    @Autowired
    private UserService userService;

    @InitBinder
    protected void initBinderMenu(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = USER_ADD, method = RequestMethod.GET)
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", ROLES.values());
        return USER_ADD_VIEW;
    }

    @RequestMapping(value = USER_SIGN_UP, method = RequestMethod.GET)
    public String showUserSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return USER_SIGN_UP_VIEW;
    }

    @RequestMapping(value = USER_ADD, method = RequestMethod.POST)
    public String addUserHandler(@Valid @ModelAttribute User user,
                                 Errors errors,
                                 Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam("file") MultipartFile file) {
        String userRole = SessionUtil.getUserRole(session);
        if (!userRole.equals(ROLES.ADMIN.name())) {
            return ACCESS_ERROR_VIEW;
        }
        if (user.getId() == 0) {
            uniqueEmailValidator.validate(user, errors);
            redirectAttributes.addFlashAttribute(CONFIRMATION, "ADDED");
        } else {
            redirectAttributes.addFlashAttribute(CONFIRMATION, "UPDATED");
        }
        if (errors.hasErrors()) {
            model.addAttribute("roles", ROLES.values());
            return USER_ADD_VIEW;
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                user.setProfilePicture(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userService.save(user);
        return "redirect:" + USER_MANAGE;
    }

    @RequestMapping(value = USER_MANAGE, method = RequestMethod.GET)
    public String userManagementHandler(Model model) {
        List<User> posts = this.userService.findAll();
        model.addAttribute("users", posts);
        model.addAttribute("user", new User());
        return USER_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = USER_UPDATE, method = RequestMethod.GET)
    public String userUpdateHandler(@ModelAttribute User user,
                                    Model model) {
        user = userService.find(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("roles", ROLES.values());
        return USER_ADD_VIEW;
    }

    @RequestMapping(value = USER_DELETE, method = RequestMethod.POST)
    public String userDeleteHandler(@ModelAttribute User user) {
        user = userService.find(user.getId());
        userService.delete(user.getId());
        return "redirect:" + USER_MANAGE;
    }

    @RequestMapping(value = USER_SIGN_UP, method = RequestMethod.POST)
    public String userSignUpHandler(@Valid @ModelAttribute User user,
                                    Errors errors,
                                    Model model,
                                    HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        if (!userRole.equals(Constants.ACCESS_GUEST)) {
            return ACCESS_ERROR_VIEW;
        }
        uniqueEmailValidator.validate(user, errors);
        if (errors.hasErrors()) {
            return USER_SIGN_UP_VIEW;
        }
        userService.signUp(user);
        return "redirect:" + ROOT;
    }

    @RequestMapping(value = USER_UPDATE_INFORMATION, method = RequestMethod.GET)
    public String updateUserSelf(HttpSession session,
                                 Model model) {
        long id = (long) session.getAttribute(USER_ID_PARAMETER);
        User user = userService.find(id);
        model.addAttribute("user", user);
        return USER_UPDATE_VIEW;
    }

    @RequestMapping(value = USER_UPDATE_INFORMATION, method = RequestMethod.POST)
    public String updateUserSelf(@Valid @ModelAttribute User user,
                                 Errors errors,
                                 Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam("file") MultipartFile file) {
        String userRole = SessionUtil.getUserRole(session);
        if (userRole.equals(ROLES.AUTHOR.name())) {
            return ACCESS_ERROR_VIEW;
        }
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            return USER_UPDATE_VIEW;
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                user.setProfilePicture(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute(CONFIRMATION, "UPDATED");
        return "redirect:" + SHOW_DASHBOARD;
    }
}


package net.therap.blog.web.controller;

import net.therap.blog.domain.User;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.URL;
import net.therap.blog.web.validator.UniqueEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
public class UserController implements Constants {

    @Autowired
    UniqueEmailValidator uniqueEmailValidator;
    @Autowired
    private UserService userService;

    @RequestMapping(value = URL.USER_ADD_VIEW, method = RequestMethod.GET)
    public String showEditUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", ROLES.values());
        return URL.USER_ADD_VIEW;
    }

    @RequestMapping(value = URL.USER_ADD, method = RequestMethod.GET)
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", ROLES.values());
        return URL.USER_ADD_VIEW;
    }

    @RequestMapping(value = URL.USER_SIGN_UP, method = RequestMethod.GET)
    public String showUserSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return URL.USER_SIGN_UP_VIEW;
    }

    @RequestMapping(value = URL.USER_ADD, method = RequestMethod.POST)
    public String addUserHandler(@ModelAttribute @Valid User user,
                                 @RequestParam("file") MultipartFile file,
                                 HttpSession session,
                                 Model model,
                                 BindingResult error) {
        uniqueEmailValidator.validate(user, error);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for (ConstraintViolation<User> violation : validator.validate(user)) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            error.addError(new FieldError("user", propertyPath, message));
        }
        if (error.hasErrors()) {
            model.addAttribute("roles", ROLES.values());
            return URL.USER_ADD_VIEW;
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                user.setProfilePicture(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userService.add(user, session);
        return "redirect:" + URL.USER_MANAGE;
    }

    @RequestMapping(value = URL.USER_MANAGE, method = RequestMethod.GET)
    public String userManagementHandler(ModelMap modelMap, HttpSession session) {
        List<User> posts = this.userService.getAll(session);
        modelMap.put("users", posts);
        return URL.USER_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = URL.USER_UPDATE)
    public String userUpdateHandler(@PathVariable("id") long id, Model model) {
        User user = userService.find(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", ROLES.values());
        return URL.USER_ADD_VIEW;
    }

    @RequestMapping(value = URL.USER_DELETE)
    public String userDeleteHandler(@PathVariable("id") long id, Model model) {
        User user = userService.find(id);
        userService.delete(user.getId());
        return "redirect:" + URL.USER_MANAGE;
    }

    @RequestMapping(value = URL.USER_SIGN_UP, method = RequestMethod.POST)
    public String userSignUpHandler(@ModelAttribute @Valid User user,
                                    @RequestParam("file") MultipartFile file,
                                    Model model,
                                    BindingResult error) {
        uniqueEmailValidator.validate(user, error);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for (ConstraintViolation<User> violation : validator.validate(user)) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            error.addError(new FieldError("user", propertyPath, message));
        }
        if (error.hasErrors()) {
            model.addAttribute("roles", ROLES.values());
            return URL.USER_SIGN_UP_VIEW;
        }
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                user.setProfilePicture(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userService.signUp(user);
        return "redirect:" + URL.ROOT;
    }
}


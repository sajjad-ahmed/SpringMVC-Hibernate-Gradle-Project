package red.niloy.blog.web.controller;

import red.niloy.blog.domain.User;
import red.niloy.blog.exception.WebSecurityException;
import red.niloy.blog.service.UserService;
import red.niloy.blog.util.Constants;
import red.niloy.blog.util.Role;
import red.niloy.blog.util.Util;
import red.niloy.blog.web.validator.UniqueEmailValidator;
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
import java.util.Optional;

import red.niloy.blog.util.URL;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class UserController implements Constants
{

    @Autowired
    private UniqueEmailValidator uniqueEmailValidator;

    @Autowired
    private UserService userService;

    @InitBinder
    protected void initBinderMenu(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = URL.USER_ADD, method = RequestMethod.GET)
    public String showAddUserForm(@ModelAttribute User user,
                                  Model model,
                                  HttpSession session) {
        if (!Util.isAdmin(session)) {
            throw new WebSecurityException();
        }
        user = user.isNew() ? user : userService.find(user.getId()).get();
        user.checkNull();
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return URL.USER_ADD_VIEW;
    }

    @RequestMapping(value = URL.USER_SIGN_UP, method = RequestMethod.GET)
    public String showUserSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return URL.USER_SIGN_UP_VIEW;
    }

    @RequestMapping(value = URL.USER_ADD, method = RequestMethod.POST)
    public String addUserHandler(@Valid @ModelAttribute User user,
                                 Errors errors,
                                 Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam("file") MultipartFile file) {
        if (!Util.isAdmin(session)) {
            throw new WebSecurityException();
        }
        if (user.isNew()) {
            uniqueEmailValidator.validate(user, errors);
            redirectAttributes.addFlashAttribute(CONFIRMATION, "ADDED");
        } else {
            redirectAttributes.addFlashAttribute(CONFIRMATION, "UPDATED");
        }
        if (errors.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return URL.USER_ADD_VIEW;
        }
        setProfilePicture(user, file);
        userService.save(user);
        return "redirect:" + URL.USER_MANAGE;
    }

    @RequestMapping(value = URL.USER_MANAGE, method = RequestMethod.GET)
    public String showUserManagementView(Model model) {
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("user", new User());
        return URL.USER_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = URL.USER_DELETE, method = RequestMethod.POST)
    public String userDeleteHandler(@ModelAttribute User user,
                                    HttpSession session) {
        if (!Util.isAdmin(session)) {
            throw new WebSecurityException();
        }
        Optional<User> userOptional = userService.find(user.getId());
        user.checkOptionalIsPresent(userOptional);
        user = userOptional.get();
        userService.delete(user.getId());
        return "redirect:" + URL.USER_MANAGE;
    }

    @RequestMapping(value = URL.USER_SIGN_UP, method = RequestMethod.POST)
    public String userSignUpHandler(@Valid @ModelAttribute User user,
                                    Errors errors,
                                    HttpSession session) {
        Role userRole = Util.getUserRole(session);
        if (!userRole.equals(Role.GUEST)) {
            throw new WebSecurityException();
        }
        uniqueEmailValidator.validate(user, errors);
        if (errors.hasErrors()) {
            return URL.USER_SIGN_UP_VIEW;
        }
        userService.save(user);
        return "redirect:" + URL.ROOT;
    }

    @RequestMapping(value = URL.USER_UPDATE_INFORMATION, method = RequestMethod.GET)
    public String updateUserForm(HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        Optional<User> userOptional = userService.find(user.getId());
        user.checkOptionalIsPresent(userOptional);
        user = userOptional.get();
        model.addAttribute("user", user);
        return URL.USER_UPDATE_VIEW;
    }

    @RequestMapping(value = URL.USER_UPDATE_INFORMATION, method = RequestMethod.POST)
    public String updateUserHandler(@Valid @ModelAttribute User user,
                                    Errors errors,
                                    Model model,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam("file") MultipartFile file) {
        Role role = Util.getUserRole(session);
        if (role.equals(Role.ADMIN)) {
            throw new WebSecurityException();
        }
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            return URL.USER_UPDATE_VIEW;
        }
        setProfilePicture(user, file);
        userService.save(user);
        redirectAttributes.addFlashAttribute(CONFIRMATION, "UPDATED");
        return "redirect:" + URL.SHOW_DASHBOARD;
    }

    private void setProfilePicture(User user, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                user.setProfilePicture(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


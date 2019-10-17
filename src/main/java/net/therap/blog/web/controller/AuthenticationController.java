package net.therap.blog.web.controller;

import net.therap.blog.dao.UserDao;
import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import net.therap.blog.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

import static net.therap.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
@Controller
public class AuthenticationController implements Constants {

    public static final int AUTHENTICATION_FAILED_FLAG = -1;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = LOG_IN, method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return LOG_IN_VIEW;
    }
    @RequestMapping(value = LOG_IN, method = RequestMethod.POST)
    public String loginHandler(@Valid @ModelAttribute(name = "user") User targetUser,
                               Errors errors,
                               HttpServletRequest request,
                               Model model) throws IllegalArgumentException {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                if (error.getField().equals("email") || error.getField().equals("password")) {
                    return LOG_IN_VIEW;
                }
            }
        }
        User user = userDao.findUserByEmail(targetUser.getEmail());
        if (Objects.nonNull(targetUser.getPassword()) && Objects.nonNull(targetUser.getEmail()) && Objects.nonNull(user)) {
            if (Encryption.encrypt(targetUser.getPassword()).equals(user.getPassword()) && user.getEmail().equals(targetUser.getEmail())) {
                HttpSession session = request.getSession();
                session.setAttribute(USER_ID_PARAMETER, user.getId());
                session.setAttribute(USER_EMAIL_PARAMETER, user.getEmail());
                session.setAttribute(USER_ROLE_PARAMETER, user.getRole());
                session.setAttribute(USER_FIRST_NAME_PARAMETER, user.getFirstName());
                return "redirect:" + ROOT;
            }
        }
        model.addAttribute("authFailed", AUTHENTICATION_FAILED_FLAG);
        return LOG_IN_VIEW;
    }

    @RequestMapping(value = LOG_OUT, method = RequestMethod.GET)
    public String logoutHandlerWithView(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (Objects.nonNull(session)) {
            session.invalidate();
        }
        return "redirect:" + ROOT;
    }
}

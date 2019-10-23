package net.therap.blog.web.controller;

import net.therap.blog.cmd.LoginCmd;
import net.therap.blog.dao.UserDao;
import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import net.therap.blog.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
        model.addAttribute("loginCmd", new LoginCmd());
        return LOG_IN_VIEW;
    }

    @RequestMapping(value = LOG_IN, method = RequestMethod.POST)
    public String loginHandler(@Valid @ModelAttribute LoginCmd loginCmd,
                               Errors errors,
                               HttpServletRequest request,
                               Model model) throws IllegalArgumentException {
        if (errors.hasErrors()) {
            return LOG_IN_VIEW;
        }
        User user = userDao.findUserByEmail(loginCmd.getEmail());
        if (Objects.nonNull(loginCmd.getPassword()) && Objects.nonNull(loginCmd.getEmail()) && Objects.nonNull(user)) {
            if (Encryption.encrypt(loginCmd.getPassword()).equals(user.getPassword()) &&
                    user.getEmail().equals(loginCmd.getEmail())) {
                HttpSession session = request.getSession();
                session.setAttribute(SESSION_USER_PARAMETER, user);
                return "redirect:" + ROOT;
            }
        }

        model.addAttribute("authFailed", AUTHENTICATION_FAILED_FLAG);
        return LOG_IN_VIEW;
    }

    @RequestMapping(value = LOG_OUT, method = RequestMethod.GET)
    public String logoutHandler(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (Objects.nonNull(session)) {
            session.invalidate();
        }
        return "redirect:" + ROOT;
    }
}

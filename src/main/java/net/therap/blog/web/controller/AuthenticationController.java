package net.therap.blog.web.controller;

import net.therap.blog.dao.UserDao;
import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import net.therap.blog.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
@Controller
public class AuthenticationController {

    public static final int AUTHENTICATION_FAILED_FLAG = -1;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = URL.LOG_IN, method = RequestMethod.GET)
    public String showLoginForm() {
        return URL.LOG_IN_VIEW;
    }

    @RequestMapping(value = URL.LOG_IN, method = RequestMethod.POST)
    public String loginHandler(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletRequest request,
            Model model) {

        try {
            User user = userDao.findUserByEmail(email);
            if (password != null && email != null && user != null) {
                if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                    HttpSession session = request.getSession();
                    session.setAttribute(Constants.USER_ID_PARAMETER, user.getId());
                    session.setAttribute(Constants.USER_EMAIL_PARAMETER, user.getEmail());
                    session.setAttribute(Constants.USER_ROLE_PARAMETER, user.getRole());
                    session.setAttribute(Constants.USER_FIRST_NAME_PARAMETER, user.getFirstName());
                    return "redirect:" + URL.ROOT;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        model.addAttribute("authFailed", AUTHENTICATION_FAILED_FLAG);
        return URL.LOG_IN_VIEW;
    }

    @RequestMapping(value = URL.LOG_OUT, method = RequestMethod.GET)
    public String logoutHandlerWithView(HttpServletRequest req,
                                        HttpServletResponse resp) {
        resp.setContentType("text/html");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:" + URL.ROOT;
    }
}

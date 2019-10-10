package net.therap.blog.web.controller;

import net.therap.blog.util.SessionUtil;
import net.therap.blog.util.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


/**
 * @author sajjad.ahmed
 * @since 9/19/19.
 */
@Controller
public class DashboardController {

    @RequestMapping(value = URL.SHOW_DASHBOARD, method = RequestMethod.GET)
    public String showDashboard(Model model, HttpSession session) {
        SessionUtil.parameterManagement(session, model);
        return URL.DASHBOARD_VIEW;
    }
}

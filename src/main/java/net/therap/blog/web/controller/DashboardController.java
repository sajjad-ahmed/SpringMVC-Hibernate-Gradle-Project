package net.therap.blog.web.controller;

import net.therap.blog.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.therap.blog.util.URL.DASHBOARD_VIEW;
import static net.therap.blog.util.URL.SHOW_DASHBOARD;


/**
 * @author sajjad.ahmed
 * @since 9/19/19.
 */
@Controller
public class DashboardController implements Constants {

    @RequestMapping(value = SHOW_DASHBOARD, method = RequestMethod.GET)
    public String showDashboard(Model model, HttpSession session) {
        long userId = (Long) session.getAttribute(USER_ID_PARAMETER);
        String userEmail = (String) session.getAttribute(USER_EMAIL_PARAMETER);
        String userFirstName = (String) session.getAttribute(USER_FIRST_NAME_PARAMETER);
        String userRole = (String) session.getAttribute(USER_ROLE_PARAMETER);
        if (userId != 0 || Objects.nonNull(userEmail) || Objects.nonNull(userFirstName) || Objects.nonNull(userRole)) {
            model.addAttribute(USER_ID_PARAMETER, userId);
            model.addAttribute(USER_EMAIL_PARAMETER, userEmail);
            model.addAttribute(USER_ROLE_PARAMETER, userRole);
            model.addAttribute(USER_FIRST_NAME_PARAMETER, userFirstName);
            model.addAttribute(AUTHORIZED_URIs, getUris(userRole));
        }
        return DASHBOARD_VIEW;
    }

    private Map<String, String> getUris(String role) {
        Map<String, String> uriMap = new HashMap<>();
        switch (role) {
            case "ADMIN": {
                uriMap.put("/user/manage", "Manage User");
                uriMap.put("/post/manage", "Manage Post");
                uriMap.put("/category/manage", "Manage Category");
                uriMap.put("/message/send", "Send A message");
                uriMap.put("/inbox", "My Inbox");
                break;
            }
            case "AUTHOR": {
                uriMap.put("/post/manage", "Manage Post");
                uriMap.put("/message/send", "Send A message");
                uriMap.put("/inbox", "My Inbox");
                break;
            }
            case "SUBSCRIBER": {
                uriMap.put("/message/send", "Send A message");
                uriMap.put("/inbox", "My Inbox");
                break;
            }
        }
        return uriMap;
    }
}

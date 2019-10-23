package net.therap.blog.web.controller;

import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.therap.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 9/19/19.
 */
@Controller
public class DashboardController implements Constants {

    @RequestMapping(value = SHOW_DASHBOARD, method = RequestMethod.GET)
    public String showDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        if (Objects.nonNull(session) && Objects.nonNull(user)) {
            model.addAttribute(USER_ID_PARAMETER, user.getId());
            model.addAttribute(USER_EMAIL_PARAMETER, user.getEmail());
            model.addAttribute(USER_ROLE_PARAMETER, user.getEmail());
            model.addAttribute(USER_FIRST_NAME_PARAMETER, user.getFirstName());
            model.addAttribute(AUTHORIZED_URIs, getUris(user.getRole().name()));
        }
        return DASHBOARD_VIEW;
    }

    private Map<String, String> getUris(String role) {
        Map<String, String> uriMap = new HashMap<>();
        switch (role) {
            case "ADMIN": {
                uriMap.put("Manage User", USER_MANAGE);
                uriMap.put("Manage Post", POST_MANAGE);
                uriMap.put("Manage Category", CATEGORY_MANAGE);
                uriMap.put("Send A message", MESSAGE_SEND);
                uriMap.put("My Inbox", MESSAGE_SHOW_INBOX);
                break;
            }
            case "AUTHOR": {
                uriMap.put("Manage Post", POST_MANAGE);
                uriMap.put("Update Information", USER_UPDATE_INFORMATION);
                uriMap.put("Send A message", MESSAGE_SEND);
                uriMap.put("My Inbox", MESSAGE_SHOW_INBOX);
                break;
            }
            case "SUBSCRIBER": {
                uriMap.put("Update Information", USER_UPDATE_INFORMATION);
                uriMap.put("Send A message", MESSAGE_SEND);
                uriMap.put("My Inbox", MESSAGE_SHOW_INBOX);
                break;
            }
        }
        return uriMap;
    }
}

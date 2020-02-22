package red.niloy.blog.web.controller;

import red.niloy.blog.domain.User;
import red.niloy.blog.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static red.niloy.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 9/19/19.
 */
@Controller
public class DashboardController implements Constants {

    @Autowired
    private MessageSourceAccessor msa;

    @RequestMapping(value = SHOW_DASHBOARD, method = RequestMethod.GET)
    public String showDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        if (Objects.nonNull(session) && Objects.nonNull(user)) {
            model.addAttribute(AUTHORIZED_URIs, getUris(user.getRole().name()));
        }
        return DASHBOARD_VIEW;
    }

    private Map<String, String> getUris(String role) {
        Map<String, String> uriMap = new HashMap<>();
        switch (role) {
            case "ADMIN": {
                uriMap.put(msa.getMessage("label.dashboard.manage.user"), USER_MANAGE);
                uriMap.put(msa.getMessage("label.dashboard.manage.post"), POST_MANAGE);
                uriMap.put(msa.getMessage("label.dashboard.manage.category"), CATEGORY_MANAGE);
                uriMap.put(msa.getMessage("label.dashboard.manage.message.send"), MESSAGE_SEND);
                uriMap.put(msa.getMessage("label.dashboard.manage.message.inbox"), MESSAGE_SHOW_INBOX);
                break;
            }
            case "AUTHOR": {
                uriMap.put(msa.getMessage("label.dashboard.manage.post"), POST_MANAGE);
                uriMap.put(msa.getMessage("label.dashboard.manage.update.information"), USER_UPDATE_INFORMATION);
                uriMap.put(msa.getMessage("label.dashboard.manage.message.send"), MESSAGE_SEND);
                uriMap.put(msa.getMessage("label.dashboard.manage.message.inbox"), MESSAGE_SHOW_INBOX);
                break;
            }
            case "SUBSCRIBER": {
                uriMap.put(msa.getMessage("label.dashboard.manage.update.information"), USER_UPDATE_INFORMATION);
                uriMap.put(msa.getMessage("label.dashboard.manage.message.send"), MESSAGE_SEND);
                uriMap.put(msa.getMessage("label.dashboard.manage.message.inbox"), MESSAGE_SHOW_INBOX);
                break;
            }
        }
        return Collections.unmodifiableMap(uriMap);
    }
}

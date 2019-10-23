package net.therap.blog.web.controller;

import net.therap.blog.dao.MessageDao;
import net.therap.blog.domain.Message;
import net.therap.blog.domain.User;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.web.editor.UserEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static net.therap.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class MessageController implements Constants {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageDao messageDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(User.class, new UserEditor(userService));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = MESSAGE_SEND, method = RequestMethod.GET)
    public String showNewMessageForm(Model model, HttpSession session) {
        model.addAttribute("message", new Message());
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        List<User> users = userService.findAllExceptSelf(user.getId());
        model.addAttribute("users", users);
        return MESSAGE_ADD_VIEW;
    }

    @RequestMapping(value = MESSAGE_SEND, method = RequestMethod.POST)
    public String sendMessageHandler(@Valid @ModelAttribute Message message,
                                     Errors errors,
                                     Model model,
                                     HttpSession session) {
        if (errors.hasErrors()) {
            User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
            List<User> users = userService.findAllExceptSelf(user.getId());
            model.addAttribute("users", users);
            return MESSAGE_ADD_VIEW;
        }
        messageDao.save(message);
        return "redirect:" + MESSAGE_SHOW_INBOX;
    }

    @RequestMapping(value = MESSAGE_SHOW_INBOX, method = RequestMethod.GET)
    public String showInbox(Model model, HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        model.addAttribute("message", new Message());
        model.addAttribute("sentMessages", messageDao.findSentMessages(user.getId()));
        model.addAttribute("receivedMessages", messageDao.findReceivedMessages(user.getId()));
        return MESSAGE_INBOX_VIEW;
    }

    @RequestMapping(value = MESSAGE_DELETE, method = RequestMethod.POST)
    public String messageDeleteHandler(@ModelAttribute Message message,
                                       HttpSession session,
                                       Model model) {
        message = messageDao.find(message.getId());
        messageDao.delete(message.getId());
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        model.addAttribute("sentMessages", messageDao.findSentMessages(user.getId()));
        model.addAttribute("receivedMessages", messageDao.findReceivedMessages(user.getId()));
        return MESSAGE_INBOX_VIEW;
    }

}


package red.niloy.blog.web.controller;

import red.niloy.blog.dao.MessageDao;
import red.niloy.blog.domain.Message;
import red.niloy.blog.domain.User;
import red.niloy.blog.service.UserService;
import red.niloy.blog.util.Constants;
import red.niloy.blog.web.editor.UserEditor;
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
import java.util.Optional;

import static red.niloy.blog.util.URL.*;

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
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        List<User> users = userService.findAllExceptSelf(user.getId());
        model.addAttribute("message", new Message());
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
        Optional<Message> messageOptional = messageDao.find(message.getId());
        message.checkOptionalIsPresent(messageOptional);
        messageDao.delete(message.getId());
        User user = (User) session.getAttribute(SESSION_USER_PARAMETER);
        model.addAttribute("sentMessages", messageDao.findSentMessages(user.getId()));
        model.addAttribute("receivedMessages", messageDao.findReceivedMessages(user.getId()));
        return MESSAGE_INBOX_VIEW;
    }

}


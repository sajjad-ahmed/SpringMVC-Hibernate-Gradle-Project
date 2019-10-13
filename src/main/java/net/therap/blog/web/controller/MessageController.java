package net.therap.blog.web.controller;

import net.therap.blog.dao.MessageDao;
import net.therap.blog.domain.Message;
import net.therap.blog.domain.User;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.URL;
import net.therap.blog.web.editor.UserEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(value = URL.MESSAGE_SEND, method = RequestMethod.GET)
    public String showNewMessageForm(Model model, HttpSession session) {
        model.addAttribute("message", new Message());
        long currentUserId = (long) session.getAttribute(USER_ID_PARAMETER);
        List<User> users = userService.findAllExceptSelf(currentUserId);
        model.addAttribute("users", users);
        return URL.MESSAGE_ADD_VIEW;
    }

    @RequestMapping(value = URL.MESSAGE_SEND, method = RequestMethod.POST)
    public String sendMessageHandler(@Valid @ModelAttribute("message") Message message,
                                     Model model,
                                     BindingResult error,
                                     HttpSession session) {
        if (error.hasErrors()) {
            System.out.println(LOG + "ERROR FOUND");
            long currentUserId = (long) session.getAttribute(USER_ID_PARAMETER);
            List<User> users = userService.findAllExceptSelf(currentUserId);
            model.addAttribute("users", users);
            return URL.MESSAGE_ADD_VIEW;
        }
        messageDao.save(message);
        return "redirect:" + URL.MESSAGE_SHOW_INBOX;
    }

    @RequestMapping(value = URL.MESSAGE_SHOW_INBOX, method = RequestMethod.GET)
    public String showInbox(Model model, HttpSession session) {
        long userId = (long) session.getAttribute(USER_ID_PARAMETER);
        model.addAttribute("sentMessages", messageDao.findSentMessages(userId));
        model.addAttribute("receivedMessages", messageDao.findReceivedMessages(userId));
        return URL.MESSAGE_INBOX_VIEW;
    }

    @RequestMapping(value = URL.MESSAGE_DELETE)
    public String messageDeleteHandler(@PathVariable("id") long id,
                                       HttpSession session,
                                       Model model) {
        Message message = messageDao.find(id);
        messageDao.delete(message.getId());
        long userId = (long) session.getAttribute(USER_ID_PARAMETER);
        model.addAttribute("sentMessages", messageDao.findSentMessages(userId));
        model.addAttribute("receivedMessages", messageDao.findReceivedMessages(userId));
        return URL.MESSAGE_INBOX_VIEW;
    }

}


package net.therap.blog.web.controller;

import net.therap.blog.domain.Message;
import net.therap.blog.domain.User;
import net.therap.blog.service.MessageService;
import net.therap.blog.service.UserService;
import net.therap.blog.util.Constants;
import net.therap.blog.util.URL;
import net.therap.blog.web.editor.UserEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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
    private MessageService messageService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(User.class, new UserEditor(userService));
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
    public String sendMessageHandler(@ModelAttribute("message") @Valid Message message,
                                     Model model,
                                     BindingResult error,
                                     HttpSession session) {

        System.out.println(LOG + message.getBody());
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for (ConstraintViolation<Message> violation : validator.validate(message)) {
            String propertyPath = violation.getPropertyPath().toString();
            String msg = violation.getMessage();
            error.addError(new FieldError("message", propertyPath, msg));
        }
        if (error.hasErrors()) {
            long currentUserId = (long) session.getAttribute(USER_ID_PARAMETER);
            List<User> users = userService.findAllExceptSelf(currentUserId);
            model.addAttribute("users", users);
            return URL.MESSAGE_ADD_VIEW;
        }
        messageService.add(message);
        return "redirect:" + URL.MESSAGE_SHOW_INBOX;
    }

    @RequestMapping(value = URL.MESSAGE_SHOW_INBOX, method = RequestMethod.GET)
    public String showInbox(Model model, HttpSession session) {
        long userId = (long) session.getAttribute(USER_ID_PARAMETER);
        model.addAttribute("sentMessages", messageService.getSentMessages(userId));
        model.addAttribute("receivedMessages", messageService.getReceivedMessages(userId));
        return URL.MESSAGE_INBOX_VIEW;
    }

    @RequestMapping(value = URL.MESSAGE_DELETE)
    public String messageDeleteHandler(@PathVariable("id") long id,
                                       HttpSession session,
                                       Model model) {
        Message message = messageService.find(id);
        messageService.delete(message.getId());
        long userId = (long) session.getAttribute(USER_ID_PARAMETER);
        model.addAttribute("sentMessages", messageService.getSentMessages(userId));
        model.addAttribute("receivedMessages", messageService.getReceivedMessages(userId));
        return URL.MESSAGE_INBOX_VIEW;
    }

}


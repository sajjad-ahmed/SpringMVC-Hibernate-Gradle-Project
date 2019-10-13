package net.therap.blog.web.editor;

import net.therap.blog.domain.User;
import net.therap.blog.service.UserService;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/24/19.
 */
public class UserEditor extends PropertyEditorSupport {

    private UserService userService;

    public UserEditor(UserService userService) {
        this.userService = userService;
    }

    public String getAsText() {
        User user = (User) this.getValue();
        if (Objects.nonNull(user))
            return String.valueOf(user.getId());
        return null;
    }

    public void setAsText(String text) {
        User user = userService.find(Long.parseLong(text));
        setValue(user);
    }
}

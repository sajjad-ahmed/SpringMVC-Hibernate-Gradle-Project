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
        return Objects.nonNull(user) ? String.valueOf(user.getId()) : null;
    }

    public void setAsText(String text) {
        if (text.isEmpty()) {
            setValue(null);
            return;
        }
        int id = Integer.parseInt(text);
        if (id > 0) {
            User user = userService.find(Long.parseLong(text)).get();
            setValue(user);
        }
    }
}

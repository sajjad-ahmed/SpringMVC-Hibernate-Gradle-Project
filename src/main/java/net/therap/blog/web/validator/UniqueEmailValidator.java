package net.therap.blog.web.validator;

import net.therap.blog.domain.User;
import net.therap.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 10/9/19.
 */
@Component
public class UniqueEmailValidator implements Validator {

    @Autowired
    UserService userService;

    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String email = user.getEmail();
        if (Objects.nonNull(email) && userService.isEmailAlreadyInUse(email)) {
            errors.rejectValue("email", "duplicate.email", "Duplicate email");
        }
    }
}

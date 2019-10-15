package net.therap.blog.web.validator;

import net.therap.blog.domain.Post;
import net.therap.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author sajjad.ahmed
 * @since 10/9/19.
 */
@Component
public class PostValidator implements Validator {

    @Autowired
    private PostService postService;

    public boolean supports(Class clazz) {
        return Post.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Post post = (Post) target;
        String value = post.getUri();
        if (post.getId() == 0) {
            if (Objects.nonNull(value) && postService.isUriAlreadyInUse(value)) {
                errors.rejectValue("uri", "validator.duplicate.uri", "URI already exists");
            }
        }
        String access = String.valueOf(post.getAccess());
        String regexp = "^[1,7]{3}$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(access);
        if (!matcher.matches()) {
            errors.rejectValue("access", "validator.post.access.error", "Must be a 3 digit number containing only 1 or 7");

        }
    }
}

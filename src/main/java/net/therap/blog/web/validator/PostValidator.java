package net.therap.blog.web.validator;

import net.therap.blog.domain.Post;
import net.therap.blog.exception.NotFoundException;
import net.therap.blog.service.PostService;
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
public class PostValidator implements Validator {

    @Autowired
    private PostService postService;

    @Override
    public boolean supports(Class clazz) {
        return Post.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Post post = (Post) target;
        String value = post.getUri();
        if (post.isNew()) {
            if (Objects.nonNull(value)) {
                if (postService.isUriAlreadyInUse(value)) {
                    errors.rejectValue("uri", "validator.duplicate.uri", "URI already exists");
                }
            }
        }
    }
}

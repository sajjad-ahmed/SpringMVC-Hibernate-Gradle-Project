package net.therap.blog.web.editor;

import net.therap.blog.domain.Post;
import net.therap.blog.service.PostService;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/24/19.
 */
public class PostEditor extends PropertyEditorSupport {

    private PostService postService;

    public PostEditor(PostService postService) {
        this.postService = postService;
    }

    public String getAsText() {
        Post post = (Post) this.getValue();
        return Objects.nonNull(post) ? String.valueOf(post.getId()) : null;
    }

    public void setAsText(String text) {
        if (text.isEmpty()) {
            setValue(null);
            return;
        }
        int id = Integer.parseInt(text);
        if (id > 0) {
            Post post = postService.find(Long.parseLong(text)).get();
            setValue(post);
        }
    }
}

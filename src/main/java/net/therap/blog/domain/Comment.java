package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;

/**
 * @author sajjad.ahmed
 * @since 9/5/19.
 */

@Entity
@Table(name = "comment")
@NamedQueries({
        @NamedQuery(name = "Comment.findAll", query = "FROM Comment"),
})
public class Comment extends BaseDomain {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @NotNull
    @Size(min = 1, max = 500)
    private String body;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommentedOn() {
        return super.getFormattedDate(DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mm:ss"));
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post postId) {
        this.post = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", userId=" + user +
                ", body='" + body + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}

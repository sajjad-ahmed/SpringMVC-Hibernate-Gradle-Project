package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private User userId;

    @Column(nullable = false)
    @NotNull
    @Size(min = 1, max = 500)
    private String body;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post postId;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommentedOn() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(getCreatedAt().toInstant(), ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mm:ss"));
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", body='" + body + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}

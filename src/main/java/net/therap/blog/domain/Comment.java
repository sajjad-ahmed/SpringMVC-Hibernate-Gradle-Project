package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * @author sajjad.ahmed
 * @since 9/5/19.
 */

@Entity
@Table(name = "comment")
@NamedQueries({
        @NamedQuery(name = "Comment.findAll", query = "FROM Comment"),
})
public class Comment extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        return ft.format(getCreatedAt());
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
                "id=" + id +
                ", userId=" + userId +
                ", body='" + body + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

    public boolean isNew() {
        return this.id == 0;
    }
}

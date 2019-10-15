package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/5/19.
 */

@Entity
@Table(name = "post")
@NamedQueries({
        @NamedQuery(name = "Post.findAll",
                query = "FROM Post"),
        @NamedQuery(name = "Post.findByUri",
                query = "FROM Post WHERE uri = :uri"),
})
public class Post implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "created_by")
    private User creator;

    @NotNull
    @Size(min = 4, max = 255)
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9-]*$", message = "Only [a-z] [A-z] [0-9] and hyphen allowed")
    private String uri;

    @NotNull
    @Size(min = 4, max = 400)
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private long access;

    @Lob
    @Column(nullable = false, columnDefinition = "mediumblob")
    @NotNull
    private byte[] picture;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(name = "post_category",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;


    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Post() {
        this.categories = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getAccess() {
        return access;
    }

    public void setAccess(long access) {
        this.access = access;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getFormattedDate() {
        SimpleDateFormat ft =
                new SimpleDateFormat("MMMMM',' yyyy");
        return ft.format(createdAt);
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImageBase64() {
        return Base64.getEncoder().encodeToString(this.picture);
    }

    public String getAuthorName() {
        return this.creator.getFirstName() + " " + this.creator.getLastName();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", creator=" + creator +
                ", uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", access=" + access +
                ", categories=" + categories +
                ", comments=" + comments +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

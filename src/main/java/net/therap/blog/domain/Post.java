package net.therap.blog.domain;

import net.therap.blog.util.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
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
                query = "FROM Post " +
                        "WHERE uri = :uri"),
        @NamedQuery(name = "Post.findAllByStatus",
                query = "FROM Post p " +
                        "WHERE p.status IN :status"),
        @NamedQuery(name = "Post.findAllByStatusAndCategory",
                query = "SELECT p FROM Post p  " +
                        "JOIN p.categories a " +
                        "WHERE a.id = :categoryId " +
                        "AND p.status IN :status"),
})
public class Post extends BaseDomain {

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
    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    @Column(nullable = false, columnDefinition = "mediumblob")
    @NotNull
    private byte[] picture;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "post_category",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusVal() {
        return status.val;
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

    public String getFormattedDate() {
        return super.getFormattedDate(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getImageBase64() {
        return Base64.getEncoder().encodeToString(this.picture);
    }

    public String getAuthorName() {
        return String.join(this.creator.getFirstName(), " ", this.creator.getLastName());
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + getId() +
                ", creator=" + creator +
                ", uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", access=" + status +
                ", categories=" + categories +
                ", comments=" + comments +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}

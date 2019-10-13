package net.therap.blog.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "Message.findAll",
                query = "FROM Message as m WHERE m.isDeleted = FALSE"),
        @NamedQuery(name = "Message.findSentMessages",
                query = "FROM Message WHERE sender_id = :senderId AND isDeleted = FALSE"),
        @NamedQuery(name = "Message.findReceivedMessages",
                query = "FROM Message WHERE receiver_id = :receiverId AND isDeleted = FALSE"),
})
public class Message implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String body;

    @OneToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_deleted",columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isDeleted;

    @Column(name = "is_seen",columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isSeen;

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isIsSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean is_seen) {
        this.isSeen = is_seen;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date created_at) {
        this.createdAt = created_at;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updatedAt = updated_at;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", is_seen=" + isSeen +
                ", is_deleted=" + isDeleted +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt +
                '}';
    }
}

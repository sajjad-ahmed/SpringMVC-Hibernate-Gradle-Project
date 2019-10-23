package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author sajjad.ahmed
 * @since 10/3/19.
 */
@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(name = "Message.findAll",
                query = "FROM Message as m " +
                        "WHERE m.isDeleted = FALSE"),
        @NamedQuery(name = "Message.findSentMessages",
                query = "FROM Message " +
                        "WHERE sender_id = :senderId " +
                        "AND isDeleted = FALSE " +
                        "ORDER BY created_at " +
                        "DESC"),
        @NamedQuery(name = "Message.findReceivedMessages",
                query = "FROM Message " +
                        "WHERE receiver_id = :receiverId " +
                        "AND isDeleted = FALSE " +
                        "ORDER BY created_at " +
                        "DESC"),
})
public class Message extends BaseDomain {

    @NotNull
    @Size(min = 4, max = 200)
    private String body;

    @OneToOne
    @NotNull
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToOne
    @NotNull
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "is_deleted", columnDefinition = "TINYINT")
    public boolean isDeleted;

    @Column(name = "is_seen", columnDefinition = "TINYINT")
    public boolean isSeen;

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

    public String getFormattedTime(){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(getCreatedAt().toInstant(), ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mm:ss"));
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + getId() +
                ", body='" + body + '\'' +
                ", is_seen=" + isSeen +
                ", is_deleted=" + isDeleted +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", created_at=" + getCreatedAt() +
                ", updated_at=" + getUpdatedAt() +
                '}';
    }
}

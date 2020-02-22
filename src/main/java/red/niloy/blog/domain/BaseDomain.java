package red.niloy.blog.domain;

import red.niloy.blog.exception.NotFoundException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 10/22/19.
 */
@MappedSuperclass
public class BaseDomain<T extends BaseDomain> implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNew() {
        return getId() == 0;
    }

    public String getFormattedDate(DateTimeFormatter format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(getCreatedAt().toInstant(), ZoneId.systemDefault());
        return localDateTime.format(format);
    }

    public void validate(T t) {
        if (Objects.isNull(t)) {
            throw new NotFoundException(t.getClass().getSimpleName());
        }
    }

    public void checkOptionalIsPresent(Optional<T> optional) {
        if (!optional.isPresent()) {
            throw new NotFoundException(optional.getClass().getSimpleName());
        }
    }
}

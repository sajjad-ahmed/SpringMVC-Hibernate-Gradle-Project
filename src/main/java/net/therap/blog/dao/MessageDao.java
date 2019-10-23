package net.therap.blog.dao;

import net.therap.blog.domain.Message;
import net.therap.blog.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class MessageDao extends BaseDao<Message> {

    public MessageDao() {
        super(Message.class);
    }

    @Override
    public Optional<Message> delete(long id) {
        Message message = em.find(Message.class, id);
        if (Objects.nonNull(message)) {
            message.setIsDeleted(true);
            em.merge(message);
        } else {
            throw new NotFoundException("Message");
        }
        return Optional.of(message);
    }

    public List<Message> findSentMessages(long id) {
        try {
            return em.createNamedQuery("Message.findSentMessages", Message.class)
                    .setParameter("senderId", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Message> findReceivedMessages(long id) {
        try {
            return em.createNamedQuery("Message.findReceivedMessages", Message.class)
                    .setParameter("receiverId", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
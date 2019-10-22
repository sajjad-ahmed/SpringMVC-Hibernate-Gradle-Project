package net.therap.blog.dao;

import net.therap.blog.domain.Message;
import net.therap.blog.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class MessageDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Message save(Message message) {
        if (message.isNew()) {
            em.persist(message);
            em.flush();
        } else {
            em.merge(message);
        }
        return message;
    }

    public Message find(long id) {
        return em.find(Message.class, id);
    }

    public List<Message> findAll() {
        return em.createNamedQuery("Message.findAll", Message.class).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Message message = em.find(Message.class, id);
        if (Objects.nonNull(message)) {
            message.setIsDeleted(true);
            em.merge(message);
        } else {
            throw new NotFoundException("Message");
        }
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
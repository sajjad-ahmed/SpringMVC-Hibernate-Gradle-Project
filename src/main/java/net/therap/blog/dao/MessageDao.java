package net.therap.blog.dao;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */

import net.therap.blog.domain.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageDao {

    private static final String FIND_ALL_MESSAGE = "FROM Message";

    private static final String GET_ALL_SENT_MESSAGES = "FROM Message WHERE sender_id = :senderId";

    private static final String GET_ALL_RECEIVED_MESSAGES = "FROM Message WHERE receiver_id = :receiverId";

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Optional<Message> save(Message message) {
        if (message.getId() == 0) {
            em.persist(message);
            em.flush();
        } else {
            em.merge(message);
        }
        return Optional.of(message);
    }

    @Transactional
    public Message find(long id) {
        return em.find(Message.class, id);
    }

    @Transactional
    public List<Message> getAll() {
        return em.createQuery(FIND_ALL_MESSAGE).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Message message = em.find(Message.class, id);
        if (message != null) {
            try {
                em.remove(message);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Message> sentMessages(long id) {
        try {
            TypedQuery<Message> query = em.createQuery(GET_ALL_SENT_MESSAGES, Message.class);
            query.setParameter("senderId", id);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Message> receivedMessages(long id) {
        try {
            TypedQuery<Message> query = em.createQuery(GET_ALL_RECEIVED_MESSAGES, Message.class);
            query.setParameter("receiverId", id);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
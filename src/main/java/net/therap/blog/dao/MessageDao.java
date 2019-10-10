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

    public Message find(long id) {
        return em.find(Message.class, id);
    }

    public List<Message> findAll() {
        return em.createNamedQuery("Message.findAll", Message.class).getResultList();
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

    public List<Message> findSentMessages(long id) {
        try {
            TypedQuery<Message> query = em.createNamedQuery("Message.findSentMessages", Message.class);
            query.setParameter("senderId", id);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Message> findReceivedMessages(long id) {
        try {
            TypedQuery<Message> query = em.createNamedQuery("Message.findReceivedMessages", Message.class);
            query.setParameter("receiverId", id);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
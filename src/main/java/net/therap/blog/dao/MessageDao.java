package net.therap.blog.dao;

import net.therap.blog.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public boolean delete(long id) {
        Message message = em.find(Message.class, id);
        message.setIsDeleted(true);
        em.merge(message);
        return true;
    }

    public List<Message> findSentMessages(long id) {
        return em.createNamedQuery("Message.findSentMessages", Message.class)
                .setParameter("senderId", id)
                .getResultList();
    }

    public List<Message> findReceivedMessages(long id) {
        return em.createNamedQuery("Message.findReceivedMessages", Message.class)
                .setParameter("receiverId", id)
                .getResultList();
    }
}
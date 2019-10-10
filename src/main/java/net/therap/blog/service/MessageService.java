package net.therap.blog.service;

import net.therap.blog.dao.MessageDao;
import net.therap.blog.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public void add(Message message) {
        messageDao.save(message);
    }

    public List<Message> getAll() {
        return messageDao.getAll();
    }

    public void delete(long id) {
        messageDao.delete(id);
    }

    public Message find(long id) {
        return messageDao.find(id);
    }

    public List<Message> getSentMessages(long userId) {
        List<Message> messages = messageDao.sentMessages(userId);
        messages.removeIf(x -> x.is_deleted);
        return messages;
    }

    public List<Message> getReceivedMessages(long userId) {
        List<Message> messages = messageDao.receivedMessages(userId);
        messages.removeIf(x -> x.is_deleted);
        return messages;
    }
}

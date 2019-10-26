package net.therap.blog.service;

import net.therap.blog.dao.CommentDao;
import net.therap.blog.dao.MessageDao;
import net.therap.blog.dao.UserDao;
import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import net.therap.blog.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class UserService implements Constants {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostService postService;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private CommentDao commentDao;

    public User save(User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        return userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public List<User> findAllExceptSelf(long id) {
        List<User> users = userDao.findAll();
        users.removeIf(i -> i.getId() == id);
        return users;
    }

    public void delete(long id) {
        deletePostsByUser(id);
        deleteCommentsByUser(id);
        deleteMessagesByUser(id);
        userDao.delete(id);
    }

    public Optional<User> find(long id) {
        return userDao.find(id);
    }

    public boolean isEmailAlreadyInUse(String value) {
        return userDao.findUserByEmail(value).isPresent();
    }

    private void deleteMessagesByUser(long id) {
        messageDao.findAll().forEach(i -> {
            if (i.getSender().getId() == id) {
                messageDao.delete(i.getId());
            }
            if (i.getReceiver().getId() == id) {
                messageDao.delete(i.getId());
            }
        });
    }

    private void deleteCommentsByUser(long id) {
        commentDao.findAll()
                .stream().filter(i -> i.getUser().getId() == id)
                .peek(i -> commentDao.delete(i.getId()));
    }

    private void deletePostsByUser(long id) {
        postService.findAll().stream()
                .filter(i -> i.getCreator().getId() == id)
                .peek(i -> postService.delete(i.getId()));
    }
}

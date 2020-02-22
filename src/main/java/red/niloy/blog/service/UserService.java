package red.niloy.blog.service;

import red.niloy.blog.dao.CommentDao;
import red.niloy.blog.dao.MessageDao;
import red.niloy.blog.dao.UserDao;
import red.niloy.blog.domain.User;
import red.niloy.blog.util.Constants;
import red.niloy.blog.util.Encryption;
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
        commentDao.findAll().forEach(i -> {
            if (i.getUser().getId() == id) {
                commentDao.delete(i.getId());
            }
        });
    }

    private void deletePostsByUser(long id) {
        postService.findAll().forEach(i -> {
            if (i.getCreator().getId() == id) {
                postService.delete(i.getId());

            }
        });
    }
}

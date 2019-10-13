package net.therap.blog.service;

import net.therap.blog.dao.CommentDao;
import net.therap.blog.dao.MessageDao;
import net.therap.blog.dao.UserDao;
import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import net.therap.blog.util.ROLES;
import net.therap.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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

    public void add(User user, HttpSession session) {
        if (hasAccess(session)) {
            userDao.save(user);
        } else {
            return;
        }
    }

    public void signUp(User user) {
        userDao.save(user);
    }

    public List<User> findAll(HttpSession session) {
        if (hasAccess(session)) {
            return userDao.findAll();
        } else {
            return null;
        }
    }

    public List<User> findAllExceptSelf(long id) {
        List<User> users = userDao.findAll();
        users.removeIf(i -> i.getId() == id);
        return users;
    }

    public void delete(long id) {
        commentDao.findAll().forEach(i -> {
            if (i.getUserId().getId() == id) {
                commentDao.delete(i.getId());
            }
        });
        postService.findAll().forEach(i -> {
            if (i.getCreator().getId() == id) {
                postService.delete(i.getId());
            }
        });

        messageDao.findAll().forEach(i -> {
            if (i.getSender().getId() == id) {
                messageDao.delete(i.getId());
            }
        });
        userDao.delete(id);
    }

    public User find(long id) {
        return userDao.find(id);
    }


    public boolean isEmailAlreadyInUse(String value) {
        return Objects.nonNull(userDao.findUserByEmail(value));
    }

    private boolean hasAccess(HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        return userRole.equals(ROLES.ADMIN.name());
    }
}

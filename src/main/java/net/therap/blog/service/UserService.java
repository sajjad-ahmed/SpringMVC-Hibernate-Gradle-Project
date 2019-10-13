package net.therap.blog.service;

import net.therap.blog.dao.UserDao;
import net.therap.blog.domain.User;
import net.therap.blog.util.Constants;
import net.therap.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostService postService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CommentService commentService;

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

    public List<User> getAll(HttpSession session) {
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
        commentService.getAll().forEach(i -> {
            if (i.getUserId().getId() == id) {
                commentService.delete(i.getId());
            }
        });
        postService.getAll().forEach(i -> {
            if (i.getCreator().getId() == id) {
                postService.delete(i.getId());
            }
        });

        messageService.getAll().forEach(i -> {
            if (i.getSender().getId() == id) {
                messageService.delete(i.getId());
            }
        });
        userDao.delete(id);
    }

    public User find(long id) {
        return userDao.find(id);
    }


    public boolean isEmailAlreadyInUse(String value) {
        boolean inUse = true;
        if (userDao.findUserByEmail(value) == null) {
            inUse = false;
        }
        return inUse;
    }

    private boolean hasAccess(HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        if (userRole.equals(Constants.ROLES[0])) {
            return true;
        } else {
            return false;
        }
    }
}

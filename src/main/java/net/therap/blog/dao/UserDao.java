package net.therap.blog.dao;

import net.therap.blog.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public Optional<User> findUserByEmail(String email) {
        List<User> users = em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        List<User> users = em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }
}
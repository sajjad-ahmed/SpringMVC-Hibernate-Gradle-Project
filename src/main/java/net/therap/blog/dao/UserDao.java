package net.therap.blog.dao;

import net.therap.blog.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
        try {
            return Optional.of(
                    em.createNamedQuery("User.findByEmail", User.class)
                            .setParameter("email", email)
                            .getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        try {
            return Optional.of(
                    em.createNamedQuery("User.findByEmailAndPassword", User.class)
                            .setParameter("email", email)
                            .setParameter("password", password)
                            .getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }
}
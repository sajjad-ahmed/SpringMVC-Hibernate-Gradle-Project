package net.therap.blog.dao;

import net.therap.blog.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User findUserByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
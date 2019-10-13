package net.therap.blog.dao;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */

import net.therap.blog.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDao {

    private static final String FIND_ALL_USERS = "FROM User";

    private static final String FIND_USER_BY_EMAIL = "FROM User WHERE email = :email";

    @PersistenceContext
    EntityManager em;

    @Transactional
    public User save(User user) {
        if (user.getId() == 0) {
            em.persist(user);
            em.flush();
        } else {
            em.merge(user);
        }
        return user;
    }

    public User find(long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery(FIND_ALL_USERS).getResultList();
    }

    @Transactional
    public Optional<User> delete(long id) {
        User user = em.find(User.class, id);
        if (Objects.nonNull(user)) {
            try {
                em.remove(user);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public User findUserByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery(FIND_USER_BY_EMAIL, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
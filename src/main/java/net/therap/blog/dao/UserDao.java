package net.therap.blog.dao;

import net.therap.blog.domain.User;
import net.therap.blog.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User save(User user) {
        if (user.isNew()) {
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
        return em.createNamedQuery("User.findAll", User.class).getResultList();
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
        } else {
            throw new NotFoundException("User");
        }
        return Optional.empty();
    }

    public User findUserByEmail(String email) {
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
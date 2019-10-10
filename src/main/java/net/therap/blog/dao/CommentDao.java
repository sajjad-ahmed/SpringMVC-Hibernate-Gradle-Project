package net.therap.blog.dao;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */

import net.therap.blog.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDao {

    private static final String FIND_ALL_MESSAGE = "FROM Comment";

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Optional<Comment> save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            em.flush();
        } else {
            em.merge(comment);
        }
        return Optional.of(comment);
    }

    @Transactional
    public Comment find(long id) {
        return em.find(Comment.class, id);
    }

    @Transactional
    public List<Comment> getAll() {
        return em.createQuery(FIND_ALL_MESSAGE).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            try {
                em.remove(comment);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        }
    }
}
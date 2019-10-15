package net.therap.blog.dao;

import net.therap.blog.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            em.flush();
        } else {
            em.merge(comment);
        }
        return comment;
    }

    public Comment find(long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findAll() {
        return em.createNamedQuery("Comment.findAll", Comment.class).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Comment comment = em.find(Comment.class, id);
        if (Objects.nonNull(comment)) {
            try {
                em.remove(comment);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        }
    }
}
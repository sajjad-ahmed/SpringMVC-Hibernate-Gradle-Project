package net.therap.blog.dao;

import net.therap.blog.domain.Post;
import net.therap.blog.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class PostDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Post save(Post post) {
        if (post.isNew()) {
            em.merge(post);
            em.flush();
        } else {
            em.merge(post);
        }
        return post;
    }

    public Post find(long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createNamedQuery("Post.findAll", Post.class).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Post post = em.find(Post.class, id);
        if (Objects.nonNull(post)) {
            try {
                em.remove(post);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotFoundException("Post");
        }
    }

    public Post findBy(String uri) {
        try {
            return em.createNamedQuery("Post.findByUri", Post.class)
                    .setParameter("uri", uri)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
package net.therap.blog.dao;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */

import net.therap.blog.domain.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Repository
public class PostDao {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Post save(Post post) {
        if (post.getId() == 0) {
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
        }
    }

    public Post findBy(String uri) {
        try {
            TypedQuery<Post> query = em.createNamedQuery("Post.findByUri", Post.class);
            query.setParameter("uri", uri);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
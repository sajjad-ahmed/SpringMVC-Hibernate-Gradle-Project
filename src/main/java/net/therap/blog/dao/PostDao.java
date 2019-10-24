package net.therap.blog.dao;

import net.therap.blog.domain.Post;
import net.therap.blog.util.STATUS;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class PostDao extends BaseDao<Post> {

    public PostDao() {
        super(Post.class);
    }

    @Override
    public Optional<Post> save(Post post) {
        em.merge(post);
        em.flush();
        return Optional.of(post);
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

    public List<Post> findByStatus(List<STATUS> status) {
        try {
            return em.createNamedQuery("Post.findAllByStatus", Post.class)
                    .setParameter("status", status)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Post> findByRoleAndCategory(long categoryId, List<STATUS> status) {
        try {
            return em.createNamedQuery("Post.findAllByStatusAndCategory", Post.class)
                    .setParameter("categoryId", categoryId)
                    .setParameter("status", status)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
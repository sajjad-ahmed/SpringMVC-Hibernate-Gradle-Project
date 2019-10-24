package net.therap.blog.dao;

import net.therap.blog.domain.Post;
import net.therap.blog.util.Status;
import org.springframework.stereotype.Repository;

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
    public Post save(Post post) {
        post = em.merge(post);
        em.flush();
        return post;
    }

    public Optional<Post> findBy(String uri) {
        return Optional.of(em.createNamedQuery("Post.findByUri", Post.class)
                .setParameter("uri", uri)
                .getSingleResult());
    }

    public List<Post> findByStatus(List<Status> status) {
        return em.createNamedQuery("Post.findAllByStatus", Post.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Post> findByRoleAndCategory(long categoryId, List<Status> status) {
        return em.createNamedQuery("Post.findAllByStatusAndCategory", Post.class)
                .setParameter("categoryId", categoryId)
                .setParameter("status", status)
                .getResultList();
    }
}
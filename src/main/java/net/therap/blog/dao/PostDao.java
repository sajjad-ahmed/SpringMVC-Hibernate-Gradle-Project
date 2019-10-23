package net.therap.blog.dao;

import net.therap.blog.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

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
        em.merge(post);
        em.flush();
        return post;
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
package net.therap.blog.dao;

import net.therap.blog.domain.Comment;
import org.springframework.stereotype.Repository;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class CommentDao extends BaseDao<Comment> {

    public CommentDao() {
        super(Comment.class);
    }
}
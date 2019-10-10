package net.therap.blog.service;

import net.therap.blog.dao.CommentDao;
import net.therap.blog.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public void add(Comment comment) {
        commentDao.save(comment);
    }

    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    public void delete(long id) {
        commentDao.delete(id);
    }

    public Comment find(long id) {
        return commentDao.find(id);
    }


}

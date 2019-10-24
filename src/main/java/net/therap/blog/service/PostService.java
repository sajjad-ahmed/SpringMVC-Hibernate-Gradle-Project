package net.therap.blog.service;

import net.therap.blog.dao.PostDao;
import net.therap.blog.domain.Post;
import net.therap.blog.util.Constants;
import net.therap.blog.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class PostService implements Constants {

    @Autowired
    private PostDao postDao;

    public Post save(Post post) {
        return postDao.save(post);
    }

    public List<Post> findAll() {
        return postDao.findAll();
    }

    public void delete(long id) {
        postDao.delete(id);
    }

    public Optional<Post> find(long id) {
        return postDao.find(id);
    }

    public boolean isUriAlreadyInUse(String uri) {
        return Objects.nonNull(postDao.findBy(uri));
    }

    public Optional<Post> findBy(String uri) {
        return postDao.findBy(uri);
    }

    public List<Post> findByStatus(List<Status> status) {
        return postDao.findByStatus(status);
    }

    public List<Post> findByRoleAndCategory(long categoryId, List<Status> statuses) {
        return postDao.findByRoleAndCategory(categoryId, statuses);
    }
}

package net.therap.blog.service;

import net.therap.blog.dao.PostDao;
import net.therap.blog.domain.Post;
import net.therap.blog.util.Constants;
import net.therap.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class PostService {

    @Autowired
    private PostDao postDao;

    public void add(Post post) {
        postDao.save(post);
    }

    public List<Post> getAll() {
        return postDao.getAll();
    }


    public List<Post> getPostByAccess(HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        List<Post> posts = postDao.getAll();
        if (userRole.equals(Constants.ROLES[0])) {
            return posts;
        } else if (userRole.equals(Constants.ROLES[1])) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(0) == Constants.ACCESS_DENY);
        } else if (userRole.equals(Constants.ROLES[2])) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(1) == Constants.ACCESS_DENY);
        } else if (userRole.equals(Constants.ACCESS_GUEST)) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(2) == Constants.ACCESS_DENY);
        }
        return posts;
    }

    public void delete(long id) {
        postDao.delete(id);
    }

    public Post find(long id) {
        return postDao.find(id);
    }

    public boolean isUriAlreadyInUse(String uri) {
        boolean inUse = true;
        if (postDao.findPostByUri(uri) == null) {
            inUse = false;
        }
        return inUse;
    }

}

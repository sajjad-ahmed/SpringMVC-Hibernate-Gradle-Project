package net.therap.blog.service;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import net.therap.blog.dao.PostDao;
import net.therap.blog.domain.Post;
import net.therap.blog.util.Constants;
import net.therap.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class PostService implements Constants {

    @Autowired
    private PostDao postDao;

    public void add(Post post) {
        postDao.save(post);
    }

    public List<Post> getAll() {
        return postDao.findAll();
    }


    public List<Post> getPostByAccess(HttpSession session) {
        String userRole = SessionUtil.getUserRole(session);
        List<Post> posts = postDao.findAll();
        if (userRole.equals(ROLES.ADMIN.name())) {
            return posts;
        } else if (userRole.equals(ROLES.AUTHOR.name())) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(0) == ACCESS_DENY);
        } else if (userRole.equals(ROLES.SUBSCRIBER.name())) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(1) == ACCESS_DENY);
        } else if (userRole.equals(ACCESS_GUEST)) {
            posts.removeIf(i ->
                    String.valueOf(i.getAccess()).charAt(2) == ACCESS_DENY);
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
        return Objects.nonNull(postDao.findPostByUri(uri));
    }

}

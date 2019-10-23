package net.therap.blog.dao;

import net.therap.blog.domain.Category;
import net.therap.blog.util.STATUS;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class CategoryDao extends BaseDao<Category> {

    public CategoryDao() {
        super(Category.class);
    }
}

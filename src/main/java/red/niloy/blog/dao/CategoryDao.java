package red.niloy.blog.dao;

import red.niloy.blog.domain.Category;
import org.springframework.stereotype.Repository;

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

package net.therap.blog.dao;

import net.therap.blog.domain.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */
@Repository
public class CategoryDao {

    private static final String FIND_ALL_CATEGORY = "FROM Category";

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Category save(Category category) {
        if (category.getId() == 0) {
            em.persist(category);
            em.flush();
        } else {
            em.merge(category);
        }
        return category;
    }

    public Category find(long id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createQuery(FIND_ALL_CATEGORY).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Category category = em.find(Category.class, id);
        if (Objects.nonNull(category)) {
            try {
                em.remove(category);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        }
    }
}

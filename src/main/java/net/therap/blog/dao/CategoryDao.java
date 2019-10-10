package net.therap.blog.dao;

/**
 * @author sajjad.ahmed
 * @since 9/20/19.
 */

import net.therap.blog.domain.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDao {

    private static final String FIND_ALL_CATEGORY = "FROM Category";

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Optional<Category> save(Category category) {
        if (category.getId() == 0) {
            em.persist(category);
            em.flush();
        } else {
            em.merge(category);
        }
        return Optional.of(category);
    }

    @Transactional
    public Category find(long id) {
        return em.find(Category.class, id);
    }

    @Transactional
    public List<Category> getAll() {
        return em.createQuery(FIND_ALL_CATEGORY).getResultList();
    }

    @Transactional
    public void delete(long id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            try {
                em.remove(category);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        }
    }
}

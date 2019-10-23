package net.therap.blog.dao;

import net.therap.blog.domain.BaseDomain;
import net.therap.blog.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

/**
 * @author sajjad.ahmed
 * @since 10/22/19.
 */
public abstract class BaseDao<T extends BaseDomain> {

    @PersistenceContext
    EntityManager em;

    private Class<T> clazz;

    public BaseDao(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public List<T> findAll() {
        return em.createNamedQuery(clazz.getSimpleName() + ".findAll", clazz).getResultList();
    }

    public T find(long id) {
        return em.find(clazz, id);
    }

    @Transactional
    public T save(T entity) {
        if (entity.isNew()) {
            em.persist(entity);
            em.flush();
        } else {
            em.merge(entity);
        }
        return entity;
    }

    @Transactional
    public void delete(long id) {
        T item = em.find(clazz, id);
        if (Objects.nonNull(item)) {
            try {
                em.remove(item);
            } catch (IllegalStateException | PersistenceException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotFoundException(clazz.getSimpleName());
        }
    }
}

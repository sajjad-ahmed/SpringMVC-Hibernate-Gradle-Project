package red.niloy.blog.dao;

import red.niloy.blog.domain.BaseDomain;
import red.niloy.blog.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<T> find(long id) {
        return Optional.of(em.find(clazz, id));
    }

    @Transactional
    public T save(T entity) {
        if (entity.isNew()) {
            em.persist(entity);
            em.flush();
        } else {
            entity = em.merge(entity);
        }
        return entity;
    }

    @Transactional
    public boolean delete(long id) {
        T item = em.find(clazz, id);
        if (Objects.isNull(item)) {
            throw new NotFoundException(clazz.getSimpleName());
        }
        em.remove(item);
        return true;
    }
}

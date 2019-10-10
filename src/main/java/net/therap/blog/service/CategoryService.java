package net.therap.blog.service;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public void add(Category category) {
        categoryDao.save(category);
    }

    public List<Category> getAll() {
        return categoryDao.findAll();
    }

    public void delete(long id) {
        categoryDao.delete(id);
    }

    public Category find(long id) {
        return categoryDao.find(id);
    }
}

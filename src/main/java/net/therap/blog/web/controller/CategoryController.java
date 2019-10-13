package net.therap.blog.web.controller;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.domain.Category;
import net.therap.blog.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = URL.CATEGORY_MANAGEMENT_VIEW, method = RequestMethod.GET)
    public String showCategoryView(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryDao.findAll());
        return URL.CATEGORY_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = URL.CATEGORY_MANAGE, method = RequestMethod.GET)
    public String showCategoryManage(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryDao.findAll());
        return URL.CATEGORY_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = URL.CATEGORY_ADD, method = RequestMethod.POST)
    public String addCategoryHandler(@ModelAttribute @Valid Category category,
                                     BindingResult error) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        for (ConstraintViolation<Category> violation : validator.validate(category)) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            error.addError(new FieldError("category", propertyPath, message));
        }
        if (error.hasErrors()) {
            return URL.CATEGORY_MANAGEMENT_VIEW;
        }
        categoryDao.save(category);
        return "redirect:" + URL.CATEGORY_MANAGE;
    }

    @RequestMapping(value = URL.CATEGORY_UPDATE)
    public String updateCategoryHandler(@PathVariable("id") long id, Model model) {
        Category category = categoryDao.find(id);
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryDao.findAll());
        return URL.CATEGORY_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = URL.CATEGORY_DELETE)
    public String deleteCategoryHandler(@PathVariable("id") long id, Model model) {
        Category category = categoryDao.find(id);
        categoryDao.delete(category.getId());
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryDao.findAll());
        return "redirect:" + URL.CATEGORY_MANAGE;
    }
}


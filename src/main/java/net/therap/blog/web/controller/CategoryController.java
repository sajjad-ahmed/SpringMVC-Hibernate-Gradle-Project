package net.therap.blog.web.controller;

import net.therap.blog.dao.CategoryDao;
import net.therap.blog.domain.Category;
import net.therap.blog.exception.WebSecurityException;
import net.therap.blog.util.Constants;
import net.therap.blog.util.ROLES;
import net.therap.blog.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.blog.util.URL.*;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Controller
public class CategoryController implements Constants {

    @Autowired
    private CategoryDao categoryDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = CATEGORY_MANAGE, method = RequestMethod.GET)
    public String showCategoryManage(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryDao.findAll());
        return CATEGORY_MANAGEMENT_VIEW;
    }

    @RequestMapping(value = CATEGORY_ADD, method = RequestMethod.GET)
    public String showAddCategoryView(@ModelAttribute Category category,
                                      Model model,
                                      HttpSession session) {
        if (!SessionUtil.isAdmin(session)) {
            throw new WebSecurityException();
        }
        category = category.isNew() ? category : categoryDao.find(category.getId());
        model.addAttribute("category", category);
        return CATEGORY_ADD_VIEW;
    }

    @RequestMapping(value = CATEGORY_ADD, method = RequestMethod.POST)
    public String addCategoryHandler(@Valid @ModelAttribute Category category,
                                     Errors errors,
                                     Model model,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session) {
        if (!SessionUtil.isAdmin(session)) {
            throw new WebSecurityException();
        }
        if (errors.hasErrors()) {
            model.addAttribute("category", category);
            return CATEGORY_ADD_VIEW;
        }
        redirectAttributes.addFlashAttribute(CONFIRMATION, category.isNew() ? "ADDED" : "UPDATED");
        categoryDao.save(category);
        return "redirect:" + CATEGORY_MANAGE;
    }

    @RequestMapping(value = CATEGORY_DELETE, method = RequestMethod.POST)
    public String deleteCategoryHandler(@ModelAttribute Category category,
                                        Model model,
                                        HttpSession session) {
        if (!SessionUtil.isAdmin(session)) {
            throw new WebSecurityException();
        }
        category = categoryDao.find(category.getId());
        categoryDao.delete(category.getId());
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryDao.findAll());
        return "redirect:" + CATEGORY_MANAGE;
    }

}

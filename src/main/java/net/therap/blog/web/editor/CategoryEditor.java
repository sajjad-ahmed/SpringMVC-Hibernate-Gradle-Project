package net.therap.blog.web.editor;

import net.therap.blog.domain.Category;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import java.util.Collection;

/**
 * @author sajjad.ahmed
 * @since 10/6/19.
 */
public class CategoryEditor extends CustomCollectionEditor {

    public CategoryEditor(Class<? extends Collection> collectionType) {
        super(collectionType);
    }

    protected Object convertElement(Object element) {
        if (element instanceof Category) {
            return element;
        }
        if (element instanceof String) {
            Category category = new Category();
            category.setId(Long.parseLong((String) element));
            return category;
        }
        return null;
    }
}

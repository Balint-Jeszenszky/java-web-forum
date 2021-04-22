package hu.bme.aut.javaweb.forum.datasource.mock;

import hu.bme.aut.javaweb.forum.datasource.CategoryDataSource;
import hu.bme.aut.javaweb.forum.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MockCategoryDataSource implements CategoryDataSource {
    private List<Category> categories = new ArrayList(Arrays.asList(new Category(1, "Category 1")));
    private int id = 2;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = null;

        for (Category c : categories) {
            if (c.getId() == id) {
                return c;
            }
        }

        throw new NoSuchElementException("Category not found");
    }

    @Override
    public Category createCategory(Category category) {
        category.setId(id++);
        categories.add(category);
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        for (Category c : categories) {
            if (c.getId() == category.getId()) {
                categories.remove(c);
                categories.add(category);
                return category;
            }
        };

        throw new NoSuchElementException("Category not found");
    }

    @Override
    public void deleteCategoryById(int id) {
        for (Category c : categories) {
            if (c.getId() == id) {
                categories.remove(c);
                return;
            }
        };

        throw new NoSuchElementException("Category not found");
    }
}

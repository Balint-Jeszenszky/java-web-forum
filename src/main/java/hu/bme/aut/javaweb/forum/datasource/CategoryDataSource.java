package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Category;

import java.util.List;

public interface CategoryDataSource {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategoryById(int id);
}

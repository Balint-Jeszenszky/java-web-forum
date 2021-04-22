package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.CategoryDataSource;
import hu.bme.aut.javaweb.forum.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryDataSource categoryDataSource;

    public CategoryService(CategoryDataSource categoryDataSource) {
        this.categoryDataSource = categoryDataSource;
    }

    public List<Category> getAllCategories() {
        return categoryDataSource.getAllCategories();
    }

    public  Category getCategoryById(int id) {
        return categoryDataSource.getCategoryById(id);
    }

    public Category createCategory(Category category) {
        return categoryDataSource.createCategory(category);
    }

    public Category updateCategory(Category category) {
        return categoryDataSource.updateCategory(category);
    }

    public void deleteCategoryById(int id) {
        categoryDataSource.deleteCategoryById(id);
    }
}

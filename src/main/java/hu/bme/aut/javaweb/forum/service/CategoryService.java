package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.CategoryDataSource;
import hu.bme.aut.javaweb.forum.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryDataSource categoryDataSource;

    public CategoryService(CategoryDataSource categoryDataSource) {
        this.categoryDataSource = categoryDataSource;
    }

    public List<Category> getAllCategories() {
        return categoryDataSource.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryDataSource.findById(id);

        if (category.isEmpty()) {
            throw new NoSuchElementException("Category not found");
        }

        return category.get();
    }

    public Category createCategory(Category category) {
        return categoryDataSource.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryDataSource.save(category);
    }

    public void deleteCategoryById(Long id) {
        categoryDataSource.deleteById(id);
    }
}

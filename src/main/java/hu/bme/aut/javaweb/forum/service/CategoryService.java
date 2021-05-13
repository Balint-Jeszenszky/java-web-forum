package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.repository.CategoryRepository;
import hu.bme.aut.javaweb.forum.model.Category;
import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.model.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionService questionService;

    private void validateCategory(CategoryDTO category) {
        Optional<Category> categoryResult = categoryRepository.findByName(category.getName());

        if (categoryResult.isPresent()) {
            throw new IllegalArgumentException("Error: Category already exists!");
        }

        if (category.getName().length() < 3) {
            throw new IllegalArgumentException("Error: Category name should be at least 3 character!");
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryDTO category) {
        validateCategory(category);

        return categoryRepository.save(new Category(category.getName()));
    }

    public Category updateCategory(CategoryDTO category) {
        validateCategory(category);

        Optional<Category> categoryResult = categoryRepository.findById(category.getId());

        if (categoryResult.isEmpty()) {
            throw new NoSuchElementException("Error: Category not found!");
        }

        Category storedCategory = categoryResult.get();

        storedCategory.setName(category.getName());

        return categoryRepository.save(storedCategory);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        List<Question> questions = questionService.getQuestionsByCategoryId(id);

        for (Question q : questions) {
            questionService.deleteQuestionById(q.getId());
        }

        categoryRepository.deleteById(id);
    }
}

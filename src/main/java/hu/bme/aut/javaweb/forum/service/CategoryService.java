package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.CategoryDataSource;
import hu.bme.aut.javaweb.forum.model.Category;
import hu.bme.aut.javaweb.forum.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDataSource categoryDataSource;

    @Autowired
    private QuestionService questionService;

    public List<Category> getAllCategories() {
        return categoryDataSource.findAll();
    }

    public Category createCategory(Category category) {
        return categoryDataSource.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryDataSource.save(category);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        List<Question> questions = questionService.getQuestionsByCategoryId(id);

        for (Question q : questions) {
            questionService.deleteQuestionById(q.getId());
        }

        categoryDataSource.deleteById(id);
    }
}

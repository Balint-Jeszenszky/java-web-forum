package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.QuestionDataSource;
import hu.bme.aut.javaweb.forum.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionDataSource questionDataSource;

    public QuestionService(QuestionDataSource questionDataSource) {
        this.questionDataSource = questionDataSource;
    }

    public List<Question> getQuestionsByCategoryId(int id) {
        return questionDataSource.getQuestionsByCategoryId(id);
    }

    public Question getQuestionById(int id) {
        return questionDataSource.getQuestionById(id);
    }

    public Question createQuestion(Question question) {
        return questionDataSource.createQuestion(question);
    }

    public Question updateQuestion(Question question) {
        return questionDataSource.updateQuestion(question);
    }

    public void deleteQuestionById(int id) {
        questionDataSource.deleteQuestionById(id);
    }

}

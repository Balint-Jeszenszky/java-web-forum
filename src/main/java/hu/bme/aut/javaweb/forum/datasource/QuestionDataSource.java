package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Question;

import java.util.List;

public interface QuestionDataSource {
    List<Question> getQuestionsByCategoryId(int id);
    Question getQuestionById(int id);
    Question createQuestion(Question question);
    Question updateQuestion(Question question);
    void deleteQuestionById(int id);
}

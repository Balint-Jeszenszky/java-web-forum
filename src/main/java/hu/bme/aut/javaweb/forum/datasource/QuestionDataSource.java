package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDataSource extends JpaRepository<Question, Long> {
//    List<Question> getQuestionsByCategoryId(int id);
//    Question getQuestionById(int id);
//    Question createQuestion(Question question);
//    Question updateQuestion(Question question);
//    void deleteQuestionById(int id);
}

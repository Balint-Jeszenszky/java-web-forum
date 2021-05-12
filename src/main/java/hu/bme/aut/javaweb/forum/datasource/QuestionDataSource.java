package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionDataSource extends JpaRepository<Question, Long> {
    List<Question> findQuestionsByCategoryId(Long id);

    @Query(nativeQuery = true, value = "SELECT TOP(10) * FROM question ORDER BY time DESC")
    List<Question> findNewestQuestions();

    List<Question> findQuestionsByUserId(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM question WHERE id IN (SELECT question_id FROM answer WHERE user_id = 25)")
    List<Question> findQuestionsAnsweredByUserId(Long id);
}

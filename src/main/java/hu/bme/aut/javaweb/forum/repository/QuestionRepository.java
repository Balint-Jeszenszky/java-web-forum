package hu.bme.aut.javaweb.forum.repository;

import hu.bme.aut.javaweb.forum.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findQuestionsByCategoryId(Long id);

    @Query(nativeQuery = true, value = "SELECT TOP(:number) * FROM question ORDER BY time DESC")
    List<Question> findNewestQuestions(@Param("number") int number);

    List<Question> findQuestionsByUserIdOrderByTimeDesc(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM question WHERE id IN (SELECT question_id FROM answer WHERE user_id = :id) ORDER BY time DESC")
    List<Question> findQuestionsAnsweredByUserId(@Param("id") Long id);
}

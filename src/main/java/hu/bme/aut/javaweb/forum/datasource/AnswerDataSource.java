package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerDataSource extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByQuestionId(Long id);
    void deleteAnswersByQuestionId(Long id);
}

package hu.bme.aut.javaweb.forum.repository;

import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByQuestionId(Long id);
    void deleteAnswersByQuestionId(Long id);
}

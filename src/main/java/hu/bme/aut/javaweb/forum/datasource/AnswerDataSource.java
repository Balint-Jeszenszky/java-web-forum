package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerDataSource extends JpaRepository<Answer, Long> {
//    List<Answer> getAnswersByQuestionId(int id);
//    Answer getAnswerById(int id);
//    Answer createAnswer(Answer answer);
//    Answer updateAnswer(Answer answer);
//    void deleteAnswerById(int id);
}

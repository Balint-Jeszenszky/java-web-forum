package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.Answer;

import java.util.List;

public interface AnswerDataSource {
    List<Answer> getAnswersByQuestionId(int id);
    Answer getAnswerById(int id);
    Answer createAnswer(Answer answer);
    Answer updateAnswer(Answer answer);
    void deleteAnswerById(int id);
}

package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.AnswerDataSource;
import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private AnswerDataSource answerDataSource;

    public AnswerService(AnswerDataSource answerDataSource) {
        this.answerDataSource = answerDataSource;
    }

    public List<Answer> getAnswersByQuestionId(int id) {
        return answerDataSource.getAnswersByQuestionId(id);
    }

    public Answer getAnswerById(int id) {
        return answerDataSource.getAnswerById(id);
    }

    public Answer createAnswer(Answer answer) {
        return answerDataSource.createAnswer(answer);
    }

    public Answer updateAnswer(Answer answer) {
        return answerDataSource.updateAnswer(answer);
    }

    public void deleteAnswerById(int id) {
        answerDataSource.deleteAnswerById(id);
    }
}

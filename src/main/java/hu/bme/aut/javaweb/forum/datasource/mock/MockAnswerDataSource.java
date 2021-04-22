package hu.bme.aut.javaweb.forum.datasource.mock;

import hu.bme.aut.javaweb.forum.datasource.AnswerDataSource;
import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MockAnswerDataSource implements AnswerDataSource {
    private List<Answer> answers = new ArrayList(Arrays.asList(new Answer(1, 1, 1, new Date(), "42")));
    private int id = 2;

    @Override
    public List<Answer> getAnswersByQuestionId(int id) {
        return answers.stream().filter(a -> a.getQuestionId() == id).collect(Collectors.toList());
    }

    @Override
    public Answer getAnswerById(int id) {
        Answer answer = null;

        for (Answer a : answers) {
            if (a.getId() == id) {
                answer = a;
                break;
            }
        };

        if (answer == null) throw new NoSuchElementException("Answer not found");

        return answer;
    }

    @Override
    public Answer createAnswer(Answer answer) {
        answer.setId(id++);
        answers.add(answer);
        return answer;
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        for (Answer a : answers) {
            if (a.getId() == answer.getId()) {
                /// TODO checks
                answers.remove(a);
                answers.add(answer);
                return answer;
            }
        };

        throw new NoSuchElementException("Answer not found");
    }

    @Override
    public void deleteAnswerById(int id) {
        for (Answer a : answers) {
            if (a.getId() == id) {
                answers.remove(a);
                return;
            }
        }
        throw new NoSuchElementException("Answer not found");
    }
}

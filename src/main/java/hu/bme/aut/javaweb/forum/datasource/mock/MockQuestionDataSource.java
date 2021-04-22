package hu.bme.aut.javaweb.forum.datasource.mock;

import hu.bme.aut.javaweb.forum.datasource.QuestionDataSource;
import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MockQuestionDataSource implements QuestionDataSource {
    private List<Question> questions = new ArrayList(Arrays.asList(new Question(1, 1, 1, "interestin question", "i dont know", new Date())));
    private int id = 2;

    public List<Question> getQuestionsByCategoryId(int id) {
        return questions.stream().filter(q -> q.getCategoryId() == id).collect(Collectors.toList());
    }

    public Question getQuestionById(int id) {
        Question question = null;

        for (Question u : questions) {
            if (u.getId() == id) {
                question = u;
                break;
            }
        };

        if (question == null) throw new NoSuchElementException("Question not found");

        return question;
    }

    public Question createQuestion(Question question) {
        question.setId(id++);

        questions.add(question);

        return question;
    }

    public Question updateQuestion(Question question) {
        for (Question q : questions) {
            if (q.getId() == question.getId()) {
                /// TODO checks
                questions.remove(q);
                questions.add(question);
                return question;
            }
        };

        throw new NoSuchElementException("Question not found");
    }

    public void deleteQuestionById(int id) {
        for (Question q : questions) {
            if (q.getId() == id) {
                questions.remove(q);
                return;
            }
        }
        throw new NoSuchElementException("Question not found");
    }
}

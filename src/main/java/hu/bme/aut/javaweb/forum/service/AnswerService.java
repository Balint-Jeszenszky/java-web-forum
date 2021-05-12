package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.model.dto.AnswerDTO;
import hu.bme.aut.javaweb.forum.datasource.AnswerDataSource;
import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnswerService {
    private AnswerDataSource answerDataSource;

    private void validateAnswer(AnswerDTO answer) {
        if (answer.getText().length() < 3) {
            throw new IllegalArgumentException("Error: Answer should be at least 3 character!");
        }
    }

    public AnswerService(AnswerDataSource answerDataSource) {
        this.answerDataSource = answerDataSource;
    }

    public List<Answer> getAnswersByQuestionId(Long id) {
        return answerDataSource.findAnswersByQuestionId(id);
    }

    public Answer getAnswerById(Long id) {
        Optional<Answer> answer = answerDataSource.findById(id);

        if (answer.isEmpty()) {
            throw new NoSuchElementException("Answer not found");
        }

        return answer.get();
    }

    public Answer createAnswer(AnswerDTO answer, Long userId) {
        validateAnswer(answer);

        if (userId != answer.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return answerDataSource.save(
                new Answer(
                        answer.getUserId(),
                        answer.getQuestionId(),
                        new Date(),
                        answer.getText()
                )
        );
    }

    public Answer updateAnswer(AnswerDTO answer, Long userId) {
        validateAnswer(answer);

        if (userId != answer.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        Optional<Answer> answerResult = answerDataSource.findById(answer.getId());

        if (answerResult.isEmpty()) {
            throw new NoSuchElementException("Answer not found");
        }

        Answer retrievedAnswer = answerResult.get();
        retrievedAnswer.setText(answer.getText());

        return answerDataSource.save(retrievedAnswer);
    }

    public void deleteAnswerById(Long id) {
        answerDataSource.deleteById(id);
    }
}

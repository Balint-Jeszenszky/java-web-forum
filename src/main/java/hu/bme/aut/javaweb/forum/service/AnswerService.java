package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.model.dto.AnswerDTO;
import hu.bme.aut.javaweb.forum.repository.AnswerRepository;
import hu.bme.aut.javaweb.forum.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    private void validateAnswer(AnswerDTO answer) {
        if (answer.getText().trim().length() < 3) {
            throw new IllegalArgumentException("Error: Answer should be at least 3 character!");
        }
    }

    public List<Answer> getAnswersByQuestionId(Long id) {
        return answerRepository.findAnswersByQuestionId(id);
    }

    public Answer getAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);

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

        return answerRepository.save(
                new Answer(
                        answer.getUserId(),
                        answer.getQuestionId(),
                        new Date(),
                        answer.getText().trim()
                )
        );
    }

    public Answer updateAnswer(AnswerDTO answer, Long userId) {
        validateAnswer(answer);

        if (userId != answer.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        Optional<Answer> answerResult = answerRepository.findById(answer.getId());

        if (answerResult.isEmpty()) {
            throw new NoSuchElementException("Answer not found");
        }

        Answer retrievedAnswer = answerResult.get();
        retrievedAnswer.setText(answer.getText().trim());

        return answerRepository.save(retrievedAnswer);
    }

    public void deleteAnswerById(Long id) {
        answerRepository.deleteById(id);
    }
}

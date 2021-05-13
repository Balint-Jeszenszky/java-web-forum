package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.repository.AnswerRepository;
import hu.bme.aut.javaweb.forum.repository.QuestionRepository;
import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.model.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private void validateQuestion(QuestionDTO question) {
        if (question.getTitle().trim().length() < 8) {
            throw new IllegalArgumentException("Error: Title should be at least 8 character!");
        }
        if (question.getDescription().trim().length() < 16) {
            throw new IllegalArgumentException("Error: Description should be at least 16 character!");
        }
    }

    public List<Question> getQuestionsByCategoryId(Long id) {
        return questionRepository.findQuestionsByCategoryId(id);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        if (question.isEmpty()) {
            throw new NoSuchElementException("Question not found");
        }

        return question.get();
    }

    public List<Question> getNewestQuestions() { return questionRepository.findNewestQuestions(10); }

    public List<Question> getQuestionsByUserId(Long id, Long userId) {
        if (userId != id) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return questionRepository.findQuestionsByUserIdOrderByTimeDesc(id);
    }

    public List<Question> getQuestionsAnsweredByUserId(Long id, Long userId) {
        if (userId != id) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return questionRepository.findQuestionsAnsweredByUserId(id);
    }

    public Question createQuestion(QuestionDTO question, Long userId) {
        validateQuestion(question);

        if (userId != question.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return questionRepository.save(new Question(
                question.getUserId(),
                question.getCategoryId(),
                question.getTitle().trim(),
                question.getDescription().trim(),
                new Date()
        ));
    }

    public Question updateQuestion(QuestionDTO question, Long userId) {
        validateQuestion(question);

        if (userId != question.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        Optional<Question> questionResult = questionRepository.findById(question.getId());

        if (questionResult.isEmpty()) {
            throw new NoSuchElementException("Question not found");
        }

        Question retrievedQuestion = questionResult.get();
        retrievedQuestion.setTitle(question.getTitle().trim());
        retrievedQuestion.setDescription(question.getDescription().trim());

        return questionRepository.save(retrievedQuestion);
    }

    @Transactional
    public void deleteQuestionById(Long id) {
        answerRepository.deleteAnswersByQuestionId(id);
        questionRepository.deleteById(id);
    }

}

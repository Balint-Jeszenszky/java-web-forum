package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.AnswerDataSource;
import hu.bme.aut.javaweb.forum.datasource.QuestionDataSource;
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
    private QuestionDataSource questionDataSource;

    @Autowired
    private AnswerDataSource answerDataSource;

    private void validateQuestion(QuestionDTO question) {
        if (question.getTitle().length() < 8) {
            throw new IllegalArgumentException("Error: Title should be at least 8 character!");
        }
        if (question.getDescription().length() < 16) {
            throw new IllegalArgumentException("Error: Description should be at least 16 character!");
        }
    }

    public List<Question> getQuestionsByCategoryId(Long id) {
        return questionDataSource.findQuestionsByCategoryId(id);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionDataSource.findById(id);

        if (question.isEmpty()) {
            throw new NoSuchElementException("Question not found");
        }

        return question.get();
    }

    public List<Question> getNewestQuestions() { return questionDataSource.findNewestQuestions(); }

    public List<Question> getQuestionsByUserId(Long id, Long userId) {
        if (userId != id) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return questionDataSource.findQuestionsByUserIdOrderByTimeDesc(id);
    }

    public List<Question> getQuestionsAnsweredByUserId(Long id, Long userId) {
        if (userId != id) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return questionDataSource.findQuestionsAnsweredByUserId(id);
    }

    public Question createQuestion(QuestionDTO question, Long userId) {
        validateQuestion(question);

        if (userId != question.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        return questionDataSource.save(new Question(
                question.getUserId(),
                question.getCategoryId(),
                question.getTitle(),
                question.getDescription(),
                new Date()
        ));
    }

    public Question updateQuestion(QuestionDTO question, Long userId) {
        validateQuestion(question);

        if (userId != question.getUserId()) {
            throw new IllegalArgumentException("Wrong userId");
        }

        Optional<Question> questionResult = questionDataSource.findById(question.getId());

        if (questionResult.isEmpty()) {
            throw new NoSuchElementException("Question not found");
        }

        Question retrievedQuestion = questionResult.get();
        retrievedQuestion.setTitle(question.getTitle());
        retrievedQuestion.setDescription(question.getDescription());

        return questionDataSource.save(retrievedQuestion);
    }

    @Transactional
    public void deleteQuestionById(Long id) {
        answerDataSource.deleteAnswersByQuestionId(id);
        questionDataSource.deleteById(id);
    }

}

package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.AnswerDataSource;
import hu.bme.aut.javaweb.forum.datasource.QuestionDataSource;
import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.model.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionDataSource questionDataSource;
    private AnswerDataSource answerDataSource;

    public QuestionService(QuestionDataSource questionDataSource, AnswerDataSource answerDataSource) {
        this.questionDataSource = questionDataSource;
        this.answerDataSource = answerDataSource;
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

    public Question createQuestion(QuestionDTO question, Long userId) {
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

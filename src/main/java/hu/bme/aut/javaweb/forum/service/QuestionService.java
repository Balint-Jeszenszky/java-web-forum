package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.QuestionDataSource;
import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.model.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionDataSource questionDataSource;

    public QuestionService(QuestionDataSource questionDataSource) {
        this.questionDataSource = questionDataSource;
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

    public Question createQuestion(QuestionDTO question) {
        return questionDataSource.save(new Question(
                question.getUserId(),
                question.getCategoryId(),
                question.getTitle(),
                question.getDescription(),
                new Date()
        ));
    }

    public Question updateQuestion(Question question) {
        return questionDataSource.save(question);
    }

    public void deleteQuestionById(Long id) { questionDataSource.deleteById(id); }

}

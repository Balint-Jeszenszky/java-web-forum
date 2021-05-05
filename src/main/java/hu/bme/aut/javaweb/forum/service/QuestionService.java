package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.QuestionDataSource;
import hu.bme.aut.javaweb.forum.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionDataSource questionDataSource;

    public QuestionService(QuestionDataSource questionDataSource) {
        this.questionDataSource = questionDataSource;
    }

    public List<Question> getQuestionsByCategoryId(int id) {
        return questionDataSource.findAll(); // TODO
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionDataSource.findById(id);

        if (question.isEmpty()) {
            throw new NoSuchElementException("Question not found");
        }

        return question.get();
    }

    public Question createQuestion(Question question) {
        return questionDataSource.save(question);
    }

    public Question updateQuestion(Question question) {
        return questionDataSource.save(question);
    }

    public void deleteQuestionById(Long id) { questionDataSource.deleteById(id); }

}

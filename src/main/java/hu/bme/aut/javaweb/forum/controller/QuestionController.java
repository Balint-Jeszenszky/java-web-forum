package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public List<Question> getQuestionsByCategoryId(@PathVariable int id) {
        return questionService.getQuestionsByCategoryId(id);
    }

    @GetMapping("/question/{id}")
    public Question getUser(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("/question")
    @ResponseStatus(HttpStatus.CREATED)
    public Question createUser(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @PutMapping("/question")
    public Question updateUser(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/question/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
    }
}

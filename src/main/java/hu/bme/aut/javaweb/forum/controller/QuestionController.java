package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.model.Question;
import hu.bme.aut.javaweb.forum.model.dto.QuestionDTO;
import hu.bme.aut.javaweb.forum.security.services.UserDetailsImpl;
import hu.bme.aut.javaweb.forum.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNotFound(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/category/{id}")
    public List<Question> getQuestionsByCategoryId(@PathVariable Long id) {
        return questionService.getQuestionsByCategoryId(id);
    }

    @GetMapping("/question/{id}")
    public Question getUser(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/newest")
    public List<Question> getUser() {
        return questionService.getNewestQuestions();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER')")
    public List<Question> getQuestionsByUserId(Authentication authentication, @PathVariable Long id) { // TODO
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return questionService.getQuestionsByUserId(id, userId);
    }

    @GetMapping("/answeredby/{id}")
    @PreAuthorize("hasRole('USER')")
    public List<Question> getQuestionsAnsweredByUserId(Authentication authentication, @PathVariable Long id) { // TODO
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return questionService.getQuestionsAnsweredByUserId(id, userId);
    }

    @PostMapping("/question")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public Question createQuestion(Authentication authentication, @RequestBody QuestionDTO question) {
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return questionService.createQuestion(question, userId);
    }

    @PutMapping("/question")
    @PreAuthorize("hasRole('USER')")
    public Question updateUser(Authentication authentication, @RequestBody QuestionDTO question) {
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return questionService.updateQuestion(question, userId);
    }

    @DeleteMapping("/question/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) { questionService.deleteQuestionById(id); }
}

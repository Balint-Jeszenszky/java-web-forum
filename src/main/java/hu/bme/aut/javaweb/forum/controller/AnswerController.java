package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.model.dto.AnswerDTO;
import hu.bme.aut.javaweb.forum.model.Answer;
import hu.bme.aut.javaweb.forum.security.services.UserDetailsImpl;
import hu.bme.aut.javaweb.forum.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/question/{id}")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long id) {
        return answerService.getAnswersByQuestionId(id);
    }

    @GetMapping("/answer/{id}")
    public Answer getAnswer(@PathVariable Long id) {
        return answerService.getAnswerById(id);
    }

    @PostMapping("/answer")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Answer createAnswer(Authentication authentication, @RequestBody AnswerDTO answer) {
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return answerService.createAnswer(answer, userId);
    }

    @PutMapping("/answer")
    @PreAuthorize("hasRole('USER')")
    public Answer updateAnswer(Authentication authentication, @RequestBody AnswerDTO answer) {
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return answerService.updateAnswer(answer, userId);
    }

    @DeleteMapping("/answer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswerById(id);
    }
}

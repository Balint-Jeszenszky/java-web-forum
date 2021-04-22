package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.model.Answer;
import hu.bme.aut.javaweb.forum.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {
    private AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public List<Answer> getAnswersByQuestionId(@PathVariable int id) {
        return answerService.getAnswersByQuestionId(id);
    }

    @GetMapping("/answer/{id}")
    public Answer getAnswer(@PathVariable int id) {
        return answerService.getAnswerById(id);
    }

    @PostMapping("/answer")
    public Answer createAnswer(@RequestBody Answer answer) {
        return answerService.createAnswer(answer);
    }

    @PutMapping("/answer")
    public Answer updateAnswer(@RequestBody Answer answer) {
        return answerService.updateAnswer(answer);
    }

    @DeleteMapping("/answer/{id}")
    public void deleteAnswer(@PathVariable int id) {
        answerService.deleteAnswerById(id);
    }
}

package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.model.User;
import hu.bme.aut.javaweb.forum.model.dto.JwtResponse;
import hu.bme.aut.javaweb.forum.model.dto.LoginRequest;
import hu.bme.aut.javaweb.forum.model.dto.SignupRequest;
import hu.bme.aut.javaweb.forum.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}

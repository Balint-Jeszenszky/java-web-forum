package hu.bme.aut.javaweb.forum.controller;

import hu.bme.aut.javaweb.forum.model.User;
import hu.bme.aut.javaweb.forum.model.dto.UserDTO;
import hu.bme.aut.javaweb.forum.security.services.UserDetailsImpl;
import hu.bme.aut.javaweb.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public User getUser(Authentication authentication, @PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        Long userId = userDetails.getId();
        Boolean admin = ((List)userDetails.getAuthorities()).stream().anyMatch(
                e -> ((SimpleGrantedAuthority)e).getAuthority().equals("ROLE_ADMIN"));

        return userService.getUserById(id, userId, admin);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public User updateUser(Authentication authentication, @RequestBody UserDTO user) {
        Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Authentication authentication, @PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        Long userId = userDetails.getId();
        Boolean admin = ((List)userDetails.getAuthorities()).stream().anyMatch(
                e -> ((SimpleGrantedAuthority)e).getAuthority().equals("ROLE_ADMIN"));

        userService.deleteUserById(id, userId, admin);
    }
}

package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.UserDataSource;
import hu.bme.aut.javaweb.forum.model.User;
import hu.bme.aut.javaweb.forum.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserDataSource userDataSource;

    @Autowired
    PasswordEncoder encoder;

    public UserService(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public List<User> getAllUsers() {
        return userDataSource.findAll().stream().map(e -> {e.setPassword(""); return e;}).collect(Collectors.toList());
    }

    public User getUserById(Long id, Long userId, Boolean admin) {
        if (id != userId && !admin) {
            throw new IllegalArgumentException("Wrong userId");
        }

        Optional<User> user = userDataSource.findById(id);

        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        User storedUser = user.get();

        storedUser.setPassword("");

        return storedUser;
    }

    public User updateUser(UserDTO user, Long userId) {

        if (user.getId() != userId) {
            throw new IllegalArgumentException("Wrong userId");
        }

        Optional<User> userResult = userDataSource.findById(userId);

        if (userResult.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        Pattern emailPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher emailMatcher = emailPattern.matcher(user.getEmail());

        if (!emailMatcher.matches()) {
            throw new IllegalArgumentException("Error: Email is invalid!");
        }

        User storedUser = userResult.get();

        if (!user.getEmail().equals(storedUser.getEmail()) && userDataSource.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Error: Email is already in use!");
        }

        storedUser.setEmail(user.getEmail());

        if (user.getOldPassword().length() > 0) {
            System.out.println("asd");
            if (!encoder.matches(user.getOldPassword(), storedUser.getPassword())) {
                throw new IllegalArgumentException("Error: Wrong password");
            }

            if (!user.getNewPassword().equals(user.getConfirmPassword())) {
                throw new IllegalArgumentException("Error: New passwords not match");
            }

            if (user.getNewPassword().length() < 8) {
                throw new IllegalArgumentException("Error: Password should be at least 8 character!");
            }

            storedUser.setPassword(encoder.encode(user.getNewPassword()));
        }

        return userDataSource.save(storedUser);
    }

    public void deleteUserById(Long id, Long userId, Boolean admin) {
        if (id != userId && !admin) {
            throw new IllegalArgumentException("Wrong userId");
        }

        if (id == userId && admin) {
            throw new IllegalArgumentException("Error: Can not delete admin");
        }

        userDataSource.deleteById(id);
    }
}

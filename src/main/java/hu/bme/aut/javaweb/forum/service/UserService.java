package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.UserDataSource;
import hu.bme.aut.javaweb.forum.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private UserDataSource userDataSource;

    public UserService(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public List<User> getAllUsers() {
        return userDataSource.findAll();
    }

    public User getUserById(Long id, Long userId, Boolean admin) {
        if (id != userId && !admin) {
            throw new IllegalArgumentException("wrong userId");
        }

        Optional<User> user = userDataSource.findById(id);

        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        return user.get();
    }

    public User updateUser(User user) {
        return userDataSource.save(user);
    }

    public void deleteUserById(Long id, Long userId, Boolean admin) {
        if (id != userId && !admin) {
            throw new IllegalArgumentException("wrong userId");
        }

        if (id == userId && admin) {
            throw new IllegalArgumentException("can not delete admin");
        }

        userDataSource.deleteById(id);
    }
}

package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.UserDataSource;
import hu.bme.aut.javaweb.forum.model.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private UserDataSource userDataSource;

    public UserService(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public User getUserById(Long id) {
        Optional<User> user = userDataSource.findById(id);

        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        return user.get();
    }

    public User createUser(User user) {
        return userDataSource.save(user);
    }

    public User updateUser(User user) {
        return userDataSource.save(user);
    }

    public void deleteUserById(Long id) {
        userDataSource.deleteById(id);
    }
}

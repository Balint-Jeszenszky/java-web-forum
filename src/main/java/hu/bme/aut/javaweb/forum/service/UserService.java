package hu.bme.aut.javaweb.forum.service;

import hu.bme.aut.javaweb.forum.datasource.UserDataSource;
import hu.bme.aut.javaweb.forum.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDataSource userDataSource;

    public UserService(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public User getUserById(int id) {
        return userDataSource.getUserById(id);
    }

    public User createUser(User user) {
        return userDataSource.createUser(user);
    }

    public User updateUser(User user) {
        return userDataSource.updateUser(user);
    }

    public void deleteUserById(int id) {
        userDataSource.deleteUserById(id);
    }
}

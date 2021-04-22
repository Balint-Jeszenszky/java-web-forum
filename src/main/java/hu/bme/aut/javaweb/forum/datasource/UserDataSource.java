package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.User;

public interface UserDataSource {
    User getUserById(int id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUserById(int id);
}

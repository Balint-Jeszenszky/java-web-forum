package hu.bme.aut.javaweb.forum.datasource.mock;

import hu.bme.aut.javaweb.forum.datasource.UserDataSource;
import hu.bme.aut.javaweb.forum.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MockUserDataSource implements UserDataSource {
    private List<User> users = new ArrayList(Arrays.asList(new User(1, "Ben", "mail@example.com", "passwword")));
    private int id = 2;

    @Override
    public User getUserById(int id) {
        User user = null;

        for (User u : users) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        };

        if (user == null) throw new NoSuchElementException("User not found");

        return user;
    }

    @Override
    public User createUser(User user) {
        user.setId(id++);

        for (User u : users) {
            if (u.getEmail() == user.getEmail()) {
                throw new IllegalArgumentException("Email already registered");
            }
        };

        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        for (User u : users) {
            if (u.getId() == user.getId()) {
                /// TODO checks
                users.remove(u);
                users.add(user);
                return user;
            }
        };

        throw new NoSuchElementException("User not found");
    }

    @Override
    public void deleteUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                users.remove(u);
                return;
            }
        }
        throw new NoSuchElementException("User not found");
    }
}

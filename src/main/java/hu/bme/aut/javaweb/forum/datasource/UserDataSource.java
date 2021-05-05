package hu.bme.aut.javaweb.forum.datasource;

import hu.bme.aut.javaweb.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataSource extends JpaRepository<User, Long> {
//    User getUserById(int id);
//    User createUser(User user);
//    User updateUser(User user);
//    void deleteUserById(int id);
}

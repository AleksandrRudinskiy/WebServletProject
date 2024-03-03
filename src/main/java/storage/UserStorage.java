package storage;

import model.User;

import java.util.List;

public interface UserStorage {

    List<User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    User getUserById(String id);

    void deleteUserById(String id);

}

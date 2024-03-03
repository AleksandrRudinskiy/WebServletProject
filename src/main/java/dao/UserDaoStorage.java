package dao;

import connection.ConnectionManager;
import exception.NotFoundException;
import model.User;
import storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UserDaoStorage implements UserStorage {
    private final ConnectionManager manager = new ConnectionManager();
    private final Logger logger = Logger.getLogger(UserStorage.class.getName());

    public UserDaoStorage() {
    }

    @Override
    public List<User> getUsers() {
        String query = "select * from cat_user";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = manager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = makeUser(resultSet);
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        try {
            String query = "insert into cat_user values(?, ?, ?)";
            PreparedStatement statement = manager.getConnection().prepareStatement(query);
            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getLogin());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        Map<String, User> users = new HashMap<>();
        for (User itemUser : getUsers()) {
            users.put(itemUser.getId(), itemUser);
        }
        String query = "update cat_user set username = ?, nickname = ? where id = ?";
        String idUpdateUser = users.get(user.getId()).getId();
        if (idUpdateUser.equals(user.getId())) {
            try {
                PreparedStatement statement = manager.getConnection().prepareStatement(query);
                statement.setString(1, user.getName());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.info(e.getMessage());
            }
        } else {
            addUser(user);
        }
    }

    public User getUserById(String id) {
        String query = "select * from cat_user where id = ?";
        User user = null;
        try {
            PreparedStatement statement = manager.getConnection().prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = makeUser(resultSet);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("Пользователь с id = " + id + "не найден.");
        }
    }

    public void deleteUserById(String id) {
        getUserById(id);
        String query = "delete from cat_user where id = ?";
        try {
            PreparedStatement statement = manager.getConnection().prepareStatement(query);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    private User makeUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3)
        );
    }
}

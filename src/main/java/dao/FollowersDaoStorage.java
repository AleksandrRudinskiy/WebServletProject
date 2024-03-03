package dao;

import connection.ConnectionManager;
import model.UserFollowers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class FollowersDaoStorage {
    private final ConnectionManager manager = new ConnectionManager();
    private final Logger logger = Logger.getLogger(FollowersDaoStorage.class.getName());

    public UserFollowers getFollowers(String userId) {
        String query = "SELECT id_user, id_subscriber, id as post_id FROM cat_follow as cf LEFT JOIN cat_post as cp ON cf.id_user = cp.author_id WHERE id_user = ?";
        Set<String> followersId = new HashSet<>();
        Set<Integer> userPosts = new HashSet<>();

        try {
            PreparedStatement statement = manager.getConnection().prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                followersId.add(resultSet.getString("id_subscriber"));
                userPosts.add(resultSet.getInt("post_id"));
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return new UserFollowers(userId,
                new ArrayList<>(followersId),
                new ArrayList<>(userPosts));
    }
}

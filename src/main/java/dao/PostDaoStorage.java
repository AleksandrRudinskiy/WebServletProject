package dao;

import connection.ConnectionManager;
import model.Post;
import model.User;
import storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PostDaoStorage {
    private final ConnectionManager manager = new ConnectionManager();


    public List<Post> getAllPosts() throws SQLException {
        String query = "select id, author_id, description, photo_url, date(creation_date) from cat_post";
        PreparedStatement statement = manager.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            Post post = new Post(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5)
            );
            posts.add(post);
        }
        return posts;
    }

    public Post getPostById(Integer id) throws SQLException {
        String query = "select * from cat_post where id = ?";
        PreparedStatement statement = manager.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        Post post = null;
        while (resultSet.next()) {
            post = new Post(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDate(5)
            );
        }
        return post;
    }

}

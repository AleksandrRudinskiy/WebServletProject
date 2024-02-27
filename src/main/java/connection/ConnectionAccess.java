package connection;

import servlet.UserServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionAccess {
    private String user = "kitty";
    private String password = "purrrrrr";
    private String url = "jdbc:postgresql://localhost/cats";
    private final Logger logger = Logger.getLogger(ConnectionAccess.class.getName());

    public Connection makeConnection() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            logger.info("Class not found " + e.getMessage());
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            logger.info(exception.getMessage());
            return null;
        }
    }
}

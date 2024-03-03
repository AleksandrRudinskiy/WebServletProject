package connection;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;


public class ConnectionManager {
    private final Connection connection = new ConnectionAccess().makeConnection();

    public Connection getConnection() {
        return connection;
    }

    public ConnectionManager() {
    }

    public DatabaseMetaData getMetaData() {
        try {
            return connection.getMetaData();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}


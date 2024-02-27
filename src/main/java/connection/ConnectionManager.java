package connection;


import java.sql.*;


public class ConnectionManager {
    private final Connection connection= new ConnectionAccess().makeConnection();;

    public Connection getConnection() {
        return connection;
    }

    public ConnectionManager() {
    }

    private DatabaseMetaData getMetaData() {
        try {

            return connection.getMetaData();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}


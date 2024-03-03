package connection;


import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

       String url = "jdbc:postgresql://127.0.0.1:5432/postgres:13";
String user="postgres";
String password = "postgres";
               DriverManager.getConnection(url, user, password);



    }
}

package DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAL {
    static String url ="jdbc:sqlite:Database.db"; ;
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

package cl.TomP22.pnb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    
    private static String url;
    private static String username;
    private static String password;
    
    static {
        try {
            url = ConfigLoader.getDbUrl();
            username = ConfigLoader.getDbUsername();
            password = ConfigLoader.getDbPassword();
            String driver = ConfigLoader.getDbDriver();
            
            Class.forName(driver);
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
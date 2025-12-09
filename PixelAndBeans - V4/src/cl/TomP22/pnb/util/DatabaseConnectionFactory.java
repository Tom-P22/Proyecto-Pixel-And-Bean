package cl.TomP22.pnb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionFactory {
    
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    
    static {
        try {

            Properties props = new Properties();
            props.load(new FileInputStream("application.properties"));
            
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            
            Class.forName(driver);
            
            System.out.println("✅ Configuración de base de datos cargada correctamente");
            System.out.println("   URL: " + url);
            System.out.println("   Usuario: " + username);
            
        } catch (IOException e) {
            System.err.println("❌ Error al cargar application.properties");
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar la configuración de BD", e);
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC no encontrado: " + driver);
            e.printStackTrace();
            throw new RuntimeException("Driver JDBC no disponible", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            boolean valid = conn != null && !conn.isClosed();
            if (valid) {
                System.out.println("✅ Conexión a base de datos exitosa");
                System.out.println("   Catálogo: " + conn.getCatalog());
            }
            return valid;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a base de datos");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    

    public static String getUrl() {
        return url;
    }
    
    public static String getUsername() {
        return username;
    }
}
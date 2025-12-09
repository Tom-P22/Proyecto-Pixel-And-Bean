package cl.TomP22.pnb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    
    private static final String CONFIG_FILE = "application.properties";
    private static Properties properties;
    
    static {
        cargarConfiguracion();
    }

    private static void cargarConfiguracion() {
        properties = new Properties();
        
        try {
            try (InputStream input = new FileInputStream(CONFIG_FILE)) {
                properties.load(input);
                System.out.println("Configuración cargada desde archivo externo");
            } catch (IOException e) {
                try (InputStream input = ConfigLoader.class.getClassLoader()
                        .getResourceAsStream(CONFIG_FILE)) {
                    if (input != null) {
                        properties.load(input);
                        System.out.println("Configuración cargada desde recursos internos");
                    } else {
                        System.err.println("No se encontró archivo de configuración");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar configuración: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getDbUrl() {
        return getProperty("db.url", "jdbc:mysql://localhost:3306/pixelandbean");
    }
    
    public static String getDbUsername() {
        return getProperty("db.username", "root");
    }
    
    public static String getDbPassword() {
        return getProperty("db.password", "");
    }
    
    public static String getDbDriver() {
        return getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }

    public static String getAppName() {
        return getProperty("app.name", "Pixel And Bean");
    }
    
    public static String getAppVersion() {
        return getProperty("app.version", "3.0.5");
    }
    
    public static String getAppAuthor() {
        return getProperty("app.author", "Tomas Peña");
    }
}
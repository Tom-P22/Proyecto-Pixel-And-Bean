package cl.TomP22.pnb;

import cl.TomP22.pnb.util.DatabaseConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase para probar la conexión a la base de datos.
 * Ejecutar como aplicación Java independiente.
 */
public class TestConnection {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   PRUEBA DE CONEXIÓN A BASE DE DATOS    ");
        System.out.println("===========================================\n");
        
        // Test 1: Probar conexión básica
        System.out.println("Test 1: Conexión básica");
        boolean connected = DatabaseConnectionFactory.testConnection();
        
        if (!connected) {
            System.err.println("\n❌ No se pudo conectar a la base de datos.");
            System.err.println("Verifica que:");
            System.err.println("  1. XAMPP MySQL esté iniciado");
            System.err.println("  2. Base de datos 'pixelandbean' exista");
            System.err.println("  3. application.properties esté en la raíz del proyecto");
            System.err.println("  4. MySQL Connector/J esté agregado al proyecto");
            return;
        }
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 2: Consultar usuarios
        System.out.println("Test 2: Consultar usuarios");
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, username, rol FROM usuario")) {
            
            System.out.println("\nUsuarios en la base de datos:");
            System.out.println("ID | Username       | Rol");
            System.out.println("---|----------------|----------");
            
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String rol = rs.getString("rol");
                System.out.printf("%-3d| %-15s| %s%n", id, username, rol);
            }
            
            System.out.println("\n✅ Total de usuarios: " + count);
            
        } catch (Exception e) {
            System.err.println("❌ Error al consultar usuarios:");
            e.printStackTrace();
        }
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 3: Consultar productos
        System.out.println("Test 3: Consultar productos");
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT categoria, COUNT(*) as total FROM producto GROUP BY categoria")) {
            
            System.out.println("\nProductos por categoría:");
            System.out.println("Categoría       | Total");
            System.out.println("----------------|------");
            
            while (rs.next()) {
                String categoria = rs.getString("categoria");
                int total = rs.getInt("total");
                System.out.printf("%-16s| %d%n", categoria, total);
            }
            
            System.out.println("\n✅ Consulta exitosa");
            
        } catch (Exception e) {
            System.err.println("❌ Error al consultar productos:");
            e.printStackTrace();
        }
        
        System.out.println("\n===========================================");
        System.out.println("   PRUEBA COMPLETADA                      ");
        System.out.println("===========================================");
    }
}
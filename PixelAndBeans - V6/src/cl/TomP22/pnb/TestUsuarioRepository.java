
package cl.TomP22.pnb;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.repository.IUsuarioRepository;
import cl.TomP22.pnb.repository.impl.UsuarioRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class TestUsuarioRepository {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   TEST: UsuarioRepository (JDBC)         ");
        System.out.println("===========================================\n");
        
        IUsuarioRepository repo = new UsuarioRepositoryImpl();
        
        // Test 1: Autenticar admin
        System.out.println("Test 1: Autenticar admin");
        Optional<Usuario> admin = repo.autenticar("admin", "1234");
        
        if (admin.isPresent()) {
            Usuario u = admin.get();
            System.out.println("✅ Login exitoso:");
            System.out.println("   ID: " + u.getId());
            System.out.println("   Username: " + u.getUsername());
            System.out.println("   Nombre: " + u.getNombreCompleto());
            System.out.println("   Rol: " + u.getRol());
        } else {
            System.out.println("❌ Login falló");
        }
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 2: Listar todos los usuarios
        System.out.println("Test 2: Listar todos los usuarios");
        List<Usuario> todos = repo.listarTodos();
        System.out.println("Total de usuarios: " + todos.size());
        
        todos.forEach(u -> {
            String estado = u.isActivo() ? "✅" : "❌";
            System.out.printf("%s %d - %s (%s) - %s%n", 
                estado, u.getId(), u.getUsername(), u.getRol(), 
                u.isActivo() ? "Activo" : "Inactivo");
        });
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 3: Buscar por username
        System.out.println("Test 3: Buscar por username");
        Optional<Usuario> operador = repo.buscarPorUsername("operador");
        
        if (operador.isPresent()) {
            System.out.println("✅ Usuario encontrado:");
            System.out.println("   " + operador.get().getNombreCompleto());
        } else {
            System.out.println("❌ Usuario no encontrado");
        }
        
        System.out.println("\n===========================================");
        System.out.println("   TEST COMPLETADO                        ");
        System.out.println("===========================================");
    }
}
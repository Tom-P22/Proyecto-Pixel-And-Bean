
package cl.TomP22.pnb.repository;


import cl.TomP22.pnb.model.Usuario;
import java.util.List;


public interface IUsuarioRepository {
    
    Usuario buscarPorId(int id);

    Usuario buscarPorUsername(String username);

    List<Usuario> listarTodos();

    List<Usuario> listarPorRol(String rol);

    int guardar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminar(int id);

    boolean existeUsername(String username);

    int contarActivosPorRol(String rol);
}
package cl.TomP22.pnb.repository;

import cl.TomP22.pnb.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {

    List<Usuario> listarTodos();
    List<Usuario> listarActivos();
    
    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorUsername(String username);
    Optional<Usuario> autenticar(String username, String password);
    
    Usuario guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(int id);
    void desactivar(int id);
    void activar(int id);
}
package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Usuario;
import java.util.List;

public interface UsuarioService {
 
    List<Usuario> listarTodos();
    List<Usuario> buscar(String texto);
    Usuario buscarPorId(int id);
    
    Usuario autenticar(String username, String password);

    void crear(String username, String password, String nombreCompleto, String rol);
    void actualizar(int id, String username, String password, String nombreCompleto, String rol, boolean activo);
    
    void eliminar(int id);
    void cambiarEstado(int id, boolean activo);
}
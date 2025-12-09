package cl.TomP22.pnb.controller;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;


public class UsuarioController {
    
    private final UsuarioService service;
    
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    
    public void crear(String username, String password, 
                      String nombreCompleto, String rol) {

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setRol(rol);
        usuario.setActivo(true); 
        
        service.guardar(usuario);
    }
    
    public void actualizar(int id, String username, String password, 
                           String nombreCompleto, String rol, boolean activo) {
 
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setRol(rol);
        usuario.setActivo(activo);
        
        service.actualizar(usuario);
    }
    
    public void eliminar(int id) {
        service.eliminar(id);
    }
    
    public List<Usuario> listarTodos() {
        return service.listarTodos();
    }
    
    public List<Usuario> listarActivos() {

        return service.listarTodos().stream()
                .filter(Usuario::isActivo)
                .collect(Collectors.toList());
    }
    
    public List<Usuario> buscar(String texto) {
        return service.buscarPorUsername(texto);
    }
}
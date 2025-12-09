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

        service.crear(username, password, nombreCompleto, rol);
    }
    
    public void actualizar(int id, String username, String password, 
                           String nombreCompleto, String rol, boolean activo) {
        service.actualizar(id, username, password, nombreCompleto, rol, activo);
    }
    
    public void eliminar(int id) {
        service.eliminar(id);
    }
    
    public void cambiarEstado(int id, boolean activo) {
        service.cambiarEstado(id, activo);
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
        return service.buscar(texto);
    }
}
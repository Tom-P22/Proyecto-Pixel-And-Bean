package cl.TomP22.pnb.controller;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.service.UsuarioService;

public class LoginController {
    
    private final UsuarioService usuarioService;
    
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    public Usuario autenticar(String username, String password) {
        return usuarioService.autenticar(username, password);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioServiceStub implements UsuarioService {
    private List<Usuario> usuarios;
    private int nextId;
    
    public UsuarioServiceStub() {
        this.usuarios = new ArrayList<>();
        this.nextId = 1;
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {
        usuarios.add(new Usuario(nextId++, "admin", "admin123", 
            "Administrador del Sistema", "ADMIN", true));
        usuarios.add(new Usuario(nextId++, "operador", "op123", 
            "Juan Pérez", "OPERADOR", true));
        usuarios.add(new Usuario(nextId++, "cajero", "caj123", 
            "María González", "OPERADOR", true));
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
    
    @Override
    public Usuario buscarPorId(int id) {
        return usuarios.stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<Usuario> buscarPorUsername(String username) {
        String usernameLower = username.toLowerCase();
        return usuarios.stream()
            .filter(u -> u.getUsername().toLowerCase().contains(usernameLower))
            .collect(Collectors.toList());
    }
    
    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setId(nextId++);
        usuarios.add(usuario);
        System.out.println("[STUB] Usuario guardado: " + usuario);
        return usuario;
    }
    
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                System.out.println("[STUB] Usuario actualizado: " + usuario);
                return;
            }
        }
    }
    
    @Override
    public void eliminar(int id) {
        usuarios.removeIf(u -> u.getId() == id);
        System.out.println("[STUB] Usuario eliminado: " + id);
    }
    
    @Override
    public void cambiarEstado(int id, boolean activo) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setActivo(activo);
            System.out.println("[STUB] Estado cambiado: " + id + " -> " + activo);
        }
    }
    
    @Override
    public Usuario autenticar(String username, String password) {
        return usuarios.stream()
            .filter(u -> u.getUsername().equals(username) && 
                        u.getPassword().equals(password) &&
                        u.isActivo())
            .findFirst()
            .orElse(null);
    }
}
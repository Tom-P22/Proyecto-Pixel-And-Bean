/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb.repository.mock;

import cl.TomP22.pnb.model.Usuario;
import cl.TomP22.pnb.repository.IUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UsuarioRepositoryMock implements IUsuarioRepository {
    
    private List<Usuario> usuarios;
    private int nextId;
    
    public UsuarioRepositoryMock() {
        usuarios = new ArrayList<>();
        nextId = 1;
        cargarDatosIniciales();
    }
    
    //ESTO ES PARA MODIFICAR DATOS DE LOGIN
    private void cargarDatosIniciales() {
        usuarios.add(new Usuario(nextId++, "admin", "1111", 
                                 "Administrador del Sistema", "ADMIN", true));
        usuarios.add(new Usuario(nextId++, "op", "1212", 
                                 "Elvis Steck", "OPERADOR", true));
        usuarios.add(new Usuario(nextId++, "op2", "3456", 
                                 "Armando Paredes", "OPERADOR", true));
        usuarios.add(new Usuario(nextId++, "cajero", "1234", 
                                 "Alan Brito", "CAJERO", true));
    }
    
    @Override
    public Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
    
    @Override
    public List<Usuario> listarPorRol(String rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .collect(Collectors.toList());
    }
    
    @Override
    public int guardar(Usuario usuario) {
        usuario.setId(nextId++);
        usuarios.add(usuario);
        return usuario.getId();
    }
    
    @Override
    public void actualizar(Usuario usuario) {
        Usuario existente = buscarPorId(usuario.getId());
        if (existente != null) {
            int index = usuarios.indexOf(existente);
            usuarios.set(index, usuario);
        }
    }
    
    @Override
    public void eliminar(int id) {
        usuarios.removeIf(u -> u.getId() == id);
    }
    
    @Override
    public boolean existeUsername(String username) {
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }
    
    @Override
    public int contarActivosPorRol(String rol) {
        return (int) usuarios.stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .filter(Usuario::isActivo)
                .count();
    }
}
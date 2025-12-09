/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Usuario;
import java.util.List;

/**
 * Servicio para gestión de usuarios.
 * Define las operaciones CRUD y búsquedas disponibles.
 */
public interface UsuarioService {
    
    /**
     * Lista todos los usuarios del sistema.
     * @return Lista de usuarios
     */
    List<Usuario> listarTodos();
    
    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     */
    Usuario buscarPorId(int id);
    
    /**
     * Busca usuarios por username (parcial).
     * @param username Username a buscar (puede ser parcial)
     * @return Lista de usuarios que coinciden
     */
    List<Usuario> buscarPorUsername(String username);
    
    /**
     * Guarda un nuevo usuario.
     * @param usuario Usuario a guardar (sin ID)
     * @return Usuario guardado con ID asignado
     */
    Usuario guardar(Usuario usuario);
    
    /**
     * Actualiza un usuario existente.
     * @param usuario Usuario con cambios
     */
    void actualizar(Usuario usuario);
    
    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     */
    void eliminar(int id);
    
    /**
     * Cambia el estado activo/inactivo de un usuario.
     * @param id ID del usuario
     * @param activo Nuevo estado
     */
    void cambiarEstado(int id, boolean activo);
    
    /**
     * Valida las credenciales de un usuario.
     * @param username Username
     * @param password Password
     * @return Usuario si las credenciales son válidas, null en caso contrario
     */
    Usuario autenticar(String username, String password);
}
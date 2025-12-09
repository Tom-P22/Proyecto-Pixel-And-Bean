/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> listarTodos();

    Usuario buscarPorId(int id);

    List<Usuario> buscarPorUsername(String username);

    Usuario guardar(Usuario usuario);

    void actualizar(Usuario usuario);

    void eliminar(int id);

    void cambiarEstado(int id, boolean activo);

    Usuario autenticar(String username, String password);
}
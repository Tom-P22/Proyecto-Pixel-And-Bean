/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Producto;
import java.util.List;


public interface ProductoService {
    

    List<Producto> listarTodos();
    

    List<Producto> listarActivos();

    Producto buscarPorId(int id);

    List<Producto> buscarPorNombre(String nombre);
 
    List<Producto> filtrarPorCategoria(String categoria);
 
    Producto guardar(Producto producto);

    void actualizar(Producto producto);

    void eliminar(int id);

    void cambiarEstado(int id, boolean activo);
}
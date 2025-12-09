/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Producto;
import java.util.List;

/**
 * Servicio para gestión de productos.
 */
public interface ProductoService {
    
    /**
     * Lista todos los productos.
     * @return Lista de productos
     */
    List<Producto> listarTodos();
    
    /**
     * Lista solo productos activos.
     * @return Lista de productos activos
     */
    List<Producto> listarActivos();
    
    /**
     * Busca un producto por su ID.
     * @param id ID del producto
     * @return Producto encontrado o null
     */
    Producto buscarPorId(int id);
    
    /**
     * Busca productos por nombre (parcial).
     * @param nombre Nombre a buscar
     * @return Lista de productos que coinciden
     */
    List<Producto> buscarPorNombre(String nombre);
    
    /**
     * Filtra productos por categoría.
     * @param categoria Categoría (BEBIDA, SNACK, TIEMPO)
     * @return Lista de productos de la categoría
     */
    List<Producto> filtrarPorCategoria(String categoria);
    
    /**
     * Guarda un nuevo producto.
     * @param producto Producto a guardar
     * @return Producto guardado con ID asignado
     */
    Producto guardar(Producto producto);
    
    /**
     * Actualiza un producto existente.
     * @param producto Producto con cambios
     */
    void actualizar(Producto producto);
    
    /**
     * Elimina un producto por su ID.
     * @param id ID del producto
     */
    void eliminar(int id);
    
    /**
     * Cambia el estado activo/inactivo de un producto.
     * @param id ID del producto
     * @param activo Nuevo estado
     */
    void cambiarEstado(int id, boolean activo);
}
package cl.TomP22.pnb.controller;

import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.service.ProductoService;
import java.util.List;

public class ProductoController {
    
    private final ProductoService service;
    
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    public void crear(String nombre, String categoria, String tipo, double precio, String descripcion) {
        service.crear(nombre, categoria, tipo, precio, descripcion);
    }

    public void actualizar(int id, String nombre, String categoria, 
                           String tipo, double precio, boolean activo, String descripcion) {
        service.actualizar(id, nombre, categoria, tipo, precio, activo, descripcion);
    }
    
    public void eliminar(int id) {
        service.eliminar(id);
    }
    
    public void cambiarEstado(int id) {
        service.cambiarEstado(id);
    }
    
    public List<Producto> listarTodos() {
        return service.listarTodos();
    }
    
    public List<Producto> listarActivos() {
        return service.listarActivos();
    }
    
    public List<Producto> listarPorCategoria(String categoria) {
        return service.filtrarPorCategoria(categoria);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return service.buscarPorNombre(nombre);
    }
}
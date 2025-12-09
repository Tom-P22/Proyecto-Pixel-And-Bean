package cl.TomP22.pnb.controller;

import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.service.ProductoService;
import java.util.List;

public class ProductoController {
    
    private final ProductoService service;
    
    public ProductoController(ProductoService service) {
        this.service = service;
    }
    
    public void crear(String nombre, String categoria, String tipo, double precio) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setTipo(tipo);
        producto.setPrecio(precio);
        producto.setActivo(true);
        
        service.guardar(producto);
    }
    
    public void actualizar(int id, String nombre, String categoria,
                           String tipo, double precio, boolean activo) {
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setTipo(tipo);
        producto.setPrecio(precio);
        producto.setActivo(activo);
        
        service.actualizar(producto);
    }
    
    public void eliminar(int id) {
        service.eliminar(id);
    }
    
    public void cambiarEstado(int id) {
        Producto p = service.buscarPorId(id);
        if (p != null) {
            service.cambiarEstado(id, !p.isActivo());
        }
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
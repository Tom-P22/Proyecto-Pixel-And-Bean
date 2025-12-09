package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Producto;
import java.util.List;

public interface ProductoService {
    
    List<Producto> listarTodos();
    List<Producto> listarActivos();
    
    List<Producto> buscarPorNombre(String nombre);
    Producto buscarPorId(int id);
    List<Producto> filtrarPorCategoria(String categoria);

    void crear(String nombre, String categoria, String tipo, double precio, String descripcion);
    
    void actualizar(int id, String nombre, String categoria, String tipo, double precio, boolean activo, String descripcion);
    
    void eliminar(int id);
    void cambiarEstado(int id);
}
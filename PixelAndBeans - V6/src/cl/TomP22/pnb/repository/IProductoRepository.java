package cl.TomP22.pnb.repository;

import cl.TomP22.pnb.model.Producto;
import java.util.List;

public interface IProductoRepository {
    
    List<Producto> listarTodos();
    List<Producto> listarActivos();
   
    Producto buscarPorId(int id); 
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarPorCategoria(String categoria);
    
    Producto guardar(Producto producto); 
    void actualizar(Producto producto);
    void eliminar(int id);
    
    void desactivar(int id);
    void activar(int id);
}
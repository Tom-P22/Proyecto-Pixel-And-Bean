
package cl.TomP22.pnb.repository;

import cl.TomP22.pnb.model.Producto;
import java.util.List;


public interface IProductoRepository {
    

    Producto buscarPorId(int id);
    

    List<Producto> listarTodos();
    

    List<Producto> listarActivos();
    
    List<Producto> listarPorCategoria(String categoria);
    

    List<Producto> buscarPorNombre(String nombre);
    

    int guardar(Producto producto);

    void actualizar(Producto producto);
    

    void eliminar(int id);
    

    void cambiarEstado(int id, boolean activo);
}
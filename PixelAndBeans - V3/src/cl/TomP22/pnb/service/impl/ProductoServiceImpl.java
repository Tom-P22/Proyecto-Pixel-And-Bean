package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.repository.IProductoRepository;
import cl.TomP22.pnb.service.ProductoService;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    
    private final IProductoRepository repository;
    
    public ProductoServiceImpl(IProductoRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Producto guardar(Producto producto) {
        validarDatosProducto(producto.getNombre(), producto.getCategoria(), producto.getTipo(), producto.getPrecio());
        
        producto.setNombre(producto.getNombre().trim());
        producto.setActivo(true);
        
        repository.guardar(producto);
        return producto;
    }
    
    @Override
    public void actualizar(Producto producto) {
        validarDatosProducto(producto.getNombre(), producto.getCategoria(), producto.getTipo(), producto.getPrecio());
        
        Producto productoExistente = repository.buscarPorId(producto.getId());
        if (productoExistente == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        producto.setNombre(producto.getNombre().trim());
        
        repository.actualizar(producto);
    }
    
    @Override
    public void eliminar(int id) {
        Producto producto = repository.buscarPorId(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        repository.eliminar(id);
    }
    
    @Override
    public void cambiarEstado(int id, boolean activo) {
        Producto producto = repository.buscarPorId(id);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
 
        producto.setActivo(activo);
        repository.actualizar(producto);
    }
    
    @Override
    public List<Producto> listarTodos() {
        return repository.listarTodos();
    }
    
    @Override
    public List<Producto> listarActivos() {
        return repository.listarActivos();
    }
    
    @Override
    public List<Producto> filtrarPorCategoria(String categoria) {
        return repository.listarPorCategoria(categoria);
    }
    
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return repository.buscarPorNombre(nombre);
    }
    
    @Override
    public Producto buscarPorId(int id) {
        return repository.buscarPorId(id);
    }
    
    private void validarDatosProducto(String nombre, String categoria, String tipo, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        if (!categoria.equals("BEBIDA") && !categoria.equals("SNACK") && 
            !categoria.equals("TIEMPO") && !categoria.equals("MERCH")) {
            throw new IllegalArgumentException("Categoría inválida");
        }
        
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }
}
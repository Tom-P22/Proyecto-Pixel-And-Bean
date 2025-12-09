package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.repository.IProductoRepository;
import cl.TomP22.pnb.repository.impl.ProductoRepositoryImpl;
import cl.TomP22.pnb.service.ProductoService;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    
    private final IProductoRepository repository;
    
    public ProductoServiceImpl() {
        this.repository = new ProductoRepositoryImpl();
    }
    
    @Override
    public List<Producto> listarTodos() { return repository.listarTodos(); }

    @Override
    public List<Producto> listarActivos() { return repository.listarActivos(); }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return listarActivos();
        }
        return repository.buscarPorNombre(nombre);
    }

    @Override
    public Producto buscarPorId(int id) { return repository.buscarPorId(id); }
    
    @Override
    public List<Producto> filtrarPorCategoria(String categoria) {
        return repository.buscarPorCategoria(categoria);
    }

    @Override
    public void crear(String nombre, String categoria, String tipo, double precio, String descripcion) {
        validarDatosProducto(nombre, categoria, tipo, precio);
        
        Producto nuevo = new Producto();
        nuevo.setNombre(nombre.trim());
        nuevo.setCategoria(categoria);
        nuevo.setTipo(tipo);
        nuevo.setPrecio(precio);
        nuevo.setDescripcion(descripcion);
        nuevo.setActivo(true);
        
        repository.guardar(nuevo);
    }

    @Override
    public void actualizar(int id, String nombre, String categoria, String tipo, double precio, boolean activo, String descripcion) {
        validarDatosProducto(nombre, categoria, tipo, precio);
        
        Producto existente = repository.buscarPorId(id);
        if (existente == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        existente.setNombre(nombre.trim());
        existente.setCategoria(categoria);
        existente.setTipo(tipo);
        existente.setPrecio(precio);
        existente.setDescripcion(descripcion);
        existente.setActivo(activo);
        
        repository.actualizar(existente);
    }

    @Override
    public void eliminar(int id) {
        if (repository.buscarPorId(id) == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        repository.eliminar(id);
    }

    @Override
    public void cambiarEstado(int id) {
        Producto p = repository.buscarPorId(id);
        if (p != null) {
            if (p.isActivo()) {
                repository.desactivar(id);
            } else {
                repository.activar(id);
            }
        }
    }
    
    private void validarDatosProducto(String nombre, String categoria, String tipo, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }
}
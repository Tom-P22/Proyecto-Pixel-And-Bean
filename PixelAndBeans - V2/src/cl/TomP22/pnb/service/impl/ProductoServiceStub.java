/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb.service.impl;


import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoServiceStub implements ProductoService {
    private List<Producto> productos;
    private int nextId;
    
    public ProductoServiceStub() {
        this.productos = new ArrayList<>();
        this.nextId = 1;
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {
        // Bebidas
        productos.add(new Producto(nextId++, "Espresso", "BEBIDA", "CAFE", 2500.0, true));
        productos.add(new Producto(nextId++, "Cappuccino", "BEBIDA", "CAFE", 3000.0, true));
        productos.add(new Producto(nextId++, "Latte", "BEBIDA", "CAFE", 3200.0, true));
        
        // Snacks
        productos.add(new Producto(nextId++, "Brownie", "SNACK", "POSTRE", 2000.0, true));
        productos.add(new Producto(nextId++, "Galletas", "SNACK", "POSTRE", 1500.0, true));
        productos.add(new Producto(nextId++, "Sandwich", "SNACK", "SALADO", 3500.0, true));
        
        // Tiempo Arcade
        productos.add(new Producto(nextId++, "15 minutos", "TIEMPO", "ARCADE", 1500.0, true));
        productos.add(new Producto(nextId++, "30 minutos", "TIEMPO", "ARCADE", 2500.0, true));
        productos.add(new Producto(nextId++, "60 minutos", "TIEMPO", "ARCADE", 4000.0, true));
        productos.add(new Producto(nextId++, "Pase Diario", "TIEMPO", "ARCADE", 10000.0, true));
        
        //Misc
        productos.add(new Producto(nextId++, "Cigarro", "TIEMPO", "ARCADE", 100.0, true));
    }
    
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
    
    @Override
    public List<Producto> listarActivos() {
        return productos.stream()
            .filter(Producto::isActivo)
            .collect(Collectors.toList());
    }
    
    @Override
    public Producto buscarPorId(int id) {
        return productos.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        String nombreLower = nombre.toLowerCase();
        return productos.stream()
            .filter(p -> p.getNombre().toLowerCase().contains(nombreLower))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Producto> filtrarPorCategoria(String categoria) {
        return productos.stream()
            .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
            .collect(Collectors.toList());
    }
    
    @Override
    public Producto guardar(Producto producto) {
        producto.setId(nextId++);
        productos.add(producto);
        System.out.println("[STUB] Producto guardado: " + producto);
        return producto;
    }
    
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == producto.getId()) {
                productos.set(i, producto);
                System.out.println("[STUB] Producto actualizado: " + producto);
                return;
            }
        }
    }
    
    @Override
    public void eliminar(int id) {
        productos.removeIf(p -> p.getId() == id);
        System.out.println("[STUB] Producto eliminado: " + id);
    }
    
    @Override
    public void cambiarEstado(int id, boolean activo) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            producto.setActivo(activo);
            System.out.println("[STUB] Estado cambiado: " + id + " -> " + activo);
        }
    }
}
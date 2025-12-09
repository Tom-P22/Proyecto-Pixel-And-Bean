/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb;

import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.repository.IProductoRepository;
import cl.TomP22.pnb.repository.impl.ProductoRepositoryImpl;

import java.util.List;

public class TestProductoRepository {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   TEST: ProductoRepository (JDBC)        ");
        System.out.println("===========================================\n");
        
        IProductoRepository repo = new ProductoRepositoryImpl();
        
        // Test 1: Listar productos activos
        System.out.println("Test 1: Listar productos activos");
        List<Producto> activos = repo.listarActivos();
        System.out.println("Total: " + activos.size() + " productos");
        
        // Agrupar por categoría
        String categoriaActual = "";
        for (Producto p : activos) {
            if (!p.getCategoria().equals(categoriaActual)) {
                categoriaActual = p.getCategoria();
                System.out.println("\n" + categoriaActual + ":");
            }
            System.out.printf("  - %s ($%.2f)%n", p.getNombre(), p.getPrecio());
        }
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 2: Buscar por nombre
        System.out.println("Test 2: Buscar productos con 'café'");
        List<Producto> cafes = repo.buscarPorNombre("café");
        System.out.println("Encontrados: " + cafes.size());
        cafes.forEach(p -> System.out.println("  - " + p.getNombre()));
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 3: Buscar por categoría
        System.out.println("Test 3: Productos de TIEMPO_ARCADE");
        List<Producto> arcade = repo.buscarPorCategoria("TIEMPO_ARCADE");
        arcade.forEach(p -> {
            System.out.printf("  - %s: $%.2f%n", p.getNombre(), p.getPrecio());
        });
        
        System.out.println("\n===========================================");
        System.out.println("   TEST COMPLETADO                        ");
        System.out.println("===========================================");
    }
}
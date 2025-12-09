/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb;


import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.repository.IVentaRepository;
import cl.TomP22.pnb.repository.impl.VentaRepositoryImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestVentaRepository {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   TEST: VentaRepository (JDBC)           ");
        System.out.println("===========================================\n");
        
        IVentaRepository repo = new VentaRepositoryImpl();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        // Test 1: Ventas del día
        System.out.println("Test 1: Ventas del día (activas)");
        List<Venta> hoy = repo.listarDelDia();
        System.out.println("Total: " + hoy.size() + " ventas");
        
        hoy.forEach(v -> {
            System.out.printf("  #%d - %s - $%.2f - %s%n", 
                v.getId(),
                v.getFechaHora().format(fmt),
                v.getTotal(),
                v.getUsuarioNombre());
        });
        
        System.out.println("\n-------------------------------------------\n");
        
        // Test 2: Total del día
        System.out.println("Test 2: Total acumulado del día");
        double total = repo.calcularTotalDelDia();
        System.out.printf("Total: $%.2f%n", total);
        
        System.out.println("\n===========================================");
        System.out.println("   TEST COMPLETADO                        ");
        System.out.println("===========================================");
    }
}
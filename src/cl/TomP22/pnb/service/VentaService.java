/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cl.TomP22.pnb.service;


import cl.TomP22.pnb.model.Venta;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para gestión de ventas.
 */
public interface VentaService {
    
    /**
     * Lista todas las ventas.
     * @return Lista de ventas
     */
    List<Venta> listarTodas();
    
    /**
     * Lista ventas de una fecha específica.
     * @param fecha Fecha a consultar
     * @return Lista de ventas del día
     */
    List<Venta> listarPorFecha(LocalDate fecha);
    
    /**
     * Lista ventas del día actual.
     * @return Lista de ventas de hoy
     */
    List<Venta> listarVentasDelDia();
    
    /**
     * Busca una venta por su ID.
     * @param id ID de la venta
     * @return Venta encontrada o null
     */
    Venta buscarPorId(int id);
    
    /**
     * Registra una nueva venta.
     * @param venta Venta a registrar
     * @return Venta registrada con ID asignado
     */
    Venta registrar(Venta venta);
    
    /**
     * Anula una venta (cambia estado a ANULADA).
     * @param id ID de la venta
     */
    void anular(int id);
    
    /**
     * Calcula el total de ventas activas de una fecha.
     * @param fecha Fecha a consultar
     * @return Total en pesos
     */
    double calcularTotalPorFecha(LocalDate fecha);
}
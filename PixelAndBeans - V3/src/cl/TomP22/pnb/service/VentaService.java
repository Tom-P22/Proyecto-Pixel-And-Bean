/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cl.TomP22.pnb.service;


import cl.TomP22.pnb.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {

    List<Venta> listarTodas();
    
    List<Venta> listarPorFecha(LocalDate fecha);

    List<Venta> listarVentasDelDia();

    Venta buscarPorId(int id);

    Venta registrar(Venta venta);

    void anular(int id);

    double calcularTotalPorFecha(LocalDate fecha);
}
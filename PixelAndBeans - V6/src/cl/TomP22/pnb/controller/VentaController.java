package cl.TomP22.pnb.controller;

import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.service.VentaService;
import java.util.List;
import cl.TomP22.pnb.model.DetalleVenta;
import cl.TomP22.pnb.model.ProductoVendido;
import java.time.LocalDate;


public class VentaController {
    
    private final VentaService service;
    
    public VentaController(VentaService service) {
        this.service = service;
    }
    
    public void registrarVenta(int usuarioId, String usuarioNombre, double total, List<DetalleVenta> detalles) {
        service.registrarVenta(usuarioId, usuarioNombre, total, detalles);
    }   
    
    public void anular(int id) {
        service.anular(id);
    }
    
    public List<Venta> listarTodas() {
        return service.listarTodas();
    }
    
    public List<Venta> listarDelDia() {
        return service.listarVentasDelDia();
    }
    
    public double calcularTotalDelDia() {
        return service.calcularTotalDelDia();
    }
    
    public List<ProductoVendido> obtenerTopProductos(LocalDate inicio, LocalDate fin) {
        return service.obtenerTopProductos(inicio, fin);
    }
}
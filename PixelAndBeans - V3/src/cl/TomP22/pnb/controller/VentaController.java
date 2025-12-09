package cl.TomP22.pnb.controller;

import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.service.VentaService;
import java.time.LocalDate;
import java.util.List;

public class VentaController {
    
    private final VentaService service;
    
    public VentaController(VentaService service) {
        this.service = service;
    }
    
    public int registrarVenta(int usuarioId, String usuarioNombre, double total) {
        Venta venta = new Venta();
        venta.setUsuarioId(usuarioId);
        venta.setUsuarioNombre(usuarioNombre);
        venta.setTotal(total);
        
        Venta ventaGuardada = service.registrar(venta);
        return ventaGuardada.getId();
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
        return service.calcularTotalPorFecha(LocalDate.now());
    }
}
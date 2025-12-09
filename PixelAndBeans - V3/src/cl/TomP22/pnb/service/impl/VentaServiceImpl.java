package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.repository.IVentaRepository;
import cl.TomP22.pnb.service.VentaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VentaServiceImpl implements VentaService {
    
    private final IVentaRepository repository;
    
    public VentaServiceImpl(IVentaRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Venta registrar(Venta venta) {
        if (venta.getUsuarioId() <= 0) {
            // Nota: Si usas ID 0 para pruebas, comenta esta validación
            // throw new IllegalArgumentException("Usuario inválido");
        }
        if (venta.getTotal() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }
        
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado("ACTIVA");
        
        int idGenerado = repository.guardar(venta);
        venta.setId(idGenerado);
        
        return venta;
    }
    
    @Override
    public void anular(int id) {
        Venta venta = repository.buscarPorId(id);
        if (venta == null) {
            throw new RuntimeException("Venta no encontrada");
        }
        
        if ("ANULADA".equals(venta.getEstado())) {
            throw new RuntimeException("La venta ya está anulada");
        }
        
        repository.anular(id);
    }
    
    @Override
    public List<Venta> listarTodas() {
        return repository.listarTodas();
    }
    
    @Override
    public List<Venta> listarVentasDelDia() {
        return repository.listarDelDia();
    }
    
    @Override
    public List<Venta> listarPorFecha(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.plusDays(1).atStartOfDay();
        return repository.listarPorRangoFechas(inicio, fin);
    }
    
    @Override
    public Venta buscarPorId(int id) {
        return repository.buscarPorId(id);
    }
    
    @Override
    public double calcularTotalPorFecha(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.plusDays(1).atStartOfDay();
        return repository.calcularTotalPorRango(inicio, fin);
    }
}
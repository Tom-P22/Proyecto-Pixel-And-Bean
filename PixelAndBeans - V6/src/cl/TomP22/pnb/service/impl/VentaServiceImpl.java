package cl.TomP22.pnb.service.impl;

import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.repository.IVentaRepository;
import cl.TomP22.pnb.repository.impl.VentaRepositoryImpl;
import cl.TomP22.pnb.service.VentaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VentaServiceImpl implements VentaService {
    
    private final IVentaRepository repository;
    
    public VentaServiceImpl() {
        this.repository = new VentaRepositoryImpl();
    }
    
    @Override
    public void registrarVenta(int idUsuario, String nombreUsuario, double total, List<cl.TomP22.pnb.model.DetalleVenta> detalles) {
        if (total <= 0) throw new IllegalArgumentException("Total debe ser > 0");
        
        Venta venta = new Venta();
        venta.setUsuarioId(idUsuario);
        venta.setUsuarioNombre(nombreUsuario);
        venta.setTotal(total);
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado("ACTIVA");
        
        venta.setDetalles(detalles);
        
        repository.guardar(venta);
    }
    
    @Override
    public void anular(int id) {
        Venta venta = repository.buscarPorId(id).orElse(null);
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
        return repository.listarPorFecha(fecha);
    }
    
    @Override
    public Venta buscarPorId(int id) {
        return repository.buscarPorId(id).orElse(null);
    }
    
    @Override
    public double calcularTotalDelDia() {
        return repository.calcularTotalDelDia();
    }
    
    @Override
    public List<cl.TomP22.pnb.model.ProductoVendido> obtenerTopProductos(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            inicio = LocalDate.now();
            fin = LocalDate.now();
        }
        return repository.obtenerTopProductos(inicio, fin);
    }
}
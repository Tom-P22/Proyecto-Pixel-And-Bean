package cl.TomP22.pnb.repository;

import cl.TomP22.pnb.model.Venta;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IVentaRepository {

    Venta guardar(Venta venta);
    
    Optional<Venta> buscarPorId(int id);
    
    List<Venta> listarTodas();
    
    List<Venta> listarPorFecha(LocalDate fecha);
    
    List<Venta> listarDelDia();

    double calcularTotalDelDia();
    
    void anular(int id);
}
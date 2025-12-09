
package cl.TomP22.pnb.repository;

import cl.TomP22.pnb.model.Venta;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IVentaRepository {

    Venta buscarPorId(int id);

    List<Venta> listarTodas();

    List<Venta> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);

    List<Venta> listarDelDia();

    List<Venta> listarPorUsuario(int usuarioId);

    int guardar(Venta venta);
 
    void anular(int id);
  
    double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta);

    double calcularTotalDelDia();
}
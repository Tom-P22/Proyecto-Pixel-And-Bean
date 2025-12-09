package cl.TomP22.pnb.service;

import cl.TomP22.pnb.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {


    void registrarVenta(int idUsuario, String nombreUsuario, double total);

    List<Venta> listarTodas();
    List<Venta> listarPorFecha(LocalDate fecha);
    List<Venta> listarVentasDelDia();
    Venta buscarPorId(int id);

    // Operaciones
    void anular(int id);
    double calcularTotalDelDia();
}
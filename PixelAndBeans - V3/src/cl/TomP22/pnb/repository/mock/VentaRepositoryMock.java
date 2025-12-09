/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb.repository.mock;


import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.repository.IVentaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class VentaRepositoryMock implements IVentaRepository {
    
    private List<Venta> ventas;
    private int nextId;
    
    public VentaRepositoryMock() {
        ventas = new ArrayList<>();
        nextId = 1;
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {

        LocalDateTime ahora = LocalDateTime.now();
        
        ventas.add(new Venta(nextId++, ahora.minusHours(5), 1, "admin", 7500, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusHours(4), 2, "operador1", 5000, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusHours(3), 1, "admin", 12000, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusHours(2), 3, "operador2", 3500, "ACTIVA"));
        ventas.add(new Venta(nextId++, ahora.minusHours(1), 2, "operador1", 8500, "ANULADA"));
        ventas.add(new Venta(nextId++, ahora.minusMinutes(30), 1, "admin", 6000, "ACTIVA"));
    }
    
    @Override
    public Venta buscarPorId(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Venta> listarTodas() {
        return new ArrayList<>(ventas);
    }
    
    @Override
    public List<Venta> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return ventas.stream()
                .filter(v -> !v.getFechaHora().isBefore(desde) && 
                            !v.getFechaHora().isAfter(hasta))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Venta> listarDelDia() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1).minusSeconds(1);
        return listarPorRangoFechas(inicioDia, finDia);
    }
    
    @Override
    public List<Venta> listarPorUsuario(int usuarioId) {
        return ventas.stream()
                .filter(v -> v.getUsuarioId() == usuarioId)
                .collect(Collectors.toList());
    }
    
    @Override
    public int guardar(Venta venta) {
        venta.setId(nextId++);
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado("ACTIVA");
        ventas.add(venta);
        return venta.getId();
    }
    
    @Override
    public void anular(int id) {
        Venta venta = buscarPorId(id);
        if (venta != null) {
            venta.setEstado("ANULADA");
        }
    }
    
    @Override
    public double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta) {
        return listarPorRangoFechas(desde, hasta).stream()
                .filter(v -> "ACTIVA".equals(v.getEstado()))
                .mapToDouble(Venta::getTotal)
                .sum();
    }
    
    @Override
    public double calcularTotalDelDia() {
        return listarDelDia().stream()
                .filter(v -> "ACTIVA".equals(v.getEstado()))
                .mapToDouble(Venta::getTotal)
                .sum();
    }
}
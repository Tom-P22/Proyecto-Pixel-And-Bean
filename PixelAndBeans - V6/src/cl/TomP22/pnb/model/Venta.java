package cl.TomP22.pnb.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private LocalDateTime fechaHora;
    private int usuarioId;
    private String usuarioNombre;
    private double total;
    private String estado; 
    private String observaciones;

    private List<DetalleVenta> detalles = new ArrayList<>();
    
    private String resumenProductos;

    public Venta() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
    
    public String getResumenProductos() { return resumenProductos; }
    public void setResumenProductos(String resumenProductos) { this.resumenProductos = resumenProductos; }

    @Override
    public String toString() {
        return "Venta #" + id + " - $" + String.format("%,.0f", total);
    }
}
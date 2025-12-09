/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb.model;

import java.time.LocalDateTime;

public class Venta {
    private int id;
    private LocalDateTime fechaHora;
    private int usuarioId;
    private String usuarioNombre;
    private double total;
    private String estado; 
    
  
    public Venta() {
    }
    
    public Venta(int id, LocalDateTime fechaHora, int usuarioId, String usuarioNombre,
                 double total, String estado) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.total = total;
        this.estado = estado;
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
    
    @Override
    public String toString() {
        return "Venta #" + id + " - $" + String.format("%,.0f", total);
    }
}

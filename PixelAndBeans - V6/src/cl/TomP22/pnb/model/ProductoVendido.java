package cl.TomP22.pnb.model;

public class ProductoVendido {
    private String nombre;
    private int cantidad;
    private double totalGenerado;

    public ProductoVendido() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getTotalGenerado() { return totalGenerado; }
    public void setTotalGenerado(double totalGenerado) { this.totalGenerado = totalGenerado; }
}
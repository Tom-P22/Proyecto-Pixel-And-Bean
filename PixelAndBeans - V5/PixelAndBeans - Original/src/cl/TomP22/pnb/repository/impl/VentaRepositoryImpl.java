package cl.TomP22.pnb.repository.impl;

import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.repository.IVentaRepository;
import cl.TomP22.pnb.util.DatabaseConnectionFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VentaRepositoryImpl implements IVentaRepository {
    
    private static final String SQL_SELECT_BASE = 
        "SELECT v.id, v.fecha_hora, v.usuario_id, v.usuario_nombre, v.total, v.estado, v.observaciones, " +
        "GROUP_CONCAT(CONCAT(vd.cantidad, 'x ', p.nombre) SEPARATOR ', ') as resumen_productos " +
        "FROM venta v " +
        "LEFT JOIN venta_detalle vd ON v.id = vd.venta_id " +
        "LEFT JOIN producto p ON vd.producto_id = p.id ";
    
    private static final String GROUP_BY = " GROUP BY v.id, v.fecha_hora, v.usuario_id, v.usuario_nombre, v.total, v.estado, v.observaciones ";
    
    private static final String SQL_SELECT_ALL = 
        SQL_SELECT_BASE + GROUP_BY + " ORDER BY v.fecha_hora DESC";

    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT_BASE + " WHERE v.id = ? " + GROUP_BY;
    
    private static final String SQL_SELECT_BY_FECHA = 
        SQL_SELECT_BASE + " WHERE DATE(v.fecha_hora) = ? " + GROUP_BY + " ORDER BY v.fecha_hora DESC";
    
    private static final String SQL_SELECT_HOY = 
        SQL_SELECT_BASE + " WHERE DATE(v.fecha_hora) = CURDATE() " + GROUP_BY + " ORDER BY v.fecha_hora DESC";
    
    private static final String SQL_TOTAL_DIA = 
        "SELECT COALESCE(SUM(total), 0) as total FROM venta WHERE DATE(fecha_hora) = CURDATE() AND estado = 'ACTIVA'";
    
    private static final String SQL_INSERT = 
        "INSERT INTO venta (usuario_id, usuario_nombre, total, fecha_hora, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ANULAR = 
        "UPDATE venta SET estado = 'ANULADA' WHERE id = ?";
    
    private static final String SQL_INSERT_DETALLE = 
        "INSERT INTO venta_detalle (venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(rs.getInt("id"));
        
        Timestamp ts = rs.getTimestamp("fecha_hora");
        if (ts != null) venta.setFechaHora(ts.toLocalDateTime());
        
        venta.setUsuarioId(rs.getInt("usuario_id"));
        venta.setUsuarioNombre(rs.getString("usuario_nombre"));
        venta.setTotal(rs.getDouble("total"));
        venta.setEstado(rs.getString("estado"));
        venta.setObservaciones(rs.getString("observaciones"));
        
        // Mapeamos el nuevo campo de resumen
        venta.setResumenProductos(rs.getString("resumen_productos"));
        
        return venta;
    }
    
    @Override
    public Venta guardar(Venta venta) {
       Connection conn = null;
        try {
            conn = DatabaseConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            
           try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, venta.getUsuarioId());
                ps.setString(2, venta.getUsuarioNombre());
                ps.setDouble(3, venta.getTotal());
                ps.setTimestamp(4, Timestamp.valueOf(venta.getFechaHora()));
                ps.setString(5, venta.getEstado());
                ps.setString(6, venta.getObservaciones());
                
                ps.executeUpdate();
            
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setId(rs.getInt(1));
                    }
                }
            }

            try (PreparedStatement psDetalle = conn.prepareStatement(SQL_INSERT_DETALLE)) {
                for (cl.TomP22.pnb.model.DetalleVenta d : venta.getDetalles()) {
                    psDetalle.setInt(1, venta.getId());
                    psDetalle.setInt(2, d.getProductoId());
                    psDetalle.setInt(3, d.getCantidad());
                    psDetalle.setDouble(4, d.getPrecioUnitario());
                    psDetalle.setDouble(5, d.getSubtotal());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            conn.commit();
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Error al guardar la venta completa", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        return venta;
    }
    
    @Override
    public List<Venta> listarTodas() {
        List<Venta> ventas = new ArrayList<>();
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ventas.add(mapearVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    @Override
    public List<Venta> listarPorFecha(LocalDate fecha) {
        List<Venta> ventas = new ArrayList<>();
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_FECHA)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearVenta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }
    
    @Override
    public List<Venta> listarDelDia() {
        List<Venta> ventas = new ArrayList<>();
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_HOY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ventas.add(mapearVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }
    
    @Override
    public Optional<Venta> buscarPorId(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearVenta(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    @Override
    public double calcularTotalDelDia() {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_TOTAL_DIA);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void anular(int id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ANULAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al anular venta", e);
        }
    }
}
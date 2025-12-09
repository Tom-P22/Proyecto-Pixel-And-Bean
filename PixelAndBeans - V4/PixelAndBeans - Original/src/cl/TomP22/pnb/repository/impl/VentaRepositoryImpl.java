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
        "SELECT v.id, v.fecha_hora, v.usuario_id, v.usuario_nombre, v.total, v.estado, v.observaciones " +
        "FROM venta v";
    
    private static final String SQL_SELECT_ALL = 
        SQL_SELECT_BASE + " ORDER BY v.fecha_hora DESC";

    private static final String SQL_SELECT_BY_ID = 
        SQL_SELECT_BASE + " WHERE v.id = ?";
    
    private static final String SQL_SELECT_BY_FECHA = 
        SQL_SELECT_BASE + " WHERE DATE(v.fecha_hora) = ? ORDER BY v.fecha_hora DESC";
    
    private static final String SQL_SELECT_HOY = 
        SQL_SELECT_BASE + " WHERE DATE(v.fecha_hora) = CURDATE() ORDER BY v.fecha_hora DESC";
    
    private static final String SQL_TOTAL_DIA = 
        "SELECT COALESCE(SUM(total), 0) as total FROM venta WHERE DATE(fecha_hora) = CURDATE() AND estado = 'ACTIVA'";
    
    private static final String SQL_INSERT = 
        "INSERT INTO venta (usuario_id, usuario_nombre, total, fecha_hora, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ANULAR = 
        "UPDATE venta SET estado = 'ANULADA' WHERE id = ?";

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(rs.getInt("id"));
        venta.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        venta.setUsuarioId(rs.getInt("usuario_id"));
        venta.setUsuarioNombre(rs.getString("usuario_nombre"));
        venta.setTotal(rs.getDouble("total"));
        venta.setEstado(rs.getString("estado"));
        venta.setObservaciones(rs.getString("observaciones"));
        return venta;
    }
    
    @Override
    public Venta guardar(Venta venta) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, venta.getUsuarioId());
            ps.setString(2, venta.getUsuarioNombre());
            ps.setDouble(3, venta.getTotal());
            ps.setTimestamp(4, Timestamp.valueOf(venta.getFechaHora()));
            ps.setString(5, venta.getEstado());
            ps.setString(6, venta.getObservaciones());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar venta", e);
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
            e.printStackTrace();
            throw new RuntimeException("Error al anular venta", e);
        }
    }
}
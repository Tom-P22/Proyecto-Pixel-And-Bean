package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.service.VentaService;
import cl.TomP22.pnb.service.impl.VentaServiceStub;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesPanel extends javax.swing.JPanel {
    
    private VentaService ventaService;
    private ReporteTableModel tableModel;
    
    public ReportesPanel() {
        this.ventaService = new VentaServiceStub();
        initComponents();
        setupComponents();
    }

     private void setupComponents() {
        tableModel = new ReporteTableModel();
        tblReporte.setModel(tableModel);
        
        tblReporte.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblReporte.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblReporte.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblReporte.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        cmbFiltro.removeAllItems();
        cmbFiltro.addItem("Hoy");
        cmbFiltro.addItem("Ayer");
        cmbFiltro.addItem("Última semana");
        cmbFiltro.addItem("Último mes");
        cmbFiltro.addItem("Todo el historial");
        
        generarReporte();
    }
    
    private void generarReporte() {
        String filtro = (String) cmbFiltro.getSelectedItem();
        if (filtro == null) return;

        List<Venta> ventasFiltradas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        switch (filtro) {
            case "Hoy":
                ventasFiltradas = ventaService.listarVentasDelDia();
                break;
            case "Ayer":
                LocalDate ayer = hoy.minusDays(1);
                ventasFiltradas = ventaService.listarPorFecha(ayer);
                break;
            case "Última semana":
                ventasFiltradas = ventaService.listarTodas().stream()
                    .filter(v -> v.getFechaHora().toLocalDate().isAfter(hoy.minusWeeks(1)))
                    .collect(Collectors.toList());
                break;
            case "Último mes":
                ventasFiltradas = ventaService.listarTodas().stream()
                    .filter(v -> v.getFechaHora().toLocalDate().isAfter(hoy.minusMonths(1)))
                    .collect(Collectors.toList());
                break;
            default:
                ventasFiltradas = ventaService.listarTodas();
                break;
        }
        
        tableModel.setVentas(ventasFiltradas);
        
        double totalRecaudado = ventasFiltradas.stream()
            .filter(v -> "ACTIVA".equals(v.getEstado()))
            .mapToDouble(Venta::getTotal)
            .sum();
        
        int totalVentas = ventasFiltradas.size();
        
        lblTotalVentas.setText("Ventas: " + totalVentas);
        lblTotal.setText(String.format("Total Recaudado: $%,.0f", totalRecaudado));
    }
    
    private class ReporteTableModel extends AbstractTableModel {
        private List<Venta> ventas = new ArrayList<>();
        private String[] columnNames = {"ID", "Fecha/Hora", "Usuario", "Total", "Estado"};
        
        public void setVentas(List<Venta> ventas) {
            this.ventas = ventas;
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount() { return ventas.size(); }
        
        @Override
        public int getColumnCount() { return columnNames.length; }
        
        @Override
        public String getColumnName(int column) { return columnNames[column]; }
        
        @Override
        public Object getValueAt(int row, int column) {
            Venta v = ventas.get(row);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            switch (column) {
                case 0: return v.getId();
                case 1: return v.getFechaHora().format(formatter);
                case 2: return v.getUsuarioNombre();
                case 3: return String.format("$%,.0f", v.getTotal());
                case 4: return v.getEstado();
                default: return null;
            }
        }
        
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    }
        
    @SuppressWarnings("unchecked")                  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblReporte = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        cmbFiltro = new javax.swing.JComboBox<>();
        btnGenerar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTotalVentas = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        tblReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblReporte);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnGenerar.setText("Generar Reporte");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(btnGenerar)
                .addGap(106, 106, 106)
                .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        lblTotalVentas.setText("Ventas: 0");
        lblTotalVentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotal.setText("Total Recaudado: 0");
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalVentas))
                .addContainerGap(380, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblTotalVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        generarReporte();
    }//GEN-LAST:event_btnGenerarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalVentas;
    private javax.swing.JTable tblReporte;
    // End of variables declaration//GEN-END:variables
}

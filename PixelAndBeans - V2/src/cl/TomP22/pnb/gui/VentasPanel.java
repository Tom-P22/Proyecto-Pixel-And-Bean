package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.model.Producto;
import cl.TomP22.pnb.model.Venta;
import cl.TomP22.pnb.service.ProductoService;
import cl.TomP22.pnb.service.VentaService;
import cl.TomP22.pnb.service.impl.ProductoServiceStub;
import cl.TomP22.pnb.service.impl.VentaServiceStub;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VentasPanel extends javax.swing.JPanel {

    private ProductoService productoService;
    private VentaService ventaService;
    private DefaultListModel<Producto> listModel;
    private List<ItemVenta> itemsVenta;
    private DetalleVentaTableModel detalleTableModel;
    private VentaTableModel ventasTableModel;

    public VentasPanel() {
        this.productoService = new ProductoServiceStub();
        this.ventaService = new VentaServiceStub();
        
        initComponents();
        setupComponents();
        setupManualEvents();
        cargarProductos();
        cargarVentasDelDia();
    }
    
    private void setupComponents() {
        // Configurar Lista
        listModel = new DefaultListModel<>();
        lstProductos.setModel(listModel);
        lstProductos.setCellRenderer(new ProductoListCellRenderer());
        
        spnCantidad.setModel(new SpinnerNumberModel(1, 1, 99, 1));
        itemsVenta = new ArrayList<>();
        detalleTableModel = new DetalleVentaTableModel();
        tblDetalle.setModel(detalleTableModel);
        
        ventasTableModel = new VentaTableModel();
        tblVentasDelDia.setModel(ventasTableModel);
        
        actualizarTotal();
    }
    
    private void setupManualEvents() {
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });
    }
    
    private void cargarProductos() {
        listModel.clear();
        List<Producto> productos = productoService.listarActivos();
        
        for (Producto p : productos) {
            listModel.addElement(p);
        }
    }

    private void buscarProductos() {
        String texto = txtBuscarProducto.getText().trim();
        listModel.clear();
        
        List<Producto> resultados;
        if (texto.isEmpty()) {
            resultados = productoService.listarActivos();
        } else {
            resultados = productoService.buscarPorNombre(texto);
        }
        
        for (Producto p : resultados) {
            listModel.addElement(p);
        }
    }
    private void cargarVentasDelDia() {

        List<Venta> ventas = ventaService.listarVentasDelDia();
       
        ventasTableModel.setVentas(ventas);

        double totalDia = ventas.stream()
            .filter(v -> "ACTIVA".equals(v.getEstado()))
            .mapToDouble(Venta::getTotal)
            .sum();
        
        lblTotalDia.setText(String.format("Total del día: $%,.0f", totalDia));
    }

    private void limpiarVenta() {
        itemsVenta.clear();
        detalleTableModel.fireTableDataChanged();
        actualizarTotal();
        lstProductos.clearSelection();
        spnCantidad.setValue(1);
    }
    
    private double calcularTotal() {
        return itemsVenta.stream()
            .mapToDouble(ItemVenta::getSubtotal)
            .sum();
    }
    
    private void actualizarTotal() {
        double total = calcularTotal();
        lblTotal.setText(String.format("TOTAL: $%,.0f", total));
    }

    private class ItemVenta {
        private Producto producto;
        private int cantidad;
        
        public ItemVenta(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }
        
        public Producto getProducto() { return producto; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public double getSubtotal() { return producto.getPrecio() * cantidad; }
    }
    
    private class DetalleVentaTableModel extends AbstractTableModel {
        private String[] columnNames = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        
        @Override
        public int getRowCount() { return itemsVenta.size(); }
        @Override
        public int getColumnCount() { return columnNames.length; }
        @Override
        public String getColumnName(int column) { return columnNames[column]; }
        
        @Override
        public Object getValueAt(int row, int column) {
            ItemVenta item = itemsVenta.get(row);
            switch (column) {
                case 0: return item.getProducto().getNombre();
                case 1: return item.getCantidad();
                case 2: return String.format("$%,.0f", item.getProducto().getPrecio());
                case 3: return String.format("$%,.0f", item.getSubtotal());
                default: return null;
            }
        }
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    }
    
    private class VentaTableModel extends AbstractTableModel {
        private List<Venta> ventas = new ArrayList<>();
        private String[] columnNames = {"#", "Fecha/Hora", "Usuario", "Total", "Estado"};
        
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
            switch (column) {
                case 0: return v.getId();
                case 1: return v.getFechaHora().format(formatter);
                case 2: return v.getUsuarioNombre();
                case 3: return String.format("$%,.0f", v.getTotal());
                case 4: return v.getEstado();
                default: return null;
            }
        }
    }
    
    private class ProductoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Producto) {
                Producto p = (Producto) value;
                setText(String.format("<html><b>%s</b><br><small>$%,.0f</small></html>", 
                    p.getNombre(), p.getPrecio()));
            }
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVentasDelDia = new javax.swing.JTable();
        lblTotalDia = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstProductos = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        spnCantidad = new javax.swing.JSpinner();
        btnAgregar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        btnQuitar = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        btnConfirmarVenta = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Registro de nueva venta");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(jLabel4)
                .addContainerGap(286, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ventas del dia"));

        tblVentasDelDia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Fecha/Hora", "Usuario", "Total", "Estado"
            }
        ));
        jScrollPane3.setViewportView(tblVentasDelDia);

        lblTotalDia.setText("Total del dia: $0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalDia, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalDia)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccion de producto"));

        jLabel1.setText("Buscar:");

        jButton1.setText("Buscar");

        jScrollPane1.setViewportView(lstProductos);

        jLabel2.setText("Cantidad:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addGap(26, 26, 26))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle de la venta"));

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Cantidad", "Precio Unit", "Subtotal"
            }
        ));
        jScrollPane2.setViewportView(tblDetalle);

        btnQuitar.setText("Quitar Seleccionado");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        lblTotal.setText("Total: $0");
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnConfirmarVenta.setText("Confirmar venta");
        btnConfirmarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarVentaActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnQuitar))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnConfirmarVenta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmarVenta))
                .addGap(40, 40, 40))
        );

        jSplitPane2.setRightComponent(jPanel6);

        jPanel1.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (!itemsVenta.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Cancelar la venta actual?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                limpiarVenta();
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarVentaActionPerformed
if (itemsVenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agrega al menos un producto", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = calcularTotal();

        int respuesta = JOptionPane.showConfirmDialog(this,
            String.format("¿Confirmar venta por $%,.0f?", total),
            "Confirmar Venta",
            JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            // --- INTEGRACIÓN SERVICIO ---
            // Creamos la venta (ID 0 y Fecha null porque el servicio lo asigna)
            // Usuario ID 1 / "admin" hardcodeado por ahora hasta tener Login real
            Venta nuevaVenta = new Venta(0, null, 1, "admin", total, "ACTIVA");
            
            // Guardar usando el servicio
            ventaService.registrar(nuevaVenta);
            
            JOptionPane.showMessageDialog(this, "Venta registrada exitosamente");

            limpiarVenta();
            cargarVentasDelDia(); // Refrescar la tabla de abajo
        }
    }//GEN-LAST:event_btnConfirmarVentaActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int selectedRow = tblDetalle.getSelectedRow();
        if (selectedRow >= 0) {
            itemsVenta.remove(selectedRow);
            detalleTableModel.fireTableDataChanged();
            actualizarTotal();
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        Producto seleccionado = lstProductos.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad = (Integer) spnCantidad.getValue();

        boolean encontrado = false;
        for (ItemVenta item : itemsVenta) {
            if (item.getProducto().getId() == seleccionado.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            itemsVenta.add(new ItemVenta(seleccionado, cantidad));
        }

        detalleTableModel.fireTableDataChanged();
        actualizarTotal();
        spnCantidad.setValue(1);
    }//GEN-LAST:event_btnAgregarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmarVenta;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalDia;
    private javax.swing.JList<Producto> lstProductos;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblVentasDelDia;
    private javax.swing.JTextField txtBuscarProducto;
    // End of variables declaration//GEN-END:variables
}
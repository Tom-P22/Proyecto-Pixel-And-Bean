package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.app.ApplicationContext;
import cl.TomP22.pnb.controller.ProductoController;
import cl.TomP22.pnb.model.Producto;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosPanel extends javax.swing.JPanel {

    private ProductoController controller;
    private ProductoTableModel tableModel;
    private Producto productoSeleccionado;

    public ProductosPanel() {
        this.controller = ApplicationContext.getInstance().getProductoController();
        
        initComponents();
        setupComponents(); 
        setupTable();      
        setupListeners();  
        cargarProductos(); 
        limpiarFormulario(); 
    }
    
    private void setupComponents() {
        String[] categoriasFiltro = {"TODAS", "BEBIDA", "SNACK", "TIEMPO", "MERCH"};
        cmbCategoria.setModel(new DefaultComboBoxModel<>(categoriasFiltro));
        
        String[] categoriasInput = {"SELECCIONAR", "BEBIDA", "SNACK", "TIEMPO", "MERCH"};
        cmbCategoriar.setModel(new DefaultComboBoxModel<>(categoriasInput));
        
        cmbTipo.setModel(new DefaultComboBoxModel<>(new String[]{"SELECCIONAR CATEGORIA PRIMERO"}));
        cmbTipo.setEnabled(false);
    }
        
    private void setupTable() {
        tableModel = new ProductoTableModel();
        tblProductos.setModel(tableModel);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tblProductos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblProductos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblProductos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblProductos.getColumnModel().getColumn(4).setPreferredWidth(60);
        tblProductos.getColumnModel().getColumn(5).setPreferredWidth(50);
    }

    private void setupListeners() {
        tblProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblProductos.getSelectedRow();
                if (selectedRow >= 0) {
                    productoSeleccionado = tableModel.getProductoAt(selectedRow);
                    cargarEnFormulario(productoSeleccionado);
                }
            }
        });
        
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tblProductos.getSelectedRow();
                    if (row >= 0) {
                        productoSeleccionado = tableModel.getProductoAt(row);
                        cargarEnFormulario(productoSeleccionado);
                        txtNombre.requestFocus();
                    }
                }
            }
        });

        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { }
        });
        
        cmbCategoria.addActionListener(e -> filtrar());
        

        cmbCategoriar.addActionListener(e -> actualizarTiposSegunCategoria());
    }
    
    private void actualizarTiposSegunCategoria() {
        String categoriaSeleccionada = (String) cmbCategoriar.getSelectedItem();
        
        if (categoriaSeleccionada == null || categoriaSeleccionada.equals("SELECCIONAR")) {
            cmbTipo.setModel(new DefaultComboBoxModel<>(new String[]{"SELECCIONAR CATEGORIA PRIMERO"}));
            cmbTipo.setEnabled(false);
            return;
        }
        
        String[] tiposDisponibles;
        
        switch (categoriaSeleccionada) {
            case "BEBIDA":
                tiposDisponibles = new String[]{"SELECCIONAR", "CAFE", "JUGO", "GASEOSA", "AGUA", "TE"};
                break;
            case "SNACK":
                tiposDisponibles = new String[]{"SELECCIONAR", "POSTRE", "SALADO", "FRUTA", "GALLETA"};
                break;
            case "TIEMPO":
                tiposDisponibles = new String[]{"SELECCIONAR", "ARCADE"};
                break;
            case "MERCH":
                tiposDisponibles = new String[]{"SELECCIONAR", "ROPA", "ACCESORIO", "FIGURA", "PELUCHE"};
                break;
            default:
                tiposDisponibles = new String[]{"SELECCIONAR"};
                break;
        }
        
        cmbTipo.setModel(new DefaultComboBoxModel<>(tiposDisponibles));
        cmbTipo.setEnabled(true);
    }

    private void filtrar() {
        String texto = txtBuscar.getText().trim();
        String categoriaFiltro = (String) cmbCategoria.getSelectedItem();
        
        List<Producto> resultados;
        
        if (texto.isEmpty()) {
            resultados = controller.listarTodos();
        } else {
            resultados = controller.buscarPorNombre(texto);
        }
        
        if (categoriaFiltro != null && !categoriaFiltro.equals("TODAS")) {
            List<Producto> filtradosPorCat = new ArrayList<>();
            for (Producto p : resultados) {
                if (p.getCategoria().equalsIgnoreCase(categoriaFiltro)) {
                    filtradosPorCat.add(p);
                }
            }
            resultados = filtradosPorCat;
        }
        
        tableModel.setProductos(resultados);
    }

    private void cargarProductos() {
        try {
            filtrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarEnFormulario(Producto p) {
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        chkActivo.setSelected(p.isActivo());

        cmbCategoriar.setSelectedItem(p.getCategoria());
        

        cmbTipo.setSelectedItem(p.getTipo());
        
        btnEliminar.setEnabled(true);
        btnCambiarEstado.setEnabled(true);
    }
    
    private void limpiarFormulario() {
        productoSeleccionado = null;
        txtNombre.setText("");
        txtPrecio.setText("");
        chkActivo.setSelected(true);
        

        cmbCategoriar.setSelectedIndex(0);
        
        btnEliminar.setEnabled(false);
        btnCambiarEstado.setEnabled(false);
        tblProductos.clearSelection();
        txtNombre.requestFocus();
    }                     

    private boolean validarFormulario() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cmbCategoriar.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoría", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (cmbTipo.getSelectedItem() == null || 
            cmbTipo.getSelectedItem().toString().equals("SELECCIONAR") ||
            cmbTipo.getSelectedItem().toString().equals("SELECCIONAR CATEGORIA PRIMERO")) {
            JOptionPane.showMessageDialog(this, "Selecciona un tipo válido", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }                                       

    private class ProductoTableModel extends AbstractTableModel {
        private List<Producto> lista = new ArrayList<>();
        private String[] columnas = {"ID", "Nombre", "Categoría", "Tipo", "Precio", "Activo"};
        
        public void setProductos(List<Producto> nuevaLista) {
            this.lista = nuevaLista;
            fireTableDataChanged();
        }
        
        public Producto getProductoAt(int rowIndex) {
            return lista.get(rowIndex);
        }
        
        @Override
        public int getRowCount() { return lista.size(); }
        
        @Override
        public int getColumnCount() { return columnas.length; }
        
        @Override
        public String getColumnName(int column) { return columnas[column]; }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Producto p = lista.get(rowIndex);
            switch(columnIndex) {
                case 0: return p.getId();
                case 1: return p.getNombre();
                case 2: return p.getCategoria();
                case 3: return p.getTipo();
                case 4: return "$" + p.getPrecio();
                case 5: return p.isActivo() ? "Sí" : "No";
                default: return null;
            }
        }
    }

    @SuppressWarnings("unchecked")                    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        cmbCategoria = new javax.swing.JComboBox<>();
        btnNuevo = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        cmbCategoriar = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        chkActivo = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCambiarEstado = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Buscar:");

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnNuevo)
                .addGap(77, 77, 77))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Categoria", "Tipo", "Activo"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jLabel2.setText("Nombre:");

        jLabel3.setText("Precio:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbCategoriar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategoriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriarActionPerformed(evt);
            }
        });

        chkActivo.setText("Activo");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCambiarEstado.setText("Cambiar Estado");
        btnCambiarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCambiarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecio))
                            .addComponent(chkActivo)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbCategoriar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategoriar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkActivo)
                .addGap(18, 18, 18)
                .addComponent(btnCambiarEstado)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel3);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (!validarFormulario()) {
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String categoria = (String) cmbCategoriar.getSelectedItem();
            String tipo = (String) cmbTipo.getSelectedItem();
            boolean activo = chkActivo.isSelected();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            
            if (productoSeleccionado == null) {
                controller.crear(nombre, categoria, tipo, precio);
                JOptionPane.showMessageDialog(this, "Producto creado exitosamente");
            } else {
                controller.actualizar(productoSeleccionado.getId(), nombre, categoria, tipo, precio, activo);
                JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente");
            }
            
            limpiarFormulario();
            cargarProductos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (productoSeleccionado == null) return;
        
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar '" + productoSeleccionado.getNombre() + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                controller.eliminar(productoSeleccionado.getId());
                JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente");
                cargarProductos();
                limpiarFormulario();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCambiarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarEstadoActionPerformed
        if (productoSeleccionado != null) {
            try {
                controller.cambiarEstado(productoSeleccionado.getId());
                cargarProductos();
                JOptionPane.showMessageDialog(this, "Estado cambiado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }       
    }//GEN-LAST:event_btnCambiarEstadoActionPerformed

    private void cmbCategoriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriarActionPerformed

    }//GEN-LAST:event_cmbCategoriarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbCategoriar;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}

package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.model.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductoDialog extends JDialog {
    

    private JTextField txtNombre;
    private JComboBox<String> cboCategoria;
    private JTextField txtTipo;
    private JTextField txtPrecio;
    private JCheckBox chkActivo;
    
    private JButton btnGuardar;
    private JButton btnCancelar;

    private Producto producto;
    private boolean confirmado = false;
 
    private static final String[] CATEGORIAS = {
        "BEBIDA", "SNACK", "TIEMPO", "MERCH"
    };
    
    public ProductoDialog(Frame parent) {
        this(parent, null);
    }
    
    public ProductoDialog(Frame parent, Producto producto) {
        super(parent, producto == null ? "Nuevo Producto" : "Editar Producto", true);
        this.producto = producto;
        
        initComponents();
        layoutComponents();
        setupListeners();
        
        if (producto != null) {
            cargarDatos();
        }
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void initComponents() {
        txtNombre = new JTextField(30);
        cboCategoria = new JComboBox<>(CATEGORIAS);
        txtTipo = new JTextField(20);
        txtPrecio = new JTextField(10);
        chkActivo = new JCheckBox("Activo");
        chkActivo.setSelected(true);
        
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void layoutComponents() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
    
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Nombre:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(new JLabel("Categoría:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(cboCategoria, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(txtTipo, gbc);
 
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(new JLabel("Precio:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel panelPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelPrecio.add(new JLabel("$"));
        panelPrecio.add(txtPrecio);
        panelCampos.add(panelPrecio, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        panelCampos.add(chkActivo, gbc);
  
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(lblNota.getFont().deriveFont(Font.ITALIC, 11f));
        panelCampos.add(lblNota, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        setLayout(new BorderLayout());
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> cancelar());
    }
    
    private void cargarDatos() {
        txtNombre.setText(producto.getNombre());
        cboCategoria.setSelectedItem(producto.getCategoria());
        txtTipo.setText(producto.getTipo());
        txtPrecio.setText(String.valueOf((int)producto.getPrecio()));
        chkActivo.setSelected(producto.isActivo());
    }
    
    private void guardar() {
        if (!validarCampos()) return;
        confirmado = true;
        dispose();
    }
    
    private void cancelar() {
        confirmado = false;
        dispose();
    }
    
    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        
        try {
            double precio = Double.parseDouble(precioStr);
            if (precio <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número mayor a 0");
            return false;
        }
        
        return true;
    }

    public boolean isConfirmado() { return confirmado; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getCategoria() { return (String) cboCategoria.getSelectedItem(); }
    public String getTipo() { return txtTipo.getText().trim(); }
    public double getPrecio() { return Double.parseDouble(txtPrecio.getText().trim()); }
    public boolean isActivo() { return chkActivo.isSelected(); }
}
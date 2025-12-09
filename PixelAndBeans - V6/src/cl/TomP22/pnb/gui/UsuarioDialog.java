
package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UsuarioDialog extends JDialog {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cboRol;
    private JCheckBox chkActivo;
    
    private JButton btnGuardar;
    private JButton btnCancelar;

    private Usuario usuario;
    private boolean confirmado = false;
    
 
    public UsuarioDialog(Frame parent) {
        this(parent, null);
    }

    public UsuarioDialog(Frame parent, Usuario usuario) {
        super(parent, usuario == null ? "Nuevo Usuario" : "Editar Usuario", true);
        this.usuario = usuario;
        
        initComponents();
        layoutComponents();
        setupListeners();
        
        if (usuario != null) {
            cargarDatos();
        }
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void initComponents() {
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtPasswordConfirm = new JPasswordField(20);
        txtNombreCompleto = new JTextField(30);

        String[] roles = {"OPERADOR", "ADMIN", "CAJERO"};
        cboRol = new JComboBox<>(roles);
        
        chkActivo = new JCheckBox("Activo");
        chkActivo.setSelected(true);
        
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        if (usuario != null) {
            txtUsername.setEditable(false);
            txtUsername.setBackground(Color.LIGHT_GRAY);
        }
    }
    
    private void layoutComponents() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
 
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Username:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        String labelPassword = usuario == null ? "Contraseña:*" : "Nueva Contraseña:";
        panelCampos.add(new JLabel(labelPassword), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(new JLabel("Confirmar:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(txtPasswordConfirm, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(txtNombreCompleto, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(new JLabel("Rol:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCampos.add(cboRol, gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        panelCampos.add(chkActivo, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        String notaTexto = (usuario == null) ? "* Campos obligatorios" : "* Dejar contraseña en blanco para mantener la actual";
        JLabel lblNota = new JLabel(notaTexto);
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
        
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    guardar();
                }
            }
        };
        
        txtUsername.addKeyListener(enterListener);
        txtPassword.addKeyListener(enterListener);
        txtPasswordConfirm.addKeyListener(enterListener);
        txtNombreCompleto.addKeyListener(enterListener);
  
        KeyAdapter escListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelar();
                }
            }
        };
        
        getRootPane().addKeyListener(escListener);
    }
    
    private void cargarDatos() {
        txtUsername.setText(usuario.getUsername());
        txtNombreCompleto.setText(usuario.getNombreCompleto());
        cboRol.setSelectedItem(usuario.getRol());
        chkActivo.setSelected(usuario.isActivo());

        txtPassword.setText("");
        txtPasswordConfirm.setText("");
    }
    
    private void guardar() {
 
        if (!validarCampos()) {
            return;
        }
        
        confirmado = true;
        dispose();
    }
    
    private void cancelar() {
        confirmado = false;
        dispose();
    }
    
    private boolean validarCampos() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String passwordConfirm = new String(txtPasswordConfirm.getPassword());
        

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El username es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
            return false;
        }
        

        if (usuario == null) {
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
                txtPassword.requestFocus();
                return false;
            }
        }
        

        if (!password.isEmpty()) {
            if (password.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                txtPassword.requestFocus();
                return false;
            }
            if (!password.equals(passwordConfirm)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                txtPasswordConfirm.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    

    
    public boolean isConfirmado() { return confirmado; }
    
    public String getUsername() { return txtUsername.getText().trim(); }
    
    public String getPassword() {
        String pwd = new String(txtPassword.getPassword());
        return pwd.isEmpty() ? null : pwd;
    }
    
    public String getNombreCompleto() { return txtNombreCompleto.getText().trim(); }
    
    public String getRol() { return (String) cboRol.getSelectedItem(); }
    
    public boolean isActivo() { return chkActivo.isSelected(); }
}
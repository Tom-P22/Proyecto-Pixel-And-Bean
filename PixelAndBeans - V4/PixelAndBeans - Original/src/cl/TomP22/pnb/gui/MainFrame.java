package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.gui.EventosPanel;
import cl.TomP22.pnb.gui.ProductosPanel;
import cl.TomP22.pnb.gui.ReportesPanel;
import cl.TomP22.pnb.gui.UsuariosPanel;
import cl.TomP22.pnb.gui.VentasPanel;
import cl.TomP22.pnb.model.Usuario;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends javax.swing.JFrame {
    
    private CardLayout cardLayout;
    private Usuario usuarioActual;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());


    public MainFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        
        initComponents();
        setTitle("Pixel & Bean – Sistema de Gestión");
        setSize(900, 600);
        
        setupNavigation();
        personalizarPorRol(); 
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    private void personalizarPorRol() {
        if (usuarioActual != null) {

            setTitle("Pixel & Bean - " + usuarioActual.getNombreCompleto() + 
                     " (" + usuarioActual.getRol() + ")");
            
            jLabel1.setText("Usuario: " + usuarioActual.getUsername() + " | Rol: " + usuarioActual.getRol());
            
            String rol = usuarioActual.getRol();
            
            if ("OPERADOR".equals(rol)) {
                jMenuItem3.setEnabled(false);
            }
            else if ("CAJERO".equals(rol)) {
                gestion.setEnabled(false); 
                eventos.setEnabled(false);
                operacion.setEnabled(true);
                reportes.setEnabled(true);
            }
        }
    }
        
    private void setupNavigation() {
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(createHomePanel(), "HOME");
        contentPanel.add(new UsuariosPanel(), "USUARIOS");
        contentPanel.add(new ProductosPanel(), "PRODUCTOS");
        contentPanel.add(new VentasPanel(this.usuarioActual), "VENTAS");
        contentPanel.add(new ReportesPanel(), "REPORTES");
        contentPanel.add(new EventosPanel(), "EVENTOS");
        
        mostrarVista("HOME");
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("<html><center>" +
            "<h1>☕🎮 Pixel & Bean</h1>" +
            "<p>Sistema de Gestión para Café-Arcade</p>" +
            "<p style='margin-top: 20px;'>Selecciona una opción del menú superior para comenzar</p>" +
            "</center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }
    
    public void mostrarVista(String nombreVista) {
        cardLayout.show(contentPanel, nombreVista);
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Usuario adminTest = new Usuario(1, "admin", "pass", "Administrador Test", "ADMIN", true);
            new MainFrame(adminTest).setVisible(true);
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        salir = new javax.swing.JMenuItem();
        gestion = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        productos = new javax.swing.JMenuItem();
        operacion = new javax.swing.JMenu();
        ventas = new javax.swing.JMenuItem();
        reportes = new javax.swing.JMenu();
        ventasDia = new javax.swing.JMenuItem();
        topProductos = new javax.swing.JMenuItem();
        eventos = new javax.swing.JMenu();
        torneos = new javax.swing.JMenuItem();
        ayuda = new javax.swing.JMenu();
        acercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 600));

        jLabel1.setText("Usuario: (Sin iniciar sesion)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentPanel.setPreferredSize(new java.awt.Dimension(900, 600));

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jMenuBar1.setPreferredSize(new java.awt.Dimension(900, 23));

        archivo.setText("Archivo");

        jMenuItem1.setText("Cerrar sesion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        archivo.add(jMenuItem1);

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        archivo.add(salir);

        jMenuBar1.add(archivo);

        gestion.setText("Gestion");

        jMenuItem3.setText("Usuarios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        gestion.add(jMenuItem3);

        productos.setText("Productos");
        productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosActionPerformed(evt);
            }
        });
        gestion.add(productos);

        jMenuBar1.add(gestion);

        operacion.setText("Operacion");

        ventas.setText("Ventas");
        ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventasActionPerformed(evt);
            }
        });
        operacion.add(ventas);

        jMenuBar1.add(operacion);

        reportes.setText("Reportes");
        reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportesActionPerformed(evt);
            }
        });

        ventasDia.setText("Ventas del dia");
        ventasDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventasDiaActionPerformed(evt);
            }
        });
        reportes.add(ventasDia);

        topProductos.setText("Top productos");
        topProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topProductosActionPerformed(evt);
            }
        });
        reportes.add(topProductos);

        jMenuBar1.add(reportes);

        eventos.setText("Eventos");

        torneos.setText("Torneos");
        torneos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                torneosActionPerformed(evt);
            }
        });
        eventos.add(torneos);

        jMenuBar1.add(eventos);

        ayuda.setText("Ayuda");

        acercaDe.setText("Acerca de...");
        acercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaDeActionPerformed(evt);
            }
        });
        ayuda.add(acercaDe);

        jMenuBar1.add(ayuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            this.dispose();
            new cl.TomP22.pnb.gui.LoginFrame().setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        mostrarVista("USUARIOS");
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_salirActionPerformed

    private void productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosActionPerformed
        mostrarVista("PRODUCTOS");
    }//GEN-LAST:event_productosActionPerformed

    private void ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ventasActionPerformed
        mostrarVista("VENTAS");
    }//GEN-LAST:event_ventasActionPerformed

    private void reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportesActionPerformed

    }//GEN-LAST:event_reportesActionPerformed

    private void ventasDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ventasDiaActionPerformed
        mostrarVista("REPORTES");
    }//GEN-LAST:event_ventasDiaActionPerformed

    private void topProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topProductosActionPerformed

    }//GEN-LAST:event_topProductosActionPerformed

    private void torneosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_torneosActionPerformed
        mostrarVista("EVENTOS");
    }//GEN-LAST:event_torneosActionPerformed

    private void acercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaDeActionPerformed
        JOptionPane.showMessageDialog(this,
            "<html><center>" +
            "<h2>Pixel & Bean</h2>" +
            "<p>Sistema de Gestión para Café-Arcade</p>" +
            "<p>Versión 2.0.0</p>" +
            "<p style='margin-top: 10px;'>Desarrollado por: Tomas Peña</p>" +
            "<p>Asignatura: Programación Orientada a Objetos</p>" +
            "<p>Año: 2025</p>" +
            "</center></html>",
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_acercaDeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercaDe;
    private javax.swing.JMenu archivo;
    private javax.swing.JMenu ayuda;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JMenu eventos;
    private javax.swing.JMenu gestion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu operacion;
    private javax.swing.JMenuItem productos;
    private javax.swing.JMenu reportes;
    private javax.swing.JMenuItem salir;
    private javax.swing.JMenuItem topProductos;
    private javax.swing.JMenuItem torneos;
    private javax.swing.JMenuItem ventas;
    private javax.swing.JMenuItem ventasDia;
    // End of variables declaration//GEN-END:variables
}

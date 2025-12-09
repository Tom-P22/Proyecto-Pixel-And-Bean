/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cl.TomP22.pnb.gui;


import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
/**
 *
 * @author Robin
 */
public class LoginFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());

    /**
     * Creates new form LoginFrame
     */
    
    private java.awt.image.BufferedImage imagenOriginal;
    
    public LoginFrame() {
        initComponents();
        setTitle("Iniciar Sesión – Pixel & Bean");
        setLocationRelativeTo(null);
        setResizable(false);
  
        try {
            Image icon = ImageIO.read(getClass().getResource("/resources/icons/logo.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.err.println("No se pudo cargar el ícono: " + e.getMessage());
        }
        
        try {
        
            imagenOriginal = javax.imageio.ImageIO.read(getClass().getResource("/resources/icons/fondo.png"));
        } catch (java.io.IOException e) {
            System.err.println("No se pudo cargar la imagen: " + e.getMessage());
        }

        jLabel4.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                escalarImagen();
            }
        });
        
        Font miFuentePixel = cargarFuentePixel(24f);
        Font miFuenteTitulo = cargarFuentePixel(80f); 
        Font miFuenteTexto = cargarFuentePixel (16f);
        
        jLabel2.setFont(miFuentePixel);
        jLabel3.setFont(miFuentePixel);
        jLabel5.setFont(miFuentePixel);
    
        jButton1.setFont(miFuentePixel);

        jTextField1.setFont(miFuentePixel);
        jPasswordField1.setFont(miFuentePixel);
        
        JOutlineLabel labelTitulo = (JOutlineLabel) jLabel1;

        labelTitulo.setFont(miFuenteTitulo);
        labelTitulo.setText("Pixel & Bean");
        labelTitulo.setForeground(Color.WHITE);
        Color azulOscuro = new Color(35, 92, 132); 
        labelTitulo.setOutlineColor(azulOscuro);
        labelTitulo.setOutlineOffset(4);
        
        JOutlineLabel labelSubtitulo = (JOutlineLabel) jLabel5;
        labelSubtitulo.setFont(miFuentePixel);
        labelSubtitulo.setForeground(Color.WHITE);
        labelSubtitulo.setOutlineColor(azulOscuro);
        labelSubtitulo.setOutlineOffset(2);
        
        JOutlineLabel labelContraseña = (JOutlineLabel) jLabel3;
        labelContraseña.setFont(miFuenteTexto);
        labelContraseña.setForeground(Color.WHITE);
        labelContraseña.setOutlineColor(azulOscuro);
        labelContraseña.setOutlineOffset(2);
        
        JOutlineLabel labelUsuario = (JOutlineLabel) jLabel2;
        labelUsuario.setFont(miFuenteTexto);
        labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setOutlineColor(azulOscuro);
        labelUsuario.setOutlineOffset(2);
       
        escalarImagen();
    }
private void escalarImagen() {
    if (imagenOriginal == null) {
        return;
    }
    int ancho = jLabel4.getWidth();
    int alto = jLabel4.getHeight();

    if (ancho <= 0 || alto <= 0) {
        return;
    }

    java.awt.Image imgEscalada = imagenOriginal.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);

    jLabel4.setIcon(new javax.swing.ImageIcon(imgEscalada));
}
    
public Font cargarFuentePixel(float tamano) {
        Font fuentePixel = null;
        try {
            String rutaFuente = "/resources/fonts/upheaval/upheavtt.ttf"; 
            InputStream is = getClass().getResourceAsStream(rutaFuente);
            
            if (is == null) {
                System.err.println("Error: No se pudo encontrar la fuente en " + rutaFuente);
                fuentePixel = new Font("Monospaced", Font.PLAIN, (int)tamano);
            } else {
                Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, is);
                fuentePixel = fuenteBase.deriveFont(tamano);
                is.close();
            }
        } catch (FontFormatException | IOException e) {
            System.err.println("Error al cargar la fuente: " + e.getMessage());
            fuentePixel = new Font("Monospaced", Font.PLAIN, (int)tamano);
        }
        return fuentePixel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new JOutlineLabel();
        jLabel2 = new JOutlineLabel();
        jLabel3 = new JOutlineLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new JOutlineLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        setSize(new java.awt.Dimension(800, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pixel & Bean");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 30, 680, 90);

        jLabel2.setText(" Usuario:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(190, 180, 180, 16);

        jLabel3.setText(" Contraseña:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(160, 210, 180, 20);

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jPasswordField1);
        jPasswordField1.setBounds(310, 210, 170, 30);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(310, 170, 170, 30);

        jButton1.setBackground(new java.awt.Color(35, 92, 132));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Iniciar sesion");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(280, 260, 220, 40);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText(" Cafe - Retro Arcade");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(250, 110, 310, 50);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/fondo.png"))); // NOI18N
        jLabel4.setText("Retro cafe - Arcade");
        jLabel4.setMaximumSize(new java.awt.Dimension(800, 600));
        jLabel4.setMinimumSize(new java.awt.Dimension(800, 600));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 0, 800, 600);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String user = jTextField1.getText();
    String pass = new String(jPasswordField1.getPassword());

    if (user.equals("admin") && pass.equals("1234")) {
        // Login exitoso
        MainFrame main = new MainFrame();
        main.setVisible(true);
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(this,
            "Usuario o contraseña incorrectos",
            "Error de autenticación",
            JOptionPane.ERROR_MESSAGE);
    
    }//GEN-LAST:event_jButton1ActionPerformed
    }
    
    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

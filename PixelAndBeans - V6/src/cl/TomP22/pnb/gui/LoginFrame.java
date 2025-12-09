package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.app.ApplicationContext;
import cl.TomP22.pnb.controller.LoginController;
import cl.TomP22.pnb.model.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class LoginFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());
    private LoginController controller;
    private java.awt.image.BufferedImage imagenOriginal;
    
    public LoginFrame() {
        this.controller = ApplicationContext.getInstance().getLoginController();
        
        initComponents();
        setTitle("Iniciar Sesión – Pixel & Bean");
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(900, 600);
        try {
            Image icon = ImageIO.read(getClass().getResource("/resources/icons/logo.png"));
            setIconImage(icon);
        } catch (IOException e) {
            System.err.println("No se pudo cargar el ícono: " + e.getMessage());
        }
        
        try {
            imagenOriginal = javax.imageio.ImageIO.read(getClass().getResource("/resources/icons/fondofinal.png"));
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

        txtUsername.setFont(miFuentePixel);
        txtPassword.setFont(miFuentePixel);
        
        if (jLabel1 instanceof JOutlineLabel) {
            JOutlineLabel labelTitulo = (JOutlineLabel) jLabel1;
            labelTitulo.setFont(miFuenteTitulo);
            labelTitulo.setText("Pixel And Bean");
            labelTitulo.setForeground(Color.WHITE);
            Color azulOscuro = new Color(35, 92, 132); 
            labelTitulo.setOutlineColor(azulOscuro);
            labelTitulo.setOutlineOffset(4);
        }
        
        if (jLabel5 instanceof JOutlineLabel) {
            JOutlineLabel labelSubtitulo = (JOutlineLabel) jLabel5;
            labelSubtitulo.setFont(miFuentePixel);
            labelSubtitulo.setForeground(Color.WHITE);
            Color azulOscuro = new Color(35, 92, 132);
            labelSubtitulo.setOutlineColor(azulOscuro);
            labelSubtitulo.setOutlineOffset(2);
        }
        
        if (jLabel3 instanceof JOutlineLabel) {
            JOutlineLabel labelContraseña = (JOutlineLabel) jLabel3;
            labelContraseña.setFont(miFuenteTexto);
            labelContraseña.setForeground(Color.WHITE);
            Color azulOscuro = new Color(35, 92, 132);
            labelContraseña.setOutlineColor(azulOscuro);
            labelContraseña.setOutlineOffset(2);
        }
        
        if (jLabel2 instanceof JOutlineLabel) {
            JOutlineLabel labelUsuario = (JOutlineLabel) jLabel2;
            labelUsuario.setFont(miFuenteTexto);
            labelUsuario.setForeground(Color.WHITE);
            Color azulOscuro = new Color(35, 92, 132);
            labelUsuario.setOutlineColor(azulOscuro);
            labelUsuario.setOutlineOffset(2);
        }
        
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


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new JOutlineLabel();
        jLabel2 = new JOutlineLabel();
        jLabel3 = new JOutlineLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new JOutlineLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 80)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pixel and Bean");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(110, 40, 680, 90);

        jLabel2.setText(" Usuario:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(280, 180, 180, 16);

        jLabel3.setText(" Contraseña:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(250, 210, 180, 20);
        jPanel1.add(txtPassword);
        txtPassword.setBounds(370, 210, 170, 30);
        jPanel1.add(txtUsername);
        txtUsername.setBounds(370, 170, 170, 30);

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
        jButton1.setBounds(340, 260, 220, 40);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText(" Cafe - Retro Arcade");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(310, 120, 310, 50);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/fondofinal.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 0, 900, 600);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor completa todos los campos",
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Usuario usuario = controller.autenticar(username, password);
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido " + usuario.getNombreCompleto() + "!",
                "Login exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            new MainFrame(usuario).setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }                             
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}

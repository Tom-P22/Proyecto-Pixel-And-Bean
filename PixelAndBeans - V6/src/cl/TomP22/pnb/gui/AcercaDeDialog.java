package cl.TomP22.pnb.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AcercaDeDialog extends JDialog {
    
    private Image imagenFondo;

    public AcercaDeDialog(Frame parent) {
        super(parent, "Acerca de Pixel & Bean", true);
        cargarImagenFondo();
        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void cargarImagenFondo() {
        try {
            URL url = getClass().getResource("/resources/icons/fondofinal.png");
            if (url != null) {
                imagenFondo = new ImageIcon(url).getImage();
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de fondo.");
        }
    }
    
    private void initComponents() {
        Font miFuentePixel = cargarFuentePixel(24f); 
        Font miFuenteTitulo = cargarFuentePixel(45f);
        Font miFuenteTexto = cargarFuentePixel(16f);
        
        setLayout(new BorderLayout(0, 0));
        
        JPanel panelContenido = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(30, 30, 30));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenido.setOpaque(false);

        Color azulOscuro = new Color(35, 92, 132);
        
        JOutlineLabel lblTitulo = new JOutlineLabel("Pixel And Bean");
        lblTitulo.setFont(miFuenteTitulo);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE); 
        lblTitulo.setOutlineColor(azulOscuro);
        lblTitulo.setOutlineOffset(3); 
        
        JOutlineLabel lblSub = new JOutlineLabel("Sistema de Gestión v3.5");
        lblSub.setFont(miFuentePixel);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setForeground(Color.WHITE); 
        lblSub.setOutlineColor(azulOscuro);
        lblSub.setOutlineOffset(1); 
        
        JOutlineLabel lblAutor = new JOutlineLabel("Desarrollado por: Tomas Peña");
        lblAutor.setFont(miFuenteTexto);
        lblAutor.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAutor.setForeground(Color.WHITE); 
        lblAutor.setOutlineColor(azulOscuro); 
        lblAutor.setOutlineOffset(1);

        panelContenido.add(lblTitulo);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(lblSub);
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(lblAutor);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> dispose());
        
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(btnCerrar);
        
        add(panelContenido, BorderLayout.CENTER);
    }
    
    public Font cargarFuentePixel(float tamano) {
        Font fuentePixel = null;
        try {
            String rutaFuente = "/resources/fonts/upheaval/upheavtt.ttf"; 
            InputStream is = getClass().getResourceAsStream(rutaFuente);
            
            if (is == null) {
                is = getClass().getResourceAsStream("/fonts/upheaval/upheavtt.ttf");
            }

            if (is == null) {
                fuentePixel = new Font("Monospaced", Font.BOLD, (int)tamano);
            } else {
                Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, is);
                fuentePixel = fuenteBase.deriveFont(tamano);
                is.close();
            }
        } catch (FontFormatException | IOException e) {
            fuentePixel = new Font("Monospaced", Font.BOLD, (int)tamano);
        }
        return fuentePixel;
    }
}
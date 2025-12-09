
package cl.TomP22.pnb.gui;

import javax.swing.*;
import java.awt.*;

public class EventosPanel extends javax.swing.JPanel {

    public EventosPanel() {
        initiComponents();
    }
    
     private void initiComponents() {
        setLayout(new GridBagLayout());
        
        JLabel lblTitulo = new JLabel("<html><center>" +
            "<h1 style='color: #FF6B35;'>🎮 Eventos y Torneos</h1>" +
            "<p style='margin-top: 20px; font-size: 14px;'>" +
            "Este módulo está en desarrollo y estará disponible próximamente." +
            "</p>" +
            "<p style='margin-top: 30px; font-size: 12px; color: #666;'>" +
            "Funcionalidades planificadas:" +
            "</p>" +
            "<ul style='text-align: left; font-size: 12px; color: #666;'>" +
            "<li>Gestión de torneos semanales</li>" +
            "<li>Inscripción de participantes</li>" +
            "<li>Registro de resultados</li>" +
            "<li>Rankings y estadísticas</li>" +
            "</ul>" +
            "</center></html>");
        
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

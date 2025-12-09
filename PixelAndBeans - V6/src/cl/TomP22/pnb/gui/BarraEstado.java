package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BarraEstado extends JPanel {
    
    private JLabel lblMensaje;
    private JLabel lblUsuario;
    private JLabel lblReloj;
    private Timer timer;
    
    public BarraEstado(Usuario usuario) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        lblMensaje = new JLabel("Sistema Listo");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario = new JLabel();
        if (usuario != null) {
            lblUsuario.setText("Usuario: " + usuario.getUsername() + " [" + usuario.getRol() + "]");
        } else {
            lblUsuario.setText("Usuario: Invitado");
        }
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblReloj = new JLabel();
        lblReloj.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        panelDerecho.add(lblUsuario);
        panelDerecho.add(new JSeparator(JSeparator.VERTICAL));
        panelDerecho.add(lblReloj);
        add(lblMensaje, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.EAST);
        iniciarReloj();
    }

    public void mostrarExito(String mensaje) {
        lblMensaje.setText("✓ " + mensaje);
        lblMensaje.setForeground(new Color(0, 150, 0));
        limpiarMensajeDespues(3000);
    }
 
    public void mostrarError(String mensaje) {
        lblMensaje.setText("✕ " + mensaje);
        lblMensaje.setForeground(Color.RED);
        limpiarMensajeDespues(5000);
    }

    public void mostrarInfo(String mensaje) {
        lblMensaje.setText("ℹ " + mensaje);
        lblMensaje.setForeground(Color.BLACK);
    }

    private void limpiarMensajeDespues(int milisegundos) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        timer = new Timer(milisegundos, e -> {
            lblMensaje.setText("Sistema Listo");
            lblMensaje.setForeground(Color.BLACK);
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void iniciarReloj() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss  |  dd/MM/yyyy");
        Timer relojTimer = new Timer(1000, e -> {
            lblReloj.setText(LocalDateTime.now().format(formatter));
        });
        relojTimer.start();
        lblReloj.setText(LocalDateTime.now().format(formatter));
    }
}
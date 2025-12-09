package cl.TomP22.pnb.util;

import javax.swing.*;
import java.net.URL;

public class CargadorIconos {
    

    private static final String RUTA_ICONOS = "/resources/icons/";
    
    public static ImageIcon cargar(String nombre) {
        try {

            URL url = CargadorIconos.class.getResource(RUTA_ICONOS + nombre);
            if (url != null) {
                return new ImageIcon(url);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar icono: " + nombre);
        }
        return null;
    }
    
    public static ImageIcon cargarEscalado(String nombre, int ancho, int alto) {
        ImageIcon icono = cargar(nombre);
        if (icono != null) {
            java.awt.Image img = icono.getImage();
            java.awt.Image imgEscalada = img.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(imgEscalada);
        }
        return null;
    }
}
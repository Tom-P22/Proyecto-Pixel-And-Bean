package cl.TomP22.pnb.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.io.FileWriter;
import java.io.IOException;

public class ExportadorCSV {
    
    public static void exportarTabla(JTable tabla, String nombreArchivo) throws IOException {
        TableModel modelo = tabla.getModel();
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                writer.write(modelo.getColumnName(col));
                if (col < modelo.getColumnCount() - 1) writer.write(",");
            }
            writer.write("\n");
            
        
           for (int row = 0; row < modelo.getRowCount(); row++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(row, col);
                    String texto = (valor != null) ? valor.toString() : "";
                    if (texto.contains(",") || texto.contains("\n")) {
                        texto = "\"" + texto.replace("\"", "\"\"") + "\"";
                    }
                    writer.write(texto);
                    if (col < modelo.getColumnCount() - 1) writer.write(",");
                }
                writer.write("\n");
            }
        }
    }
    
    public static void mostrarDialogoExportar(JTable tabla, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte como CSV");
        fileChooser.setSelectedFile(new java.io.File("reporte.csv"));
        
        int resultado = fileChooser.showSaveDialog(parent);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                if (!ruta.toLowerCase().endsWith(".csv")) ruta += ".csv";
                
                exportarTabla(tabla, ruta);
                JOptionPane.showMessageDialog(parent, "Datos exportados correctamente a:\n" + ruta);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Error al exportar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
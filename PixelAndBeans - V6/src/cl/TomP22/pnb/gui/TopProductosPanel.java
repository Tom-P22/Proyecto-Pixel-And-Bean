package cl.TomP22.pnb.gui;

import cl.TomP22.pnb.app.ApplicationContext;
import cl.TomP22.pnb.controller.VentaController;
import cl.TomP22.pnb.model.ProductoVendido;
import cl.TomP22.pnb.util.ExportadorCSV; // Asegúrate de tener esta clase del paso anterior
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TopProductosPanel extends JPanel {
    
    private VentaController controller; 
    private JComboBox<String> cboRango;
    private JTable tblReporte;
    private DefaultTableModel model;
    private JLabel lblTotalGeneral;
    
    public TopProductosPanel() {
        // Obtenemos el controlador
        this.controller = ApplicationContext.getInstance().getVentaController();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // --- PANEL SUPERIOR (Filtros) ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        cboRango = new JComboBox<>(new String[]{"Hoy", "Última Semana", "Último Mes", "Año Actual"});
        JButton btnGenerar = new JButton("Generar Reporte");
        
        // AQUÍ ESTABA EL PROBLEMA: Conectamos el botón al método generar()
        btnGenerar.addActionListener(e -> generar());
        
        pnlTop.add(new JLabel("Rango de tiempo:"));
        pnlTop.add(cboRango);
        pnlTop.add(btnGenerar);
        
        add(pnlTop, BorderLayout.NORTH);
        
        // --- PANEL CENTRAL (Tabla) ---
        String[] cols = {"Posición", "Producto", "Cantidad Vendida", "Dinero Generado"};
        model = new DefaultTableModel(cols, 0) {
            @Override // Hacemos que no sea editable
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tblReporte = new JTable(model);
        // Ajustes estéticos de columnas
        tblReporte.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblReporte.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        add(new JScrollPane(tblReporte), BorderLayout.CENTER);
        
        // --- PANEL INFERIOR (Exportar y Total) ---
        JPanel pnlBot = new JPanel(new BorderLayout());
        
        lblTotalGeneral = new JLabel("Total Ventas Periodo: $0");
        lblTotalGeneral.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnExportar = new JButton("Exportar a CSV");
        btnExportar.addActionListener(e -> {
            if (tblReporte.getRowCount() > 0) {
                // Asegúrate de tener la clase ExportadorCSV creada
                try {
                    ExportadorCSV.mostrarDialogoExportar(tblReporte, this);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Falta la clase ExportadorCSV");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay datos para exportar");
            }
        });
        
        pnlBot.add(lblTotalGeneral, BorderLayout.WEST);
        pnlBot.add(btnExportar, BorderLayout.EAST);
        
        add(pnlBot, BorderLayout.SOUTH);
    }
    
    private void generar() {
        // 1. Calcular fechas según el combo
        LocalDate fin = LocalDate.now();
        LocalDate inicio = fin;
        
        String seleccion = (String) cboRango.getSelectedItem();
        switch (seleccion) {
            case "Última Semana": inicio = fin.minusWeeks(1); break;
            case "Último Mes":    inicio = fin.minusMonths(1); break;
            case "Año Actual":    inicio = LocalDate.of(fin.getYear(), 1, 1); break;
            default:              inicio = fin; break; // Hoy
        }
        
        try {
            // 2. Llamar al controlador (ESTO ES LO QUE FALTABA)
            List<ProductoVendido> lista = controller.obtenerTopProductos(inicio, fin);
            
            // 3. Limpiar tabla
            model.setRowCount(0);
            
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron ventas en este periodo.");
                lblTotalGeneral.setText("Total Ventas Periodo: $0");
                return;
            }
            
            // 4. Llenar tabla
            int posicion = 1;
            double sumaTotal = 0;
            
            for (ProductoVendido p : lista) {
                model.addRow(new Object[]{
                    posicion++, 
                    p.getNombre(), 
                    p.getCantidad(), 
                    String.format("$%,.0f", p.getTotalGenerado())
                });
                sumaTotal += p.getTotalGenerado();
            }
            
            lblTotalGeneral.setText(String.format("Total Ventas Periodo: $%,.0f", sumaTotal));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
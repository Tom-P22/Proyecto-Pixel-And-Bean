
package cl.TomP22.pnb;

import cl.TomP22.pnb.gui.LoginFrame;

public class PixelAndBeans {
    public static void main(String[] args) {
        
         java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}

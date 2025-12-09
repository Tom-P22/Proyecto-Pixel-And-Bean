/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cl.TomP22.pnb.gui;

/**
 *
 * @author Robin
 */

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

public class JOutlineLabel extends JLabel {

    private Color outlineColor = Color.BLUE;
    private int outlineOffset = 2;

    public JOutlineLabel() {
        super();
    }

    public JOutlineLabel(String text) {
        super(text);
    }


    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public int getOutlineOffset() {
        return outlineOffset;
    }

    public void setOutlineOffset(int outlineOffset) {
        this.outlineOffset = outlineOffset;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        String text = getText();
        g2.setFont(getFont());
        
        FontMetrics fm = g2.getFontMetrics();
       
        int x = 0;
        if (getHorizontalAlignment() == CENTER) {
            x = (getWidth() - fm.stringWidth(text)) / 2;
        } else if (getHorizontalAlignment() == RIGHT) {
            x = getWidth() - fm.stringWidth(text);
        }

        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        
        
        g2.setColor(outlineColor);
        int o = outlineOffset;
        g2.drawString(text, x - o, y - o);
        g2.drawString(text, x + o, y - o);
        g2.drawString(text, x - o, y + o);
        g2.drawString(text, x + o, y + o);
        g2.drawString(text, x, y - o);
        g2.drawString(text, x, y + o);
        g2.drawString(text, x - o, y);
        g2.drawString(text, x + o, y);
        
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}
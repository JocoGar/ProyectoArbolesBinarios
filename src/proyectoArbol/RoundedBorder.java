package proyectoArbol;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder extends JButton {
    private int arcWidth = 20;
    private int arcHeight = 20;

    public RoundedBorder(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        setBackground(Color.decode("#3498db"));
        setForeground(Color.WHITE);
        setMargin(new Insets(5, 15, 5, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }
        
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arcWidth, arcHeight));
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arcWidth, arcHeight));
        g2.dispose();
    }
}
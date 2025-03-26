package proyectoArbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TreePanel extends JPanel {
    private ArbolBinario arbol;
    private Integer nodoResaltado = null; 
    private List<NodeInfo> nodeInfoList = new ArrayList<>();

    public TreePanel(ArbolBinario arbol) {
        this.arbol = arbol;
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    NodeInfo clickedNode = getNodeAtPosition(e.getX(), e.getY());
                    if (clickedNode != null) {
                        showContextMenu(clickedNode, e.getX(), e.getY());
                    }
                }
            }
        });
    }
    
    // Setter para nodo resaltado
    public void setNodoResaltado(Integer valor) {
        this.nodoResaltado = valor;
    }
    
    // Obtiene la información del nodo (posición y radio) en la posición indicada
    private NodeInfo getNodeAtPosition(int x, int y) {
        for (NodeInfo info : nodeInfoList) {
            double dx = x - info.x;
            double dy = y - info.y;
            if (Math.sqrt(dx * dx + dy * dy) <= info.radio) {
                return info;
            }
        }
        return null;
    }
    
    // Muestra el menú contextual con la opción de eliminar
    private void showContextMenu(NodeInfo nodeInfo, int x, int y) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem eliminarItem = new JMenuItem("Eliminar nodo");
        eliminarItem.addActionListener(ev -> {
            arbol.eliminar(nodeInfo.nodo.valor);
            repaint();
        });
        menu.add(eliminarItem);
        menu.show(this, x, y);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        nodeInfoList.clear(); // Limpiar posiciones anteriores
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (arbol.raiz != null) {
            dibujarArbol(g2d, arbol.raiz, getWidth() / 2, 50, getWidth() / 4);
        }
        g2d.dispose();
    }
    
    // Método recursivo para dibujar el árbol y registrar las posiciones de cada nodo
    private void dibujarArbol(Graphics2D g, Nodo nodo, int x, int y, int espacio) {
        final int DIAMETRO = 40;
        final int RADIO = DIAMETRO / 2;
        final int VERTICAL = 70;
        
        // Dibujar el nodo
        g.setColor(Color.decode("#2ecc71"));
        g.fillOval(x - RADIO, y - RADIO, DIAMETRO, DIAMETRO);
        g.setColor(Color.decode("#27ae60"));
        g.setStroke(new BasicStroke(2));
        g.drawOval(x - RADIO, y - RADIO, DIAMETRO, DIAMETRO);
        
        // Registrar la posición del nodo para detectar clics
        nodeInfoList.add(new NodeInfo(nodo, x, y, RADIO));
        
        // Dibujar el valor del nodo
        String texto = String.valueOf(nodo.valor);
        g.setFont(new Font("Segoe UI", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int anchoTexto = fm.stringWidth(texto);
        int alturaTexto = fm.getAscent();
        g.setColor(Color.WHITE);
        g.drawString(texto, x - anchoTexto / 2, y + alturaTexto / 4);
        
        // Resaltar el nodo buscado si corresponde
        if (nodoResaltado != null && nodo.valor == nodoResaltado) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(3));
            g.drawOval(x - RADIO - 5, y - RADIO - 5, DIAMETRO + 10, DIAMETRO + 10);
        }
        
        // Dibujar el subárbol izquierdo
        if (nodo.izquierda != null) {
            int xIzq = x - espacio;
            int yIzq = y + VERTICAL;
            g.setColor(Color.decode("#34495e"));
            g.drawLine(x, y + RADIO, xIzq, yIzq - RADIO);
            dibujarArbol(g, nodo.izquierda, xIzq, yIzq, espacio / 2);
        }
        // Dibujar el subárbol derecho
        if (nodo.derecha != null) {
            int xDer = x + espacio;
            int yDer = y + VERTICAL;
            g.setColor(Color.decode("#34495e"));
            g.drawLine(x, y + RADIO, xDer, yDer - RADIO);
            dibujarArbol(g, nodo.derecha, xDer, yDer, espacio / 2);
        }
    }
    
    // Clase interna para almacenar información de posición de cada nodo dibujado
    private class NodeInfo {
        Nodo nodo;
        int x, y, radio;
        public NodeInfo(Nodo nodo, int x, int y, int radio) {
            this.nodo = nodo;
            this.x = x;
            this.y = y;
            this.radio = radio;
        }
    }
}

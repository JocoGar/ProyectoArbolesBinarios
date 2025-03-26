package proyectoArbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class MainFrame extends JFrame {
    private ArbolBinario arbol;
    private TreePanel treePanel;
    private JTextArea resultadoArea;

    public MainFrame() {
        arbol = new ArbolBinario();
        configurarInterfaz();
    }

    private void configurarInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Árbol Binario");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel izquierdo con botones
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelIzquierdo.setBackground(Color.WHITE);

        JButton btnAgregar = crearBoton("Agregar Nodo", e -> agregarNodo());

        JButton btnEliminarTodo = crearBoton("Eliminar todo", e -> eliminarTodo());
        JButton btnBuscar = crearBoton("Buscar Nodo", e -> buscarNodo());
        JButton btnAltura = crearBoton("Calcular Altura", e -> mostrarAltura());
        JButton btnHojas = crearBoton("Calcular Hojas", e -> mostrarHojas());
        JButton btnLimpiar = crearBoton("Limpiar", e -> limpiarResaltado());

        panelIzquierdo.add(btnAgregar);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnEliminarTodo);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnBuscar);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnAltura);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnHojas);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(btnLimpiar);

        JPanel contenedorIzquierdo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        contenedorIzquierdo.setBackground(Color.WHITE);
        contenedorIzquierdo.add(panelIzquierdo);
        add(contenedorIzquierdo, BorderLayout.WEST);

        // Panel central: dibujo del árbol
        treePanel = new TreePanel(arbol);
        add(treePanel, BorderLayout.CENTER);

        // Panel inferior para mostrar recorridos y exportar/importar
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBotonesRecorrido = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelBotonesRecorrido.setBackground(Color.WHITE);
        JButton btnPreorden = crearBoton("Preorden", e -> mostrarRecorrido("preorden"));
        JButton btnInorden = crearBoton("Inorden", e -> mostrarRecorrido("inorden"));
        JButton btnPostorden = crearBoton("Postorden", e -> mostrarRecorrido("postorden"));
        JButton btnExportar = crearBoton("Exportar", e -> exportarRecorridos());
        JButton btnImportar = crearBoton("Importar", e -> importarArbol());
        
        panelBotonesRecorrido.add(btnPreorden);
        panelBotonesRecorrido.add(btnInorden);
        panelBotonesRecorrido.add(btnPostorden);
        panelBotonesRecorrido.add(btnExportar);
        panelBotonesRecorrido.add(btnImportar);

        resultadoArea = new JTextArea(2, 20);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        JScrollPane scrollRecorrido = new JScrollPane(resultadoArea);

        panelInferior.add(panelBotonesRecorrido, BorderLayout.NORTH);
        panelInferior.add(scrollRecorrido, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }


    private JButton crearBoton(String texto, ActionListener listener) {
    RoundedBorder boton = new RoundedBorder(texto); // Usar RoundedButton
        boton.addActionListener(listener);
        return boton;
    }

    private void agregarNodo() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el valor a agregar:");
        try {
            int valor = Integer.parseInt(input);
            arbol.insertar(valor);
            treePanel.setNodoResaltado(null);
            treePanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido");
        }
    }
    

    private void eliminarTodo() {
        // Reiniciamos la estructura de datos
        arbol = new ArbolBinario();
        // Quitamos el treePanel actual y ponemos uno nuevo vacío
        remove(treePanel);
        treePanel = new TreePanel(arbol);
        add(treePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void buscarNodo() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el valor a buscar:");
        try {
            int valor = Integer.parseInt(input);
            boolean encontrado = arbol.buscar(valor);
            String mensaje = encontrado ? "El valor " + valor + " existe en el árbol" 
                                        : "El valor " + valor + " no fue encontrado";
            JOptionPane.showMessageDialog(this, mensaje);
            treePanel.setNodoResaltado(encontrado ? valor : null);
            treePanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido");
        }
    }

    private void mostrarAltura() {
        JOptionPane.showMessageDialog(this, "Altura del árbol: " + arbol.altura());
    }

    private void mostrarHojas() {
        JOptionPane.showMessageDialog(this, "Número de hojas: " + arbol.contarHojas());
    }

    private void mostrarRecorrido(String tipo) {
        String resultado = switch (tipo) {
            case "preorden" -> arbol.recorridoPreorden();
            case "inorden" -> arbol.recorridoInorden();
            case "postorden" -> arbol.recorridoPostorden();
            default -> "";
        };
        resultadoArea.setText(resultado);
    }
    
    private void exportarRecorridos() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write("Recorrido Preorden:\n");
                writer.write(arbol.recorridoPreorden() + "\n\n");
                writer.write("Recorrido Inorden:\n");
                writer.write(arbol.recorridoInorden() + "\n\n");
                writer.write("Recorrido Postorden:\n");
                writer.write(arbol.recorridoPostorden() + "\n");
                JOptionPane.showMessageDialog(this, "Recorridos exportados correctamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al exportar el archivo.");
            }
        }
    }
    
    private void importarArbol() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(archivo)) {
                String linea;
                String preorden = null;
                while (scanner.hasNextLine()) {
                    linea = scanner.nextLine().trim();
                    if (linea.equalsIgnoreCase("Recorrido Preorden:")) {
                        if (scanner.hasNextLine()) {
                            preorden = scanner.nextLine().trim();
                        }
                        break;
                    }
                }
                if (preorden != null && !preorden.isEmpty()) {
                    arbol = new ArbolBinario();
                    String[] valores = preorden.split("\\s*-\\s*");
                    for (String val : valores) {
                        if (!val.isEmpty()) {
                            try {
                                int valor = Integer.parseInt(val);
                                arbol.insertar(valor);
                            } catch (NumberFormatException ex) {
                               
                            }
                        }
                    }
                    remove(treePanel);
                    treePanel = new TreePanel(arbol);
                    add(treePanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    JOptionPane.showMessageDialog(this, "Árbol importado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "El archivo no contiene el formato esperado.");
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Archivo no encontrado.");
            }
        }
    }
    
    private void limpiarResaltado() {
        treePanel.setNodoResaltado(null);
        treePanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

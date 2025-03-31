package proyectoArbol;

import javax.swing.JOptionPane;

public class ArbolBinario {
    Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }
    
    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return false;
        }
        if (valor == nodo.valor) {
            return true;
        } else if (valor < nodo.valor) {
            return buscarRecursivo(nodo.izquierda, valor);
        } else {
            return buscarRecursivo(nodo.derecha, valor);
        }
    }

public void insertar(int valor) {
    if (existeNodo(raiz, valor)) {
        JOptionPane.showMessageDialog(null, "El nodo con el valor " + valor + " ya existe.");
        return;
    }
    raiz = insertarRecursivo(raiz, valor);
}

private boolean existeNodo(Nodo nodo, int valor) {
    if (nodo == null) return false;
    if (valor == nodo.valor) return true;
    return valor < nodo.valor ? existeNodo(nodo.izquierda, valor) : existeNodo(nodo.derecha, valor);
}

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);
        if (valor < nodo.valor) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor);
        }
        return nodo;
    }

    public void eliminar(int valor) {
        raiz = eliminarRecursivo(raiz, valor);
    }
// Método que retorna la profundidad (nivel) en el que se encuentra un valor en el árbol
// Retorna -1 si no se encuentra. La raíz se considera en el nivel 1.
public int buscarConProfundidad(int valor) {
    return buscarConProfundidadRecursivo(raiz, valor, 1);
}

private int buscarConProfundidadRecursivo(Nodo nodo, int valor, int nivel) {
    if (nodo == null) {
        return -1; // No se encontró
    }
    if (nodo.valor == valor) {
        return nivel;
    } else if (valor < nodo.valor) {
        return buscarConProfundidadRecursivo(nodo.izquierda, valor, nivel + 1);
    } else {
        return buscarConProfundidadRecursivo(nodo.derecha, valor, nivel + 1);
    }
}


    private Nodo eliminarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return null;
        if (valor < nodo.valor) {
            nodo.izquierda = eliminarRecursivo(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = eliminarRecursivo(nodo.derecha, valor);
        } else {
            if (nodo.izquierda == null) return nodo.derecha;
            else if (nodo.derecha == null) return nodo.izquierda;
            nodo.valor = obtenerMinimo(nodo.derecha);
            nodo.derecha = eliminarRecursivo(nodo.derecha, nodo.valor);
        }
        return nodo;
    }

    private int obtenerMinimo(Nodo nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo.valor;
    }

    public String recorridoPreorden() {
        StringBuilder sb = new StringBuilder();
        preordenRecursivo(raiz, sb);
        if(sb.length() > 0) sb.setLength(sb.length() - 3); // Eliminar el último " - "
        return sb.toString();
    }

    private void preordenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.valor).append(" - ");
            preordenRecursivo(nodo.izquierda, sb);
            preordenRecursivo(nodo.derecha, sb);
        }
    }

    public String recorridoInorden() {
        StringBuilder sb = new StringBuilder();
        inordenRecursivo(raiz, sb);
        if(sb.length() > 0) sb.setLength(sb.length() - 3);
        return sb.toString();
    }

    private void inordenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierda, sb);
            sb.append(nodo.valor).append(" - ");
            inordenRecursivo(nodo.derecha, sb);
        }
    }

    public String recorridoPostorden() {
        StringBuilder sb = new StringBuilder();
        postordenRecursivo(raiz, sb);
        if(sb.length() > 0) sb.setLength(sb.length() - 3);
        return sb.toString();
    }

    private void postordenRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            postordenRecursivo(nodo.izquierda, sb);
            postordenRecursivo(nodo.derecha, sb);
            sb.append(nodo.valor).append(" - ");
        }
    }

    public int altura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(calcularAltura(nodo.izquierda), calcularAltura(nodo.derecha));
    }

    public int contarHojas() {
        return contarHojasRecursivo(raiz);
    }

    private int contarHojasRecursivo(Nodo nodo) {
        if (nodo == null) return 0;
        if (nodo.izquierda == null && nodo.derecha == null) return 1;
        return contarHojasRecursivo(nodo.izquierda) + contarHojasRecursivo(nodo.derecha);
    }
    
    
}

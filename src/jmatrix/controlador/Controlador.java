package jmatrix.controlador;

import jmatrix.Matriz;

import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JTable;

/**
 * Clase padre controladora de elementos del interfaz gráfica
 * @author matia
 */
public class Controlador {
    
    protected ArrayList<JList<String>> listas;
    protected ArrayList<JTable> tablas;
    protected ArrayList<Matriz> matrices;
    
    /**
     * Actualiza todas las tablas que están seleccionando una matriz específica
     * @param indice la posición de la matriz en la lista de matrices
     */
    public void actualizarTablas(int indice) {
        
        Matriz matriz = this.matrices.get(indice);
        
        for (int i = 0; i < this.listas.size(); i++) {
            
            JList<String> lista = this.listas.get(i);
            
            if (lista.getSelectedIndex() == indice & i < this.tablas.size()) {
                
                matriz.insertar( this.tablas.get(i) );
                
            }

        }
        
    }
    
}

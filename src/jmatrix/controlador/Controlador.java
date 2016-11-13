package jmatrix.controlador;

import jmatrix.Matriz;

import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JTable;

public class Controlador {
    
    protected ArrayList<JList<String>> listas;
    protected ArrayList<JTable> tablas;
    protected ArrayList<Matriz> matrices;
    
    public void actualizarTablas(int indice) {
        
        Matriz matriz = this.matrices.get(indice);
        
        for (int i = 0; i < this.listas.size(); i++) {
            
            JList<String> lista = this.listas.get(i);
            
            if (lista.getSelectedIndex() == indice) {
                
                JTable tabla = this.tablas.get(i);
                
                if (tabla != null){
                    
                    matriz.insertar(tabla);
                    
                }
                
            }

        }
        
    }
    
}

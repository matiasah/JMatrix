package jmatrix.controlador;

import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import jmatrix.ListaModeloMatriz;
import jmatrix.Matriz;

public class ControladorGUI extends Controlador {
    
    // Dimensiones con los controles en la lista/tabla actual
    private JSpinner anchoMatriz;
    private JSpinner largoMatriz;
    
    // Lista y tablas actuales
    private JList<String> lista;
    private JTable tabla;
    private JTextArea salida;
    
    public ControladorGUI(
            JSpinner anchoMatriz,
            JSpinner largoMatriz,
            JList<String> lista,
            JTable tabla,
            JTextArea salida,
            ArrayList<JList<String>> listas,
            ArrayList<JTable> tablas,
            ArrayList<Matriz> matrices
        ) {
        
        this.anchoMatriz = anchoMatriz;
        this.largoMatriz = largoMatriz;
        this.lista = lista;
        this.tabla = tabla;
        this.salida = salida;
        this.listas = listas;
        this.tablas = tablas;
        this.matrices = matrices;
        
    }
    
    private void actualizarListas() {
        
        ListaModeloMatriz modelo = new ListaModeloMatriz(matrices);
        
        for (JList<String> lista : this.listas) {
            
            int indice = lista.getSelectedIndex();
            
            lista.setModel(modelo);
            lista.setSelectedIndex(indice);
            
        }
        
    }
    
    public void propiedad(java.beans.PropertyChangeEvent evt) {
        
        if (!this.tabla.isEditing()) {
            
            // Una vez se termina de editar, se reemplazan los valores en el objeto Matriz
            
            int indice = this.lista.getSelectedIndex();
            
            if (indice >= 0) {
                
                // Verificar que hay elementos seleccionados
                
                Matriz matriz = this.matrices.get(indice);
                
                if (matriz != null) {
                    
                    // Verificar que la matríz en el índice indicado no es nula
                    
                    int x = this.tabla.getEditingColumn();
                    int y = this.tabla.getEditingRow();
                    Double valor = (Double) this.tabla.getValueAt(y, x);
                    
                    // Asignar el valor especificado a la matriz actual
                    
                    matriz.establecer(y, x, valor);
                    
                    // Si el resto de las listas seleccionaron la misma matriz
                    // Actualizar sus tablas
                    
                    this.actualizarTablas(indice);
                    
                }
                
            }
            
        }
        
    }
    
    public void ok(java.awt.event.ActionEvent evt) {
        
        int ancho = (Integer) this.anchoMatriz.getValue();
        int largo = (Integer) this.largoMatriz.getValue();
        
        if (ancho <= 0) {
            
            JOptionPane.showMessageDialog(null, "El ancho de la matriz debe ser mayor que cero");
            
        }else if (largo <= 0) {
            
            JOptionPane.showMessageDialog(null, "El largo de la matriz debe ser mayor que cero");
            
        }else{
        
            int indice = this.lista.getSelectedIndex();

            if (indice >= 0){

                Matriz matriz = new Matriz(ancho, largo);
                this.matrices.set(indice, matriz);
                this.actualizarTablas(indice);
                this.actualizarListas();

            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione la matriz");
                
            }
            
        }
        
    }
    
    public void seleccionar(javax.swing.event.ListSelectionEvent evt) {
        
        if (evt.getValueIsAdjusting()) {
            
            Matriz matriz = this.matrices.get(this.lista.getSelectedIndex());
            
            if (matriz != null) {
                
                matriz.insertar(this.tabla);
                
            }
            
        }
        
    }
    
    public void agregar() {
        
        int ancho = (Integer) this.anchoMatriz.getValue();
        int largo = (Integer) this.largoMatriz.getValue();
        
        if (ancho <= 0){
            
            JOptionPane.showMessageDialog(null, "El ancho de la matriz debe ser mayor que cero");
            
        }else if (largo <= 0) {
            
            JOptionPane.showMessageDialog(null, "El largo de la matriz debe ser mayor que cero");
            
        }else{
            
            this.matrices.add(new Matriz(ancho, largo));
            this.actualizarListas();
            
        }
        
    }
    
    public void escalonar() {
        
        int indice = this.lista.getSelectedIndex();
        
        if (indice >= 0) {
            
            Matriz matriz = this.matrices.get(indice);
            
            if (matriz == null) {
                
                JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
                
            }else{
                
                matriz = matriz.escalonar();
                
                this.matrices.set(indice, matriz);
                this.actualizarTablas(indice);
                
                this.salida.setText("");
                
                for (Object instruccion : matriz.obtenerInstrucciones()) {
                    
                    if (instruccion instanceof String) {
                        
                        this.salida.setText(instruccion + "\n" + this.salida.getText());
                        
                    }
                    
                }
                
            }
            
        }else{
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea escalonar");
            
        }
        
    }
    
    public void invertir() {
        
        int indice = this.lista.getSelectedIndex();
        
        if (indice >= 0) {
            
            Matriz matriz = this.matrices.get(indice);
            
            if (matriz == null) {
                
                JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
                
            }else{
                
                Matriz matrizInversa = matriz.inversa();
                
                if ( matriz.multiplicar(matrizInversa).equals(Matriz.identidad(matriz)) ) {
                
                    this.matrices.set(indice, matrizInversa);
                    this.actualizarTablas(indice);

                    this.salida.setText("");

                    for (Object instruccion : matriz.obtenerInstrucciones()) {

                        if (instruccion instanceof String) {

                            this.salida.setText(instruccion + "\n" + this.salida.getText());

                        }

                    }
                    
                }else{
                    
                    JOptionPane.showMessageDialog(null, "La matriz no se puede invertir, es singular");
                    
                }
                
            }
            
        }else{
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea invertir");
            
        }
        
    }
    
}

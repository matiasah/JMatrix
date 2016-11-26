package jmatrix.controlador;

import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import jmatrix.ListaModeloMatriz;
import jmatrix.Matriz;
import jmatrix.VentanaInformacion;
import jmatrix.excepciones.*;

public class ControladorGUI extends Controlador {
    
    // Dimensiones con los controles en la lista/tabla actual
    private JSpinner anchoMatriz;
    private JSpinner largoMatriz;
    
    // Lista y tablas actuales
    private JList<String> lista;
    private JTable tabla;
    private JTextArea salida;
    
    private VentanaInformacion ventanaInfo;
    
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
        
        this.ventanaInfo = new VentanaInformacion();
        
    }
    
    /**
     * Actualiza los elementos de las listas
     */
    private void actualizarListas() {
        
        ListaModeloMatriz modelo = new ListaModeloMatriz(matrices);
        
        for (JList<String> lista : this.listas) {
            
            int indice = lista.getSelectedIndex();
            
            lista.setModel(modelo);
            lista.setSelectedIndex(indice);
            
        }
        
    }
    
    private int obtenerSeleccion() throws ExcListaSeleccion {
        
        int seleccion = this.lista.getSelectedIndex();
        
        if (seleccion < 0) {
            
            throw new ExcListaSeleccion();
            
        }
        
        return seleccion;
        
    }
    
    private Matriz obtenerMatriz(int indice) throws ExcMatrizInexistente {
        
        Matriz matriz = this.matrices.get(indice);
        
        if (matriz == null) {
            
            throw new ExcMatrizInexistente();
            
        }
        
        return matriz;
        
    }
    
    /**
     * Evento al editar la celda de una tabla
     * @param evt 
     */
    public void propiedad(java.beans.PropertyChangeEvent evt) {
        
        if (!this.tabla.isEditing()) {
            
            // Una vez se termina de editar, se reemplazan los valores en el objeto Matriz
            
            try {
                
                int indice = this.obtenerSeleccion();
                
                // Verificar que hay elementos seleccionados
                
                Matriz matriz = this.obtenerMatriz(indice);
                
                // Verificar que la matríz en el índice indicado no es nula
                
                int x = this.tabla.getEditingColumn();
                int y = this.tabla.getEditingRow();
                Double valor = (Double) this.tabla.getValueAt(y, x);
                
                // Asignar el valor especificado a la matriz actual
                
                matriz.establecer(y, x, valor);
                
                // Si el resto de las listas seleccionaron la misma matriz
                // Actualizar sus tablas
                
                this.actualizarTablas(indice);
                this.ventanaInfo.informar(matriz);
                
            } catch (ExcListaSeleccion e) {
                
                // No es necesario generar un error para esta ventana
                
            } catch (ExcMatrizInexistente e) {
                
                // No es necesario generar un error para esta ventana
                
            }
            
        }
        
    }
    
    /**
     * Evento al apretar el boton 'OK'
     * @param evt 
     */
    public void ok(java.awt.event.ActionEvent evt) {
        
        int ancho = (Integer) this.anchoMatriz.getValue();
        int largo = (Integer) this.largoMatriz.getValue();
        
        try {
            
            int indice = this.obtenerSeleccion();
            
            Matriz matriz = new Matriz(ancho, largo);
            
            this.matrices.set(indice, matriz);
            this.actualizarTablas(indice);
            this.actualizarListas();
            this.ventanaInfo.informar(matriz);
        
        } catch (ExcListaSeleccion e) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz");
            
        } catch (ExcDimensionImposible e) {
            
            if (e.getAncho() <= 0) {
                
                JOptionPane.showMessageDialog(null, "El ancho de la matriz debe ser mayor que cero");
                
            } else if (e.getLargo() <= 0) {
                
                JOptionPane.showMessageDialog(null, "El largo de la matriz debe ser mayor que cero");
                
            }
                
        }
        
    }
    
    /**
     * Evento al seleccionar un elemento de la lista asociada
     * @param evt 
     */
    public void seleccionar(javax.swing.event.ListSelectionEvent evt) {
        
        if (evt.getValueIsAdjusting()) {
            
            try {
                
                int indice = this.obtenerSeleccion();
                Matriz matriz = this.obtenerMatriz(indice);

                matriz.insertar(this.tabla);

                this.ventanaInfo.informar(matriz);
                
            } catch (ExcListaSeleccion e) {
                
            } catch (ExcMatrizInexistente e) {
                
            }
            
        }
        
    }
    
    /**
     * Evento al apretar el boton 'agregar'
     */
    public void agregar() {
        
        int ancho = (Integer) this.anchoMatriz.getValue();
        int largo = (Integer) this.largoMatriz.getValue();
        
        
        try {
        
            Matriz matrizNueva = new Matriz(ancho, largo);

            this.matrices.add(matrizNueva);
            this.actualizarListas();
            this.lista.setSelectedIndex(this.matrices.size() - 1);

            this.actualizarTablas(this.matrices.size() - 1);
            this.ventanaInfo.informar(matrizNueva);
            
        } catch (ExcDimensionImposible e) {
            
            if ( e.getAncho() <= 0) {
                
                JOptionPane.showMessageDialog(null, "El ancho de la matriz debe ser mayor que cero");
                    
            } else if ( e.getLargo() <= 0) {
                
                JOptionPane.showMessageDialog(null, "El largo de la matriz debe ser mayor que cero");
                
            }
                
        }
        
    }
    
    /**
     * Evento al apretar el boton 'escalonar'
     */
    public void escalonar() {
        
        try {
            
            int indice = this.obtenerSeleccion();
            
            Matriz matriz = this.obtenerMatriz(indice);
            Matriz matrizEscalonada = matriz.escalonar();
                
            this.matrices.set(indice, matrizEscalonada);

            this.actualizarTablas(indice);
            this.ventanaInfo.informar(matrizEscalonada);

            this.salida.setText("");

            for (Object instruccion : matrizEscalonada.obtenerInstrucciones()) {

                if (instruccion instanceof String) {

                    this.salida.setText(this.salida.getText() + "\n" + instruccion);

                }
                        
            }
            
        } catch (ExcDimensionImposible e) {
            
        } catch (ExcListaSeleccion e) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea escalonar");
            
        } catch (ExcMatrizInexistente e) {
            
            JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
            
        }
        
    }
    
    /**
     * Evento al apretar el boton 'invertir'
     */
    public void invertir() {
        
        try {
            
            int indice = this.obtenerSeleccion();
            
            Matriz matriz = this.obtenerMatriz(indice);
            Matriz matrizInversa = matriz.inversa();

            if ( matriz.multiplicar(matrizInversa).equals( Matriz.identidad(matriz) ) ) {

                this.matrices.set(indice, matrizInversa);

                this.actualizarTablas(indice);
                this.ventanaInfo.informar(matrizInversa);

                this.salida.setText("");

                for (Object instruccion : matriz.obtenerInstrucciones()) {

                    if (instruccion instanceof String) {

                        this.salida.setText(this.salida.getText() + "\n" + instruccion);

                    }

                }

            }else{

                JOptionPane.showMessageDialog(null, "La matriz no se puede invertir, es singular");

            }
                    
        } catch (ExcDimensionImposible e) {
        
        } catch (ExcMultiplicacionImposible e) {
            
            JOptionPane.showMessageDialog(null, "La matriz no se puede invertir, no se puede multiplicar por su inversa");
            
        } catch (ExcListaSeleccion e) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea invertir");
            
        } catch (ExcMatrizInexistente e) {
            
            JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
            
        }
        
    }
    
    /**
     * Evento al apretar el boton 'transponer'
     */
    public void transponer() {
        
        try {
            
            int indice = this.obtenerSeleccion();
            
            Matriz matriz = this.obtenerMatriz(indice);
            Matriz matrizTranspuesta = matriz.transpuesta();

            this.matrices.set(indice, matrizTranspuesta);

            this.actualizarTablas(indice);
            this.ventanaInfo.informar(matrizTranspuesta);
        
        } catch (ExcDimensionImposible e) {
                    
            JOptionPane.showMessageDialog(null, "La matriz no se puede transponer");
            
        } catch (ExcMatrizInexistente e) {
            
            JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
            
        } catch (ExcListaSeleccion e) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea transponer");
            
        }
        
    }
    
    /**
     * Evento al apretar el boton '?'
     */
    public void informacion() {
        
        this.ventanaInfo.setVisible(true);
        
    }
    
}

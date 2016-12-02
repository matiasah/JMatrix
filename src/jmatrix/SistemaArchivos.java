package jmatrix;

import jmatrix.excepciones.*;

import java.io.RandomAccessFile;
import java.util.ArrayList;

import java.io.File;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

public class SistemaArchivos {
    
    /**
     * Lista de matrices seleccionables
     */
    private JList<String> lista;
    
    /**
     * Lista de todas las matrices
     */
    private ArrayList<Matriz> matrices;
    
    /**
     * Todas las listas con matrices seleccionables
     */
    private ArrayList<JList<String>> listas;
    
    /**
     * Selector de archivos
     */
    private JFileChooser selector;
    
    /**
     * Constructor de la clase
     * @param matrices lista de matrices
     * @param listas   listas del interfaz gráfica
     * @param selector objeto selector de archivos
     * @param lista    la lista propia de la ventana de archivos
     */
    public SistemaArchivos(ArrayList<Matriz> matrices, ArrayList<JList<String>> listas, JFileChooser selector, JList<String> lista) {
        
        this.matrices = matrices;
        this.listas = listas;
        this.selector = selector;
        this.lista = lista;
        
    }
    
    /**
     * Actualizar los elementos de las listas
     */
    private void actualizarListas() {
        
        ListaModeloMatriz modelo = new ListaModeloMatriz(matrices);
        
        for (JList<String> lista : this.listas) {
            
            int indice = lista.getSelectedIndex();
            
            lista.setModel(modelo);
            lista.setSelectedIndex(indice);
            
        }
        
    }
    
    /**
     * La matriz seleccionada actualmente
     * @return el índice de la matriz seleccionada actualmente
     * @throws ExcListaSeleccion 
     */
    private int obtenerSeleccion() throws ExcListaSeleccion {
        
        int seleccion = this.lista.getSelectedIndex();
        
        if (seleccion < 0) {
            
            throw new ExcListaSeleccion();
            
        }
        
        return seleccion;
        
    }
    
    /**
     * Obtener la matriz en un índice especifico
     * @param indice
     * @return La matriz en el índice especificado
     * @throws ExcMatrizInexistente 
     */
    private Matriz obtenerMatriz(int indice) throws ExcMatrizInexistente {
        
        Matriz matriz = this.matrices.get(indice);
        
        if (matriz == null) {
            
            throw new ExcMatrizInexistente();
            
        }
        
        return matriz;
        
    }
    
    /**
     * Cargar un archivo
     */
    public void cargar() {
        
        switch (this.selector.showOpenDialog(null)) {

            case javax.swing.JFileChooser.APPROVE_OPTION:

                File archivo = this.selector.getSelectedFile();

                try {
            
                    RandomAccessFile lectorArchivo = new RandomAccessFile(archivo, "r");

                    Matriz matriz = new Matriz(lectorArchivo);

                    if ( matriz.validar() ) {

                        this.matrices.add(matriz);
                        this.actualizarListas();

                    }else{

                        JOptionPane.showMessageDialog(null, "Imposible cargar matriz, el archivo está archivo dañado");

                    }
                    
                } catch (ExcDimensionImposible e) {
                    
                    JOptionPane.showMessageDialog(null, "Imposible cargar matriz, dimensiones erroneas");

                } catch (java.io.FileNotFoundException e) {

                    JOptionPane.showMessageDialog(null, "Archivo no encontrado");

                }

                break;

        }
        
    }
    
    /**
     * Guardar un archivo
     */
    public void guardar() {
        
        
        try {
        
            switch (this.selector.showSaveDialog(null)) {

                case javax.swing.JFileChooser.APPROVE_OPTION:
                    
                    int indice = this.obtenerSeleccion();

                    Matriz matriz = this.obtenerMatriz(indice);
                    
                    File archivo = this.selector.getSelectedFile();

                    RandomAccessFile escritorArchivo = new RandomAccessFile(archivo, "rw");
                    matriz.guardar(escritorArchivo);
                
                    break;
                
            }
            
        } catch (java.io.FileNotFoundException e) {
            
            JOptionPane.showMessageDialog(null, "Dirección inválida");
            
        } catch (ExcListaSeleccion e) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea guardar");
                    
        } catch (ExcMatrizInexistente e) {
            
            JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
            
        }
        
    }
    
}

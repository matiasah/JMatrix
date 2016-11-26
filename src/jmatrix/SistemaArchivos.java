package jmatrix;

import java.io.RandomAccessFile;
import java.util.ArrayList;

import java.io.File;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

public class SistemaArchivos {
    
    private JList<String> lista;
    
    private ArrayList<Matriz> matrices;
    private ArrayList<JList<String>> listas;
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
        
        int indice = this.lista.getSelectedIndex();
        
        if (indice >= 0) {
        
            switch (this.selector.showSaveDialog(null)) {

                case javax.swing.JFileChooser.APPROVE_OPTION:

                    Matriz matriz = this.matrices.get(indice);
                    
                    if (matriz == null) {
                        
                        JOptionPane.showMessageDialog(null, "Matriz no encontrada en la memoria");
                        
                    }else{
                    
                        File archivo = this.selector.getSelectedFile();

                        try {

                            RandomAccessFile escritorArchivo = new RandomAccessFile(archivo, "rw");
                            matriz.guardar(escritorArchivo);

                        } catch (java.io.FileNotFoundException e) {

                            JOptionPane.showMessageDialog(null, "Dirección inválida");

                        }
                        
                    }
                
                break;
                
            }
            
        }else{
            
            JOptionPane.showMessageDialog(null, "Seleccione la matriz que desea guardar");
                    
        }
        
    }
    
}

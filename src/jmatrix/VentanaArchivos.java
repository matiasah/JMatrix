/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmatrix;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import java.io.File;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.filechooser.FileNameExtensionFilter;

import jmatrix.Matriz;
import jmatrix.ListaModeloMatriz;

/**
 *
 * @author matia
 */
public class VentanaArchivos extends javax.swing.JFrame {
    
    private ArrayList<Matriz> matrices;
    
    private SistemaArchivos sistema;

    /**
     * Creates new form ControladorArchivo
     */
    public VentanaArchivos(ArrayList<Matriz> matrices, ArrayList<JList<String>> listas) {
        
        this.matrices = matrices;
        
        this.initComponents();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Archivo de texto (.txt)", "txt", "text");
        
        selectorArchivos.setFileFilter(filtroTxt);
        
        this.sistema = new SistemaArchivos(
                this.matrices,
                listas,
                this.selectorArchivos,
                this.lista
        );
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectorArchivos = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();
        texto = new javax.swing.JLabel();
        boton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(null);

        lista.setModel(new ListaModeloMatriz(this.matrices));
        jScrollPane1.setViewportView(lista);

        texto.setText("Seleccione cual matriz desea guardar");

        boton.setText("Guardar");
        boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(texto)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(texto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boton)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void botonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActionPerformed
        
        this.sistema.guardar();
        
    }//GEN-LAST:event_botonActionPerformed
    
    @Override public Image getIconImage() {

        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jmatrix/imagenes/Calculator-16.png"));
        
    }
    
    /**
     * Se muestra la ventana de guardado
     * @param evt 
     */
    public void guardar(java.awt.event.ActionEvent evt) {
        
        this.setVisible(true);
        
    }
    
    /**
     * Carga una matriz directamente a la lista de matrices
     * @param evt 
     */
    public void cargar(java.awt.event.ActionEvent evt) {
        
        this.sistema.cargar();
        
    }
    
    /**
     * Retorna la lista perteneciente a esta clase
     * @return 
     */
    public JList<String> obtenerLista() {
        
        return this.lista;
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista;
    private javax.swing.JFileChooser selectorArchivos;
    private javax.swing.JLabel texto;
    // End of variables declaration//GEN-END:variables
}

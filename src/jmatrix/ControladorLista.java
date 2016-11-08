/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmatrix;

import java.util.ArrayList;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JTable;

/**
 *
 * @author matia
 */
public class ControladorLista {
    
    private JSpinner anchoMatriz;
    private JSpinner largoMatriz;
    private JList<String> lista;
    private JTable tabla;
    private ArrayList<JList<String>> listas;
    private ArrayList<JTable> tablas;
    private ArrayList<Matriz> matrices;
    
    public ControladorLista(
            JSpinner anchoMatriz,
            JSpinner largoMatriz,
            JList<String> lista,
            JTable tabla,
            ArrayList<JList<String>> listas,
            ArrayList<JTable> tablas,
            ArrayList<Matriz> matrices
        ) {
        
        this.anchoMatriz = anchoMatriz;
        this.largoMatriz = largoMatriz;
        this.lista = lista;
        this.tabla = tabla;
        this.listas = listas;
        this.tablas = tablas;
        this.matrices = matrices;
        
    }
    
    private void actualizar() {
        
        ListaModeloMatriz modelo = new ListaModeloMatriz(matrices);
        
        for (JList<String> lista : this.listas) {
            
            int indice = lista.getSelectedIndex();
            
            lista.setModel(modelo);
            lista.setSelectedIndex(indice);
            
        }
        
    }
    
    public void propiedad(java.beans.PropertyChangeEvent evt) {
        
        System.out.println(evt.getPropertyName()+":"+evt.getNewValue());
        
    }
    
    public void ok(java.awt.event.ActionEvent evt) {
        
        int ancho = (Integer) anchoMatriz.getValue();
        int largo = (Integer) largoMatriz.getValue();
        
        int indice = lista.getSelectedIndex();
        
        if (indice >= 0){
        
            Matriz matriz = new Matriz(ancho, largo);
            matriz.insertar(this.tabla);

            for (int i = 0; i < listas.size(); i++) {

                JList<String> estaLista = listas.get(i);

                if (estaLista != lista) {

                    if (estaLista.getSelectedIndex() == indice) {

                        matriz.insertar(tablas.get(i));

                    }

                }

            }

            this.matrices.set(indice, matriz);
            this.actualizar();
            
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
        
        int ancho = (Integer) anchoMatriz.getValue();
        int largo = (Integer) largoMatriz.getValue();
        
        this.matrices.add(new Matriz(ancho, largo));
        this.actualizar();
        
    }
    
}

package jmatrix.controlador;

import jmatrix.Matriz;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JOptionPane;

public class ControladorMatematico extends Controlador {
    
    private JList<String> primeraLista;
    private JList<String> segundaLista;
    private JList<String> resultadoLista;
    
    public ControladorMatematico(
            JList<String> primeraLista,
            JList<String> segundaLista,
            JList<String> resultadoLista,
            ArrayList<JList<String>> listas,
            ArrayList<JTable> tablas,
            ArrayList<Matriz> matrices
        ) {
        
        this.tablas = tablas;
        this.listas = listas;
        this.matrices = matrices;
        
        this.primeraLista = primeraLista;
        this.segundaLista = segundaLista;
        this.resultadoLista = resultadoLista;
        
    }
    
    private Matriz obtenerPrimeraMatriz() {
        
        int indice = this.primeraLista.getSelectedIndex();
        
        if (indice >= 0) {
            
            return this.matrices.get(indice);
            
        }
        
        return null;
        
    }
    
    private Matriz obtenerSegundaMatriz() {
        
        int indice = this.segundaLista.getSelectedIndex();
        
        if (indice >= 0) {
            
            return this.matrices.get(indice);
            
        }
        
        return null;
        
    }
    
    public void multiplicar() {
        
        Matriz primeraMatriz = this.obtenerPrimeraMatriz();
        Matriz segundaMatriz = this.obtenerSegundaMatriz();
        
        if (primeraMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la primera matriz");
            
        }else if (segundaMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la segunda matriz");
            
        }else{
            
            int indiceResultado = this.resultadoLista.getSelectedIndex();
            
            if (indiceResultado >= 0) {
                
                Matriz resultado = primeraMatriz.multiplicar(segundaMatriz);
                
                if (resultado == null) {
                    
                    JOptionPane.showMessageDialog(null, "Multiplicación imposible");
                    
                }else{
                    
                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
    public void dividir() {
        
        Matriz primeraMatriz = this.obtenerPrimeraMatriz();
        Matriz segundaMatriz = this.obtenerSegundaMatriz();
        
        if (primeraMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la primera matriz");
            
        }else if (segundaMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la segunda matriz");
            
        }else{
            
            int indiceResultado = this.resultadoLista.getSelectedIndex();
            
            if (indiceResultado >= 0) {
                
                Matriz resultado = primeraMatriz.multiplicar(segundaMatriz.inversa());
                
                if (resultado == null) {
                    
                    JOptionPane.showMessageDialog(null, "División imposible");
                    
                }else{
                    
                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
    public void sumar() {
        
        Matriz primeraMatriz = this.obtenerPrimeraMatriz();
        Matriz segundaMatriz = this.obtenerSegundaMatriz();
        
        if (primeraMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la primera matriz");
            
        }else if (segundaMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la segunda matriz");
            
        }else{
            
            int indiceResultado = this.resultadoLista.getSelectedIndex();
            
            if (indiceResultado >= 0) {
                
                Matriz resultado = primeraMatriz.sumar(segundaMatriz);
                
                if (resultado == null) {
                    
                    JOptionPane.showMessageDialog(null, "Suma imposible");
                    
                }else{
                    
                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
    public void restar() {
        
        Matriz primeraMatriz = this.obtenerPrimeraMatriz();
        Matriz segundaMatriz = this.obtenerSegundaMatriz();
        
        if (primeraMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la primera matriz");
            
        }else if (segundaMatriz == null) {
            
            JOptionPane.showMessageDialog(null, "Seleccione la segunda matriz");
            
        }else{
            
            int indiceResultado = this.resultadoLista.getSelectedIndex();
            
            if (indiceResultado >= 0) {
                
                Matriz resultado = primeraMatriz.restar(segundaMatriz);
                
                if (resultado == null) {
                    
                    JOptionPane.showMessageDialog(null, "Resta imposible");
                    
                }else{
                    
                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
}
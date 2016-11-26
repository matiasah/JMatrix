package jmatrix.controlador;

import jmatrix.Matriz;
import jmatrix.excepciones.*;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JOptionPane;

public class ControladorMatematico extends Controlador {
    
    private JList<String> primeraLista;
    private JList<String> segundaLista;
    private JList<String> resultadoLista;
    
    /**
     * Constructor de la clase
     * @param primeraLista   primera lista donde se selecciona la primera matriz
     * @param segundaLista   segunda lista donde se selecciona la segunda matriz
     * @param resultadoLista lista de resultado, donde se asignará la matriz resultado de la operación
     * @param listas         todas las listas utilizadas
     * @param tablas         todas las tablas utilizadas
     * @param matrices       todas las matrices utilizadas
     */
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
    
    /**
     * Método privado, simplifica la obtención de matrices
     * @return la primera matriz de la operación
     */
    private Matriz obtenerPrimeraMatriz() {
        
        int indice = this.primeraLista.getSelectedIndex();
        
        if (indice >= 0) {
            
            return this.matrices.get(indice);
            
        }
        
        return null;
        
    }
    
    /**
     * Método privado, simplifica la obtención de matrices
     * @return la segunda matriz de la operación
     */
    private Matriz obtenerSegundaMatriz() {
        
        int indice = this.segundaLista.getSelectedIndex();
        
        if (indice >= 0) {
            
            return this.matrices.get(indice);
            
        }
        
        return null;
        
    }
    
    /**
     * Evento ejecutado al apretar el boton de multiplicación
     */
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
                
                try {
                
                    Matriz resultado = primeraMatriz.multiplicar(segundaMatriz);

                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                } catch (ExcDimensionImposible e) {
                    
                } catch (ExcMultiplicacionImposible e) {
                    
                    JOptionPane.showMessageDialog(null, "Multiplicación imposible");
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
    /**
     * Evento ejecutado al apretar el boton de división
     */
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
                
                try {
                    
                    Matriz matrizInversa = segundaMatriz.inversa();

                    if ( segundaMatriz.multiplicar(matrizInversa).equals( Matriz.identidad(segundaMatriz) ) ) {

                        Matriz resultado = primeraMatriz.multiplicar(matrizInversa);

                        this.matrices.set(indiceResultado, resultado);
                        this.actualizarTablas(indiceResultado);

                    }else{

                        JOptionPane.showMessageDialog(null, "División imposible, la segunda matriz no se puede invertir");

                    }
                    
                } catch (ExcDimensionImposible e) {
                    
                    JOptionPane.showMessageDialog(null, "División imposible");
                    
                } catch (ExcMultiplicacionImposible e) {
                    
                    JOptionPane.showMessageDialog(null, "División imposible, no se puede multiplicar por la inversa");
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
    /**
     * Evento ejecutado al apretar el boton de suma
     */
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
                
                try {
                    
                    Matriz resultado = primeraMatriz.sumar(segundaMatriz);

                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                } catch (ExcDimensionImposible e) {
                    
                    JOptionPane.showMessageDialog(null, "Suma imposible");
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
    /**
     * Evento ejecutado al apretar el boton de resta
     */
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
                
                try {
                    Matriz resultado = primeraMatriz.restar(segundaMatriz);

                    this.matrices.set(indiceResultado, resultado);
                    this.actualizarTablas(indiceResultado);
                    
                } catch (ExcDimensionImposible e) {
                    
                    JOptionPane.showMessageDialog(null, "Resta imposible");
                    
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Seleccione una matriz en la lista de resultados");
                
            }
            
        }
        
    }
    
}

package jmatrix;

import jmatrix.excepciones.*;

public class Vector {
    
    /**
     * Delta utilizado para comparar dos vectores
     */
    static double precision = Math.pow(2, -51);
    
    /**
     * Valores numéricos que se comparten con una matríz
     */
    private double [] elemento;
    
    /**
     * Constructor de la clase
     * @param ancho el número de ceros que debe contener el arreglo del vector
     * @throws ExcDimensionVImposible
     */
    public Vector(int ancho) throws ExcDimensionVImposible {
        
        if ( ancho <= 0 ) {
            
            throw new ExcDimensionVImposible( ancho );
            
        }
        
        this.elemento = new double[ancho];
        
    }
    
    /**
     * Constructor de la clase
     * @param elemento el arreglo que se le asignará al vector
     */
    public Vector(double [] elemento){
        
        this.elemento = elemento;
        
    }
    
    /**
     * Copia todos los elementos del vector
     * @return el nuevo vector con los elementos copiados
     * @throws ExcDimensionVImposible
     */
    public Vector clonar() throws ExcDimensionVImposible {
        
        int A = this.ancho();
        Vector vector = new Vector(A);
        
        for (int x = 0; x < A; x++){
            
            vector.establecer(x, this.obtener(x));
            
        }
        
        return vector;
        
    }
    
    /**
     * Los elementos de un vector separados por tabulación
     * @return el string que muestra los elementos del vector
     */
    @Override public String toString(){
        
        String str = "";
        
        for (int x = 0, A = this.ancho(); x < A; x++){
            str += ",\t" + this.elemento[x];
        }
        
        if (str.length() > 0){
            
            return "[" + str.substring(2) + "]";
            
        }
        
        return "[]";
    }
    
    /**
     * Compara dos vectores
     * @param objeto el vector con el que se desea comparar un objeto
     * @return true si ambos objetos contienen los mismos elementos, caso contrario retorna false
     */
    @Override public boolean equals(Object objeto){
        
        if (objeto instanceof Vector){
        
            Vector vector = (Vector) objeto;
            
            int A = this.ancho();
            
            if (vector.ancho() != A){

                return false;

            }

            for (int x = 0; x < A; x++){

                if ( Math.abs( this.obtener(x) - vector.obtener(x) ) > Vector.precision ){

                    return false;

                }
                
            }
            
            return true;
            
        }
        
        return false;
        
    }
    
    /**
     * Cambia un valor del vector
     * @param valor el valor que se desea insertar
     */
    public void establecer(int indice, double valor) {
        
        if (indice >= 0 & indice < this.ancho()){
            
            this.elemento[indice] = valor;
            
        }
        
    }
    
    /**
     * Aumenta un valor del vector
     * @param indice la posición donde se desea aumentar el valor
     * @param valor la cantidad que se va a aumentar
     */
    public void sumar(int indice, double valor){
        
        if (indice >= 0 & indice < this.ancho()){
            
            this.elemento[indice] += valor;
            
        }
        
    }
    
    /**
     * Obtiene un valor del vector
     * @param indice la posición donde se desea obtener el valor
     * @return el valor que se desea obtener
     */
    public double obtener(int indice){
        
        if (indice >= 0 & indice < this.ancho()){
            
            return this.elemento[indice];
            
        }
        
        return 0;
        
    }
    
    /**
     * Obtiene una copia del arreglo del vector pero en clase 'Double'
     * @return la copia del arreglo
     */
    public Double [] obtener() {
        
        Double elemento[] = new Double[this.elemento.length];
        
        for (int i = 0; i < this.elemento.length; i++) {
            
            elemento[i] = new Double(this.elemento[i]);
            
        }
        
        return elemento;
        
    }
    
    /**
     * La cantidad de elementos del vector
     * @return 
     */
    public int ancho(){
        
        return this.elemento.length;
        
    }
    
    /**
     * Genera un vector cuyos valores numéricos es la suma de dos vectores
     * @param vec el vector que se desea sumar
     * @return la suma de ambos vectores
     * @throws ExcDimensionVImposible
     */
    public Vector sumar(Vector vec) throws ExcDimensionVImposible {
        
        Vector suma = new Vector(
            
            Math.max(
                
                this.ancho(),
                vec.ancho()
                
            )
            
        );
        
        for (int x = 0, ancho = suma.ancho(); x < ancho; x++){
            
            suma.sumar(x, this.obtener(x) + vec.obtener(x));
            
        }
        
        return suma;
        
    }
    
    /**
     * Genera un vector cuyos valores numéricos es la suma de dos vectores
     * @param vector el vector que se desea sumar
     * @return la suma de ambos vectores
     * @throws ExcDimensionVImposible
     */
    public Vector restar(Vector vector) throws ExcDimensionVImposible {
        
        Vector resta = new Vector(
            
            Math.max(
                
                this.ancho(),
                vector.ancho()
                
            )
            
        );
        
        for (int x = 0, ancho = resta.ancho(); x < ancho; x++){
            
            resta.sumar(x, this.obtener(x) - vector.obtener(x));
            
        }
        
        return resta;
        
    }
    
    /**
     * Genera un vector cuyos valores numéricos es la multiplicación de un vector con un número
     * @param numero el número que multiplicará el vector
     * @return la multiplicación entre el vector y el número
     * @throws ExcDimensionVImposible
     */
    public Vector multiplicar(double numero) throws ExcDimensionVImposible {
        
        int ancho = this.ancho();
        Vector multiplicacion = new Vector(ancho);
        
        for (int x = 0; x < ancho; x++){
            
            multiplicacion.establecer(x, this.obtener(x) * numero);
            
        }
        
        return multiplicacion;
        
    }
    
    /**
     * Genera un vector cuyos valores numéricos es la multiplicación de un vector con un número
     * @param numero el número que dividirá el vector
     * @return la división entre el vector y el número
     * @throws ExcDimensionVImposible
     */
    public Vector dividir(double numero) throws ExcDimensionVImposible {
        
        if (numero == 0){
            
            // No se puede dividir por cero
            return null;
            
        }
        
        // Dividir 1 por el número y luego multiplicar el inverso cuesta menos CPU
        return this.multiplicar(1 / numero);
        
    }
    
    /**
     * Copia el vector y cambia el signo de sus valores numéricos
     * @return el vector opuesto
     * @throws ExcDimensionVImposible
     */
    public Vector opuesto() throws ExcDimensionVImposible{
        
        int ancho = this.ancho();
        Vector opuesto = new Vector(ancho);
        
        for (int x = 0; x < ancho; x++){
            
            opuesto.establecer(x, -this.obtener(x));
            
        }
        
        return opuesto;
        
    }
    
}

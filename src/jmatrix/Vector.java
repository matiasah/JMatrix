package jmatrix;


public class Vector {
    
    private double [] elemento;
    
    public Vector(int Ancho){
        elemento = new double[Ancho];
    }
    
    public Vector(double [] Elementos){
        elemento = Elementos;
    }
    
    public Vector clonar(){
        
        int A = this.ancho();
        Vector vector = new Vector(A);
        
        for (int x = 0; x < A; x++){
            
            vector.establecer(x, this.obtener(x));
            
        }
        
        return vector;
        
    }
    
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
    
    @Override public boolean equals(Object objeto){
        Vector vector = (Vector) objeto;
        
        if (vector != null){
            
            int A = this.ancho();
            
            if (vector.ancho() != A){
                
                return false;
                
            }
            
            for (int x = 0; x < A; x++){
                
                if (this.obtener(x) != vector.obtener(x)){
                    
                    return false;
                    
                }
                
            }
            
            return true;
        }
        
        return false;
        
    }
    
    public void establecer(int Indice, double Valor){
        
        if (Indice >= 0 & Indice < this.ancho()){
            
            this.elemento[Indice] = Valor;
            
        }
        
    }
    
    public void sumar(int Indice, double Valor){
        
        if (Indice >= 0 & Indice < this.ancho()){
            
            this.elemento[Indice] += Valor;
            
        }
        
    }
    
    public double obtener(int Indice){
        
        if (Indice >= 0 & Indice < this.ancho()){
            
            return this.elemento[Indice];
            
        }
        
        return 0;
        
    }
    
    public int ancho(){
        
        return this.elemento.length;
        
    }
    
    public Vector sumar(Vector vec){
        
        Vector suma = new Vector(
            Math.max(
                this.ancho(),
                vec.ancho()
            )
        );
        
        for (int x = 0, A = suma.ancho(); x < A; x++){
            
            suma.sumar(x, this.obtener(x) + vec.obtener(x));
            
        }
        
        return suma;
        
    }
    
    public Vector restar(Vector vector){
        
        Vector resta = new Vector(
            Math.max(
                this.ancho(),
                vector.ancho()
            )
        );
        
        for (int x = 0, A = resta.ancho(); x < A; x++){
            resta.sumar(x, this.obtener(x) - vector.obtener(x));
        }
        
        return vector;
        
    }
    
    public Vector multiplicar(double Numero){
        
        int A = this.ancho();
        Vector multiplicacion = new Vector(A);
        
        for (int x = 0; x < A; x++){
            
            multiplicacion.establecer(x, this.obtener(x) * Numero);
            
        }
        
        return multiplicacion;
        
    }
    
    public Vector dividir(double Numero){
        
        if (Numero == 0){
            
            // No se puede dividir por cero
            return null;
            
        }
        
        // Dividir 1 por el número y luego multiplicar el inverso cuesta menos CPU
        return this.multiplicar(1 / Numero);
        
    }
    
    public Vector opuesta(){
        
        int A = this.ancho();
        Vector opuesto = new Vector(A);
        
        for (int x = 0; x < A; x++){
            
            opuesto.establecer(x, -this.obtener(x));
            
        }
        
        return opuesto;
        
    }
    
}

package jmatrix;


public class Vector {
    
    private double [] elemento;
    
    public Vector(int ancho){
        this.elemento = new double[ancho];
    }
    
    public Vector(double [] elemento){
        this.elemento = elemento;
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
        
        if (objeto instanceof Vector){
        
            Vector vector = (Vector) objeto;
            
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
    
    public void establecer(int indice, double valor) {
        
        if (indice >= 0 & indice < this.ancho()){
            
            this.elemento[indice] = valor;
            
        }
        
    }
    
    public void sumar(int indice, double valor){
        
        if (indice >= 0 & indice < this.ancho()){
            
            this.elemento[indice] += valor;
            
        }
        
    }
    
    public double obtener(int indice){
        
        if (indice >= 0 & indice < this.ancho()){
            
            return this.elemento[indice];
            
        }
        
        return 0;
        
    }
    
    public Double [] obtener() {
        
        Double elemento[] = new Double[this.elemento.length];
        
        for (int i = 0; i < this.elemento.length; i++) {
            
            elemento[i] = new Double(this.elemento[i]);
            
        }
        
        return elemento;
        
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
        
        for (int x = 0, ancho = suma.ancho(); x < ancho; x++){
            
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
        
        for (int x = 0, ancho = resta.ancho(); x < ancho; x++){
            resta.sumar(x, this.obtener(x) - vector.obtener(x));
        }
        
        return resta;
        
    }
    
    public Vector multiplicar(double numero){
        
        int ancho = this.ancho();
        Vector multiplicacion = new Vector(ancho);
        
        for (int x = 0; x < ancho; x++){
            
            multiplicacion.establecer(x, this.obtener(x) * numero);
            
        }
        
        return multiplicacion;
        
    }
    
    public Vector dividir(double numero){
        
        if (numero == 0){
            
            // No se puede dividir por cero
            return null;
            
        }
        
        // Dividir 1 por el número y luego multiplicar el inverso cuesta menos CPU
        return this.multiplicar(1 / numero);
        
    }
    
    public Vector opuesto(){
        
        int ancho = this.ancho();
        Vector opuesto = new Vector(ancho);
        
        for (int x = 0; x < ancho; x++){
            
            opuesto.establecer(x, -this.obtener(x));
            
        }
        
        return opuesto;
        
    }
    
}

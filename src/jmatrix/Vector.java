package jmatrix;


public class Vector {
    
    private double [] Elemento;
    
    public Vector(int Ancho){
        Elemento = new double[Ancho];
    }
    
    public Vector(double [] Elementos){
        Elemento = Elementos;
    }
    
    public Vector Clonar(){
        Vector Salida = new Vector(Elemento.length);
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Establecer(x, Obtener(x));
        }
        
        return Salida;
    }
    
    @Override public String toString(){
        String Imprimible = "";
        
        for (int x = 0; x < Elemento.length; x++){
            Imprimible += ",\t" + Elemento[x];
        }
        
        return "[" + Imprimible.substring(2) + "]";
    }
    
    public void Establecer(int Indice, double Valor){
        if (Indice >= 0 & Indice < Elemento.length){
            Elemento[Indice] = Valor;
        }
    }
    
    public void Sumar(int Indice, double Valor){
        if (Indice >= 0 & Indice < Elemento.length){
            Elemento[Indice] += Valor;
        }
    }
    
    public double Obtener(int Indice){
        if (Indice >= 0 & Indice < Elemento.length){
            return Elemento[Indice];
        }
        return 0;
    }
    
    public int Ancho(){
        return Elemento.length;
    }
    
    public Vector Sumar(Vector Vec){
        Vector Salida = new Vector(Math.max(Ancho(), Vec.Ancho()));
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Sumar(x, Obtener(x) + Vec.Obtener(x));
        }
        
        return Salida;
    }
    
    public Vector Restar(Vector Vec){
        Vector Salida = new Vector(Math.max(Ancho(), Vec.Ancho()));
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Sumar(x, Obtener(x) - Vec.Obtener(x));
        }
        
        return Salida;
    }
    
    public Vector Multiplicar(double Numero){
        Vector Salida = new Vector(Ancho());
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Establecer(x, Obtener(x) * Numero);
        }
        
        return Salida;
    }
    
    public Vector Dividir(double Numero){
        Vector Salida = new Vector(Ancho());
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Establecer(x, Obtener(x) / Numero);
        }
        
        return Salida;
    }
}

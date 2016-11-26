package jmatrix.excepciones;

public class ExcMultiplicacionImposible extends Exception {
    
    private int ancho;
    private int largo;
    
    public ExcMultiplicacionImposible(int ancho, int largo) {
        
        this.ancho = ancho;
        this.largo = largo;
        
    }
    
    public int getAncho() {
        
        return this.ancho;
        
    }
    
    public int getLargo() {
        
        return this.largo;
        
    }
    
}

package jmatrix.excepciones;

public class ExcDimensionImposible extends Exception {
    
    private int ancho;
    private int largo;
    
    public ExcDimensionImposible(int ancho, int largo) {
        
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

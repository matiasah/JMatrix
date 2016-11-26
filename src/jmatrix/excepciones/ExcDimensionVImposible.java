package jmatrix.excepciones;

public class ExcDimensionVImposible extends Exception {
    
    private int ancho;
    
    public ExcDimensionVImposible(int ancho) {
    
        this.ancho = ancho;
        
    }
    
    public int getAncho() {
        
        return this.ancho;
        
    }
    
}

package jmatrix;

public class ListaModeloMatriz extends javax.swing.AbstractListModel<String> {
    
    private java.util.ArrayList<Matriz> matrices;
    
    public ListaModeloMatriz(java.util.ArrayList<Matriz> matrices) {
        
        this.matrices = matrices;
        
    }
    
    public int getSize() {
        
        return matrices.size();
    
    }
    
    public String getElementAt(int i) {
        
        return "Matriz " + (i + 1);
    
    }
    
}

package jmatrix;

public class ListaModeloMatriz extends javax.swing.AbstractListModel<String> {
    
    private java.util.ArrayList<Matriz> matrices;
    
    /**
     * Constructor de la clase
     * @param matrices la lista de elementos que contiene las matrices
     */
    public ListaModeloMatriz(java.util.ArrayList<Matriz> matrices) {
        
        this.matrices = matrices;
        
    }
    
    /**
     * Obtener el número de elementos que contiene la lista de matrices
     * @return el número de elementos
     */
    public int getSize() {
        
        return matrices.size();
    
    }
    
    /**
     * Obtener elemento en un índice específico
     * @param i el índice específico
     * @return el elemento en el indice específico
     */
    public String getElementAt(int i) {
        
        return "Matriz " + (i + 1);
    
    }
    
}

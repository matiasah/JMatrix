package jmatrix;

public class JMatrix {
    
    public static void main(String[] args) {
        
        Matriz A = new Matriz( new double [][]
            {
                {1, 1, 1, 1, 1},
                {1, 3, 3, 3, 3},
                {1, 3, 5, 5, 5},
                {1, 3, 5, 7, 7},
                {1, 3, 5, 7, 9},
            }
        );
        
        System.out.println(A.determinante());
    }
    
}

package jmatrix;

public class JMatrix {
    
    public static void main(String[] args) {
        Matriz A = new Matriz( new double [][]
            {
                {2, 0, 1},
                {3, 0, 0},
                {5, 1, 1}
            }
        );
        
        System.out.println(A.Escalonar());
    }
    
}

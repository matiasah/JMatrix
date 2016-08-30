package jmatrix;

import java.util.ArrayList;

public class Matriz {
    private Vector [] Vector;
    private ArrayList<String> Instrucciones = new ArrayList<String>();
    
    public Matriz(int Ancho, int Largo){
        Vector = new Vector[Ancho];
        
        for (int x = 0; x < Ancho; x++){
            Vector[x] = new Vector(Largo);
        }
    }
    
    public Matriz(double [][] M){
        Vector = new Vector[M.length];
        
        if (M.length > 0){
            int Largo = M[0].length;

            for (int x = 0; x < Vector.length; x++){
                if (M[x].length == Largo){
                    Vector[x] = new Vector(M[x]);
                }else{
                    // Entregar un error
                    Vector = null;
                    break;
                }
            }
        }
    }
    
    @Override public String toString(){
        String Imprimible = "";
        
        for (int x = 0; x < Vector.length; x++){
            Imprimible += "\n" + Vector[x];
        }
        
        return Imprimible.substring(1);
    }
    
    @Override public boolean equals(Object O){
        Matriz M = (Matriz) O;
        
        if (M != null){
            if (M.Ancho() != Ancho() | M.Largo() != Largo()){
                return false;
            }
            
            for (int x = 0, A = Ancho(); x < A; x++){
                if (Obtener(x).equals(M.Obtener(x))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static Matriz Identidad(int Tamaño){
        Matriz Ident = new Matriz(Tamaño, Tamaño);
        
        for (int x = 0; x < Tamaño; x++){
            Ident.Establecer(x, x, 1);
        }
        
        return Ident;
    }
    
    public Matriz Clonar(){
        Matriz Salida = new Matriz(Ancho(), Largo());
        
        for (int x = 0; x < Ancho(); x++){
            Salida.Establecer(x, Vector[x].Clonar());
        }
        
        return Salida;
    }
    
    public boolean Validar(){
        if (Vector != null & Vector.length > 0){
            return true;
        }
        return false;
    }
    
    public int Ancho(){
        return Vector.length;
    }
    
    public int Largo(){
        if (Validar()){
            return Vector[0].Ancho();
        }
        return 0;
    }
    
    public void Establecer(int Indice, Vector Vec){
        if (Indice >= 0 & Indice < Vector.length){
            Vector[Indice] = Vec;
        }
    }
    
    public void Establecer(int x, int y, double Valor){
        if (x >= 0 & x < Vector.length){
            Vector[x].Establecer(y, Valor);
        }
    }
    
    public void Sumar(int Indice, Vector Vec){
        if (Indice >= 0 & Indice < Vector.length){
            Vector[Indice] = Vector[Indice].Sumar(Vec);
        }
    }
    
    public Vector Obtener(int Indice){
        if (Indice >= 0 & Indice < Vector.length){
            return Vector[Indice];
        }
        return new Vector(Vector.length);
    }
    
    public double Obtener(int x, int y){
        if (x >= 0 & x < Vector.length){
            return Vector[x].Obtener(y);
        }
        return 0;
    }
    
    public Matriz Sumar(Matriz Mat){
        Matriz Salida = new Matriz(
            Math.max(Mat.Ancho(), Ancho()),
            Math.max(Mat.Largo(), Largo())
        );
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Establecer(x,
                // Lamentablemente no se pueden utilizar operadores matemáticos en objetos
                // Así que es necesario usar Obtener(x).Sumar
                Obtener(x).Sumar( Mat.Obtener(x) )
            );
        }
        
        return Salida;
    }
    
    public Matriz Restar(Matriz Mat){
        Matriz Salida = new Matriz(
            Math.max(Mat.Ancho(), Ancho()),
            Math.max(Mat.Largo(), Largo())
        );
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Establecer(x,
                // Mismo caso, no se pueden utilizar operadores matemáticos en objetos
                Obtener(x).Restar( Mat.Obtener(x) )
            );
        }
        
        return Salida;
    }
    
    public Matriz Multiplicar(Matriz Mat){
        Matriz Salida = new Matriz(Ancho(), Largo());
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Vector Vec = new Vector(Salida.Largo());
            
            for (int y = 0; y < A; y++){
                
                for (int i = 0; i < A; i++){
                    
                    Vec.Sumar(y, Obtener(x).Obtener(i) * Mat.Obtener(i).Obtener(y));
                    
                }
            }
            
            Salida.Establecer(x, Vec);
        }
        
        return Salida;
    }
    
    public Matriz Multiplicar(double Numero){
        Matriz Salida = new Matriz(Ancho(), Largo());
        
        for (int x = 0, A = Salida.Ancho(); x < A; x++){
            Salida.Establecer(x, Obtener(x).Multiplicar(Numero));
        }
        
        return Salida;
    }
    
    public Matriz Elevar(int Exponente){
        Matriz Salida = this;
        
        for (int x = 0; x < Exponente - 1; x++){
            Salida = Salida.Multiplicar(this);
        }
        
        return Salida;
    }
    
    public Matriz Transpuesta(){
        Matriz Transpuesta = new Matriz(Largo(), Ancho());
        
        for (int x = 0, A = Ancho(); x < A; x++){
            for (int y = 0, L = Largo(); y < A; y++){
                Transpuesta.Establecer(y, x, Obtener(x, y));
            }
        }
        
        return Transpuesta;
    }
    
    public void AgregarInstruccion(String Instruccion){
        Instrucciones.add(Instruccion);
    }
    
    public Object [] ObtenerInstrucciones(){
        return Instrucciones.toArray();
    }
    
    public Matriz Inversa(){
        Matriz Escalonada = Clonar();
        Matriz Inversa = Matriz.Identidad(Ancho());
        
        for (boolean Repetir = true; Repetir;){
            for (int Ancho = Escalonada.Ancho(), IndiceNulo = Ancho; IndiceNulo > 0 ; IndiceNulo--){
                if (Escalonada.Obtener(IndiceNulo, IndiceNulo) == 0){
                    for (int i = Ancho; i > 0; i--){
                        if (Escalonada.Obtener(i, IndiceNulo) != 0){
                            Inversa.AgregarInstruccion("L" + (IndiceNulo + 1) + (i + 1));
                            Escalonada.AgregarInstruccion("L" + (IndiceNulo + 1) + (i + 1));

                            Vector Vec = Escalonada.Obtener(i);
                            Escalonada.Establecer(i, Escalonada.Obtener(IndiceNulo));
                            Escalonada.Establecer(IndiceNulo, Vec);

                            Vector VecInverso = Inversa.Obtener(i);
                            Inversa.Establecer(i, Inversa.Obtener(IndiceNulo));
                            Inversa.Establecer(IndiceNulo, VecInverso);
                            break;
                        }
                    }
                }
            }

            for (int Diagonal = 0, Ancho = Escalonada.Ancho(); Diagonal < Ancho; Diagonal++){
                // Verificar que diagonalmente los valores son 1
                // Si son 0, no se puede hacer nada
                // Si son diferentes de 1 se debe dividir la fila por tal numero

                double ValorDiagonal = Escalonada.Obtener(Diagonal, Diagonal);
                if (ValorDiagonal != 0 & ValorDiagonal != 1){
                    Inversa.AgregarInstruccion("L" + (Diagonal + 1) + "(1/" + ValorDiagonal + ")");
                    Escalonada.AgregarInstruccion("L" + (Diagonal + 1) + "(1/" + ValorDiagonal + ")");
                    
                    double ValorInverso = 1 / ValorDiagonal;
                    
                    Inversa.Establecer(Diagonal, Inversa.Obtener(Diagonal).Multiplicar(ValorInverso));
                    Escalonada.Establecer(Diagonal, Escalonada.Obtener(Diagonal).Multiplicar(ValorInverso));
                }
            }

            for (int x = 0, Ancho = Escalonada.Ancho(); x < Ancho; x++){
                for (int y = 0; y < Ancho; y++){
                    if (Escalonada.Obtener(x, y) != 0 & Escalonada.Obtener(y, y) == 1 & x != y){
                        double Multiplo = Escalonada.Obtener(x, y);

                        Inversa.AgregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");
                        Escalonada.AgregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");

                        Inversa.Establecer(x, Inversa.Obtener(x).Restar(Inversa.Obtener(y).Multiplicar(Multiplo)));
                        Escalonada.Establecer(x, Escalonada.Obtener(x).Restar(Escalonada.Obtener(y).Multiplicar(Multiplo)));
                    }
                }
            }
            
            Repetir = false;
            
            for (int Diagonal = 0, Ancho = Escalonada.Ancho(); Diagonal < Ancho; Diagonal++){
                double ValorDiagonal = Escalonada.Obtener(Diagonal, Diagonal);
                
                if (ValorDiagonal != 0 & ValorDiagonal != 1){
                    Repetir = true;
                    break;
                }
            }
        }
        
        return Inversa;
    }
    
    public Matriz Escalonar(){
        Matriz Escalonada = Clonar();
        
        for (boolean Repetir = true; Repetir;){
            for (int Ancho = Escalonada.Ancho(), IndiceNulo = Ancho; IndiceNulo > 0 ; IndiceNulo--){
                if (Escalonada.Obtener(IndiceNulo, IndiceNulo) == 0){
                    for (int i = Ancho; i > 0; i--){
                        if (Escalonada.Obtener(i, IndiceNulo) != 0){
                            Escalonada.AgregarInstruccion("L" + (IndiceNulo + 1) + (i + 1));

                            Vector Vec = Escalonada.Obtener(i);
                            Escalonada.Establecer(i, Escalonada.Obtener(IndiceNulo));
                            Escalonada.Establecer(IndiceNulo, Vec);
                            break;
                        }
                    }
                }
            }

            for (int Diagonal = 0, Ancho = Escalonada.Ancho(); Diagonal < Ancho; Diagonal++){
                // Verificar que diagonalmente los valores son 1
                // Si son 0, no se puede hacer nada
                // Si son diferentes de 1 se debe dividir la fila por tal numero

                double ValorDiagonal = Escalonada.Obtener(Diagonal, Diagonal);
                if (ValorDiagonal != 0 & ValorDiagonal != 1){
                    Escalonada.AgregarInstruccion("L" + (Diagonal + 1) + "(1/" + ValorDiagonal + ")");
                    Escalonada.Establecer(Diagonal, Escalonada.Obtener(Diagonal).Multiplicar(1 / ValorDiagonal));
                }
            }

            for (int x = 0, Ancho = Escalonada.Ancho(); x < Ancho; x++){
                for (int y = 0; y < Ancho; y++){
                    if (Escalonada.Obtener(x, y) != 0 & Escalonada.Obtener(y, y) == 1 & x != y){
                        double Multiplo = Escalonada.Obtener(x, y);

                        Escalonada.AgregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");
                        Escalonada.Establecer(x, Escalonada.Obtener(x).Restar(Escalonada.Obtener(y).Multiplicar(Multiplo)));
                    }
                }
            }
            
            Repetir = false;
            
            for (int Diagonal = 0, Ancho = Escalonada.Ancho(); Diagonal < Ancho; Diagonal++){
                double ValorDiagonal = Escalonada.Obtener(Diagonal, Diagonal);
                
                if (ValorDiagonal != 0 & ValorDiagonal != 1){
                    Repetir = true;
                    break;
                }
            }
        }
        
        return Escalonada;
    }
    
    public Matriz Opuesta(){
        Matriz Opuesta = new Matriz(Ancho(), Largo());
        
        for (int x = 0, A = Ancho(); x < A; x++){
            Opuesta.Establecer(x, Obtener(x).Opuesta());
        }
        
        return Opuesta;
    }
    
    public boolean EsIdempotente(){
        return this.equals(this.Multiplicar(this));
    }
    
    public boolean EsInvolutiva(){
        return this.equals(this.Inversa());
    }
    
    public boolean EsSimetrica(){
        return this.Transpuesta().equals(this);
    }
    
    public boolean EsAntisimetrica(){
        return this.Transpuesta().equals(this.Opuesta());
    }
    
    public boolean EsOrtogonal(){
        return this.Transpuesta().equals(this.Inversa());
    }
}

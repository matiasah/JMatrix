package jmatrix;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.RandomAccessFile;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;

public class Matriz {
    
    private Vector [] vector;
    private ArrayList<String> instrucciones;
    
    public Matriz(int ancho, int largo){
        
        this.vector = new Vector[ancho];
        this.instrucciones = new ArrayList<String>();
        
        for (int x = 0; x < ancho; x++){
            
            this.vector[x] = new Vector(largo);
            
        }
        
    }
    
    public Matriz(double [][] matriz){
        
        this.vector = new Vector[matriz.length];
        this.instrucciones = new ArrayList<String>();
        
        if (matriz.length > 0){
            
            int Largo = matriz[0].length;

            for (int x = 0; x < this.vector.length; x++){
                
                if (matriz[x].length == Largo){
                    
                    this.vector[x] = new Vector(matriz[x]);
                    
                }else{
                    
                    // Entregar un error (por hacer)
                    this.vector = null;
                    break;
                    
                }
                
            }
            
        }
    }
    
    public Matriz(JTable tabla) {
        
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int l = modelo.getRowCount();
        int a = modelo.getColumnCount();
        
        this.vector = new Vector[l];
        
        for (int x = 0; x < l; x++){
            
            Vector v = new Vector(a);
            this.vector[x] = v;
            
            for (int y = 0; y < a; y++) {
                
                Double valor = (Double) modelo.getValueAt(x, y);
                
                if (valor == null) {
                    
                    valor = new Double(0);
                    
                }
                
                v.establecer(y, valor);
                
            }
            
        }
        
    }
    
    public Matriz(int tamaño){
        
        this.vector = new Vector[tamaño];
        this.instrucciones = new ArrayList<String>();
        
        for (int x = 0; x < tamaño; x++){
            
            this.vector[x] = new Vector(tamaño);
            this.establecer(x, x, 1);
            
        }
        
    }
    
    public Matriz(RandomAccessFile archivo) {
        
        try {
            
            byte [] contenido = new byte[ (int) archivo.length()];
            
            archivo.read(contenido);
            archivo.close();
            
            JSONArray matriz = new JSONArray( new String(contenido) );
            
            int a = matriz.length();
            
            this.vector = new Vector[a];
            
            for (int x = 0; x < a; x++) {
                
                JSONArray arreglo = matriz.optJSONArray(x);
                
                if (arreglo != null) {
                
                    int l = arreglo.length();
                    Vector vec = new Vector(l);

                    this.vector[x] = vec;

                    for (int y = 0; y < l; y++) {

                        vec.establecer(y, arreglo.optDouble(y));

                    }
                    
                }
                
            }
        
        } catch (IOException | JSONException e) {
            
        }
        
    }
    
    @Override public String toString(){
        
        String str = "";
        
        for (int x = 0; x < this.vector.length; x++){
            
            str += "\n" + this.vector[x];
            
        }
        
        if (str.length() > 0){
            
            return str.substring(1);
            
        }
        
        return "";
        
    }
    
    @Override public boolean equals(Object objeto){
        
        if (objeto instanceof Matriz){
            
            Matriz matriz = (Matriz) objeto;
            
            if (matriz.ancho() != this.ancho() | matriz.largo() != this.largo()){
                
                return false;
                
            }
            
            for (int x = 0, A = this.ancho(); x < A; x++){
                
                if (this.obtener(x).equals(matriz.obtener(x))){
                    
                    return false;
                    
                }
                
            }
            
            return true;
            
        }
        
        return false;
        
    }
    
    public Matriz clonar(){
        
        Matriz copia = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0; x < this.ancho(); x++){
            
            copia.establecer(x, this.vector[x].clonar());
            
        }
        
        return copia;
        
    }
    
    public boolean validar(){
        
        if (this.vector != null & this.vector.length > 0){
            
            return true;
            
        }
        
        return false;
    }
    
    public int ancho(){
        
        return this.vector.length;
        
    }
    
    public int largo(){
        
        if (this.validar()){
            
            return this.vector[0].ancho();
            
        }
        
        return 0;
    }
    
    public void establecer(int indice, Vector vec){
        
        if (indice >= 0 & indice < this.vector.length){
            
            this.vector[indice] = vec;
            
        }
        
    }
    
    public void establecer(int x, int y, double valor){
        
        if (x >= 0 & x < this.vector.length){
            
            this.vector[x].establecer(y, valor);
            
        }
        
    }
    
    public void sumar(int indice, Vector vec){
        
        if (indice >= 0 & indice < this.vector.length){
            
            this.vector[indice] = this.vector[indice].sumar(vec);
            
        }
        
    }
    
    public Vector obtener(int indice){
        
        if (indice >= 0 & indice < this.vector.length){
            
            return this.vector[indice];
            
        }
        
        return new Vector(this.vector.length);
        
    }
    
    public double obtener(int x, int y){
        
        if (x >= 0 & x < this.vector.length){
            
            return this.vector[x].obtener(y);
            
        }
        
        return 0;
        
    }
    
    public Matriz sumar(Matriz matriz){
        
        Matriz Salida = new Matriz(
            Math.max(matriz.ancho(), this.ancho()),
            Math.max(matriz.largo(), this.largo())
        );
        
        for (int x = 0, A = Salida.ancho(); x < A; x++){
            
            Salida.establecer(x,
                // Lamentablemente no se pueden utilizar operadores matemáticos en objetos
                // Así que es necesario usar Obtener(x).Sumar
                this.obtener(x).sumar( matriz.obtener(x) )
            );
            
        }
        
        return Salida;
        
    }
    
    public Matriz restar(Matriz matriz){
        
        Matriz resta = new Matriz(
            Math.max(matriz.ancho(), this.ancho()),
            Math.max(matriz.largo(), this.largo())
        );
        
        for (int x = 0, A = resta.ancho(); x < A; x++){
            
            resta.establecer(x,
                // Mismo caso, no se pueden utilizar operadores matemáticos en objetos
                this.obtener(x).restar( matriz.obtener(x) )
            );
            
        }
        
        return resta;
        
    }
    
    public Matriz multiplicar(Matriz matriz){
        
        Matriz multiplicacion = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0, A = multiplicacion.ancho(); x < A; x++){
            
            Vector vector = new Vector(multiplicacion.largo());
            
            for (int y = 0; y < A; y++){
                
                for (int i = 0; i < A; i++){
                    
                    vector.sumar(y, this.obtener(x).obtener(i) * matriz.obtener(i).obtener(y));
                    
                }
            }
            
            multiplicacion.establecer(x, vector);
            
        }
        
        return multiplicacion;
        
    }
    
    public Matriz multiplicar(double numero){
        
        Matriz multiplicacion = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0, A = multiplicacion.ancho(); x < A; x++){
            
            multiplicacion.establecer(x, this.obtener(x).multiplicar(numero));
            
        }
        
        return multiplicacion;
        
    }
    
    public Matriz elevar(int exponente){
        
        Matriz operacion = this;
        
        for (int x = 0; x < exponente - 1; x++){
            
            operacion = operacion.multiplicar(this);
            
        }
        
        return operacion;
        
    }
    
    public Matriz transpuesta(){
        
        Matriz transpuesta = new Matriz(this.largo(), this.ancho());
        
        for (int x = 0, A = this.ancho(), L = this.largo(); x < A; x++){
            
            for (int y = 0; y < A; y++){
                
                transpuesta.establecer(y, x, this.obtener(x, y));
                
            }
            
        }
        
        return transpuesta;
        
    }
    
    private void agregarInstruccion(String Instruccion){
        
        this.instrucciones.add(Instruccion);
        
    }
    
    public Object [] obtenerInstrucciones(){
        
        return this.instrucciones.toArray();
        
    }
    
    public Matriz inversa(){
        
        Matriz escalonada = this.clonar();
        Matriz inversa = new Matriz(this.ancho());
        
        for (boolean repetir = true; repetir;){
            
            for (int ancho = escalonada.ancho(), indiceNulo = ancho; indiceNulo > 0 ; indiceNulo--){
                
                if (escalonada.obtener(indiceNulo, indiceNulo) == 0){
                    
                    for (int i = ancho; i > 0; i--){
                        
                        if (escalonada.obtener(i, indiceNulo) != 0){
                            
                            inversa.agregarInstruccion("L" + (indiceNulo + 1) + (i + 1));
                            escalonada.agregarInstruccion("L" + (indiceNulo + 1) + (i + 1));

                            Vector vector = escalonada.obtener(i);
                            escalonada.establecer(i, escalonada.obtener(indiceNulo));
                            escalonada.establecer(indiceNulo, vector);

                            Vector vectorInverso = inversa.obtener(i);
                            inversa.establecer(i, inversa.obtener(indiceNulo));
                            inversa.establecer(indiceNulo, vectorInverso);
                            break;
                            
                        }
                        
                    }
                    
                }
                
            }

            for (int Diagonal = 0, Ancho = escalonada.ancho(); Diagonal < Ancho; Diagonal++){
                // Verificar que diagonalmente los valores son 1
                // Si son 0, no se puede hacer nada
                // Si son diferentes de 1 se debe dividir la fila por tal numero

                double valorDiagonal = escalonada.obtener(Diagonal, Diagonal);
                
                if (valorDiagonal != 0 & valorDiagonal != 1){
                    
                    inversa.agregarInstruccion("L" + (Diagonal + 1) + "(1/" + valorDiagonal + ")");
                    escalonada.agregarInstruccion("L" + (Diagonal + 1) + "(1/" + valorDiagonal + ")");
                    
                    double valorInverso = 1 / valorDiagonal;
                    
                    inversa.establecer(Diagonal, inversa.obtener(Diagonal).multiplicar(valorInverso));
                    escalonada.establecer(Diagonal, escalonada.obtener(Diagonal).multiplicar(valorInverso));
                    
                }
                
            }

            for (int x = 0, ancho = escalonada.ancho(); x < ancho; x++){
                
                for (int y = 0; y < ancho; y++){
                    
                    if (escalonada.obtener(x, y) != 0 & escalonada.obtener(y, y) == 1 & x != y){
                        
                        double Multiplo = escalonada.obtener(x, y);

                        inversa.agregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");
                        escalonada.agregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");

                        inversa.establecer(x, inversa.obtener(x).restar(inversa.obtener(y).multiplicar(Multiplo)));
                        escalonada.establecer(x, escalonada.obtener(x).restar(escalonada.obtener(y).multiplicar(Multiplo)));
                        
                    }
                    
                }
                
            }
            
            repetir = false;
            
            for (int Diagonal = 0, Ancho = escalonada.ancho(); Diagonal < Ancho; Diagonal++){
                
                double ValorDiagonal = escalonada.obtener(Diagonal, Diagonal);
                
                if (ValorDiagonal != 0 & ValorDiagonal != 1){
                    
                    repetir = true;
                    break;
                    
                }
                
            }
            
        }
        
        return inversa;
    }
    
    public Matriz escalonar(){
        
        Matriz escalonada = this.clonar();
        
        for (boolean Repetir = true; Repetir;){
            
            for (int Ancho = escalonada.ancho(), indiceNulo = Ancho; indiceNulo > 0 ; indiceNulo--){
                
                if (escalonada.obtener(indiceNulo, indiceNulo) == 0){
                    
                    for (int i = Ancho; i > 0; i--){
                        
                        if (escalonada.obtener(i, indiceNulo) != 0){
                            
                            escalonada.agregarInstruccion("L" + (indiceNulo + 1) + (i + 1));

                            Vector vector = escalonada.obtener(i);
                            escalonada.establecer(i, escalonada.obtener(indiceNulo));
                            escalonada.establecer(indiceNulo, vector);
                            break;
                            
                        }
                        
                    }
                    
                }
                
            }

            for (int diagonal = 0, Ancho = escalonada.ancho(); diagonal < Ancho; diagonal++){
                // Verificar que diagonalmente (en la matriz) los valores son 1
                // Si son 0, no se puede hacer nada
                // Si son diferentes de 1 se debe dividir la fila por tal numero

                double valorDiagonal = escalonada.obtener(diagonal, diagonal);
                
                if (valorDiagonal != 0 & valorDiagonal != 1){
                    
                    escalonada.agregarInstruccion("L" + (diagonal + 1) + "(1/" + valorDiagonal + ")");
                    escalonada.establecer(diagonal, escalonada.obtener(diagonal).multiplicar(1 / valorDiagonal));
                    
                }
                
            }

            for (int x = 0, ancho = escalonada.ancho(); x < ancho; x++){
                
                for (int y = 0; y < ancho; y++){
                    
                    if (escalonada.obtener(x, y) != 0 & escalonada.obtener(y, y) == 1 & x != y){
                        
                        double Multiplo = escalonada.obtener(x, y);

                        escalonada.agregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");
                        escalonada.establecer(x, escalonada.obtener(x).restar(escalonada.obtener(y).multiplicar(Multiplo)));
                        
                    }
                    
                }
                
            }
            
            Repetir = false;
            
            for (int Diagonal = 0, Ancho = escalonada.ancho(); Diagonal < Ancho; Diagonal++){
                
                double ValorDiagonal = escalonada.obtener(Diagonal, Diagonal);
                
                if (ValorDiagonal != 0 & ValorDiagonal != 1){
                    
                    Repetir = true;
                    break;
                    
                }
                
            }
            
        }
        
        return escalonada;
        
    }
    
    public Matriz opuesta(){
        
        Matriz opuesta = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0, A = this.ancho(); x < A; x++){
            
            opuesta.establecer(x, this.obtener(x).opuesto());
            
        }
        
        return opuesta;
        
    }
    
    public double determinante(){
        
        if (this.ancho() != this.largo()){
            
            return 0;
            
        }else if (this.ancho() == 2){
            
            return this.obtener(0, 0) * this.obtener(1, 1) - this.obtener(0, 1) * this.obtener(1, 0);
            
        }
        
        double determinante = 0;
        byte signo = 1;
        
        for (int i = 0, A = this.ancho(), L = this.largo(); i < A; i++){
            
            Matriz matriz = new Matriz(A - 1, L - 1);
            
            for (int y = 0, n = 0; y < L; y++){
                
                if (y != i){
                    
                    for (int x = 1; x < A; x++) {
                        
                        matriz.establecer(x - 1, n, this.obtener(y, x));
                        
                    }
                    
                    n++;
                    
                }
                
            }
            
            // La determinante de una matriz de dimension mayor que 2x2 es un número líder multiplicado por la determinante de una sub-matriz
            determinante += signo * this.obtener(i, 0) * matriz.determinante();
            signo *= -1;
            
        }
        
        return determinante;
        
    }
    
    public boolean esIdempotente(){
        
        return this.equals(this.multiplicar(this));
        
    }
    
    public boolean esInvolutiva(){
        
        return this.equals(this.inversa());
        
    }
    
    public boolean esSimetrica(){
        
        return this.transpuesta().equals(this);
        
    }
    
    public boolean esAntisimetrica(){
        
        return this.transpuesta().equals(this.opuesta());
        
    }
    
    public boolean esOrtogonal(){
        
        return this.transpuesta().equals(this.inversa());
        
    }
    
    public void insertar(JTable tabla) {
        
        if (this.validar()) {
        
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tabla.getModel();

            modelo.setColumnCount(this.vector[0].ancho());
            modelo.setRowCount(this.vector.length);
            
            for (int x = 0; x < this.vector.length; x++) {
                
                Vector v = this.vector[x];
                
                for (int y = 0, a = v.ancho(); y < a; y++) {
                    
                    modelo.setValueAt(v.obtener(y), x, y);
                    
                }
                
            }
            
        }
        
    }
    
    public void guardar(RandomAccessFile archivo) {
        
        ArrayList<ArrayList<Double>> lista = this.obtenerLista();
        
        JSONArray arreglo = new JSONArray(lista);
        
        try {
            
            archivo.writeBytes( arreglo.toString() );
            archivo.close();
        
        } catch (IOException e) {
            
            
        }
        
    }
    
    public ArrayList<ArrayList<Double>> obtenerLista() {
        
        ArrayList<ArrayList<Double>> lista = new ArrayList<ArrayList<Double>>();
        
        for (int x = 0, a = this.ancho(); x < a; x++) {
            
            ArrayList<Double> subLista = new ArrayList<Double>();
            Double [] vector = this.vector[x].obtener();
            
            for (int y = 0; y < vector.length; y++) {
                
                subLista.add(vector[y]);
                
            }
            
            lista.add(subLista);
            
        }
        
        return lista;
        
    }
    
    public double [][] obtener() {
        
        int a = this.ancho();
        int l = this.largo();
        double [][] matriz = new double[a][l];
        
        for (int x = 0; x < a; x++) {
            
            for (int y = 0; y < l; y++) {
                
                matriz[x][y] = vector[x].obtener(y);
                
            }
            
        }
        
        return matriz;
        
    }
    
}

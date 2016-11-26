package jmatrix;

import jmatrix.excepciones.*;

import java.util.ArrayList;

import java.io.RandomAccessFile;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 
 * @author matias
 */
public class Matriz {
    
    private Vector [] vector;
    private ArrayList<String> instrucciones;
    
    /**
     * Constructor principal de la clase, genera una matriz nula de dimensiones ancho x largo
     * @param ancho ancho de la matriz que se desea crear
     * @param largo largo de la matriz que se desea crear
     */
    public Matriz(int ancho, int largo) throws ExcDimensionImposible {
        
        if (ancho <= 0 | largo <= 0) {
            
            throw new ExcDimensionImposible(ancho, largo);
            
        }
        
        this.vector = new Vector[ancho];
        this.instrucciones = new ArrayList<String>();
        
        for (int x = 0; x < ancho; x++){
            
            this.vector[x] = new Vector(largo);
            
        }
        
    }
    
    /**
     * Constructor de la clase, copia los datos de una matriz de clase double dentro de la matriz creada
     * @param matriz la matriz que se desea copiar
     */
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
    
    /**
     * Constructor de la clase, copia los datos de un objeto clase JTable
     * @param tabla la tabla que se desea copiar
     */
    public Matriz(JTable tabla) {
        
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int l = modelo.getRowCount();
        int a = modelo.getColumnCount();
        
        this.vector = new Vector[l];
        this.instrucciones = new ArrayList<String>();
        
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
    
    /**
     * Constructor de la clase, genera una matriz diagonal cuadrada
     * @param tamaño la dimension de la matriz cuadrada: tamaño x tamaño
     */
    public Matriz(int tamaño) throws ExcDimensionImposible {
        
        if ( tamaño <= 0 ) {
            
            throw new ExcDimensionImposible( tamaño, tamaño );
            
        }
        
        this.vector = new Vector[tamaño];
        this.instrucciones = new ArrayList<String>();
        
        for (int x = 0; x < tamaño; x++){
            
            this.vector[x] = new Vector(tamaño);
            this.establecer(x, x, 1);
            
        }
        
    }
    
    /**
     * Constructor de la clase, lee un archivo y genera los datos de la matriz a partir del contenido
     * @param archivo archivo que se desea leer
     */
    public Matriz(RandomAccessFile archivo) {
        
        try {
            
            byte [] contenido = new byte[ (int) archivo.length()];
            
            archivo.read(contenido);
            archivo.close();
            
            // Transformar matriz JSON a Matriz
            JSONArray matriz = new JSONArray( new String(contenido) );
            
            // Ancho del arreglo JSON
            int a = matriz.length();
            
            this.vector = new Vector[a];
            
            for (int x = 0; x < a; x++) {
                
                // Sub-arreglo JSON
                JSONArray arreglo = matriz.optJSONArray(x);
                
                if (arreglo != null) {
                
                    // Ancho del subarreglo JSON
                    int l = arreglo.length();
                    Vector vec = new Vector(l);

                    // Transformar arreglo JSON a Vector
                    this.vector[x] = vec;

                    for (int y = 0; y < l; y++) {

                        vec.establecer(y, arreglo.optDouble(y));

                    }
                    
                }
                
            }
            
            this.instrucciones = new ArrayList<String>();
        
        } catch (IOException | JSONException e) {
            
            // Si hay error, la matriz es inválida
            
        }
        
    }
    
    /**
     * Función estática, genera matrices copiando las dimensiones de una matriz especificada
     * @param matriz la matriz de la cual se copian sus dimensiones
     * @return matriz identidad
     */
    public static Matriz identidad(Matriz matriz) throws ExcDimensionImposible {
        
        Matriz identidad = new Matriz(matriz.ancho(), matriz.largo());
        
        for (int x = 0; x < Math.min(identidad.ancho(), identidad.largo()); x++) {
            
            identidad.establecer(x, x, 1);
            
        }
        
        return identidad;
        
    }
    
    /**
     * @return los valores numéricos que contiene la matriz
     */
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
    
    /**
     * Compara los números que contienen dos matricess
     * @param objeto objeto de clase matriz que se desea comparar
     * @return true si contienen los mismos números, caso contrario false
     */
    @Override public boolean equals(Object objeto){
        
        if (objeto instanceof Matriz){
            
            Matriz matriz = (Matriz) objeto;
            
            if (matriz.ancho() != this.ancho() | matriz.largo() != this.largo()){
                
                return false;
                
            }
            
            for (int x = 0, A = this.ancho(); x < A; x++){
                
                if ( !this.obtener(x).equals(matriz.obtener(x)) ){
                    
                    return false;
                    
                }
                
            }
            
            return true;
            
        }
        
        return false;
        
    }
    
    /**
     * Copia los datos numericos de la matriz
     * @return matriz copiada
     */
    public Matriz clonar() throws ExcDimensionImposible {
        
        Matriz copia = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0; x < this.ancho(); x++){
            
            copia.establecer(x, this.vector[x].clonar());
            
        }
        
        return copia;
        
    }
    
    /**
     * Verifica que la matriz contiene datos
     * @return true si contiene datos, caso contrario false
     */
    public boolean validar(){
        
        try {
        
            // Si el vector está vacio, la matriz es inválida
            
            if (this.vector.length > 0){

                return true;

            }
            
        } catch (java.lang.NullPointerException e) {
            
            // Si el vector no existe, Java entregará un error y por lo tanto la matriz será inválida
            
            return false;
            
        }
        
        return false;
    }
    
    /**
     * @return la cantidad de vectores que se están utilizando
     */
    public int ancho(){
        
        return this.vector.length;
        
    }
    
    /**
     * @return la longitud de los vectores que se están utilizando
     */
    public int largo(){
        
        if (this.validar()){
            
            return this.vector[0].ancho();
            
        }
        
        return 0;
    }
    
    /**
     * Reemplaza un vector por otro en el índice especificado
     * @param indice donde reemplazar el vector
     * @param vec el vector que se desea insertar
     */
    public void establecer(int indice, Vector vec){
        
        if (indice >= 0 & indice < this.vector.length){
            
            this.vector[indice] = vec;
            
        }
        
    }
    
    /**
     * Reemplaza un valor numérico en la matriz
     * @param y     coordenada 'y' donde se desea reemplazar el valor
     * @param x     coordenada 'x' donde se desea reemplazar el valor
     * @param valor valor que se desea insertar
     */
    public void establecer(int y, int x, double valor) {
        
        if (y >= 0 & y < this.vector.length){
            
            this.vector[y].establecer(x, valor);
            
        }
        
    }
    
    /**
     * Suma un vector con otro vector perteneciente a la matriz, el resultado es insertado en el mísmo indice
     * @param indice    posicion del vector donde se desea sumar
     * @param vec       vector que se desea sumar en la matriz
     */
    public void sumar(int indice, Vector vec){
        
        if (indice >= 0 & indice < this.vector.length){
            
            this.vector[indice] = this.vector[indice].sumar(vec);
            
        }
        
    }
    
    /**
     * Retorna un vector perteneciente a la matriz
     * @param indice posicion del vector que se desea obtener
     * @return el vector que se desea obtener
     */
    public Vector obtener(int indice){
        
        if (indice >= 0 & indice < this.vector.length){
            
            return this.vector[indice];
            
        }
        
        return new Vector(this.vector.length);
        
    }
    
    /**
     * Retorna un valor numérico contenido en la matriz
     * @param y coordenada 'y' donde se contiene el valor numérico en la matriz
     * @param x coordenada 'x' donde se contiene el valor númerico en la matriz
     * @return  el valor numérico que se desea obtener
     */
    public double obtener(int y, int x){
        
        if (y >= 0 & y < this.vector.length){
            
            return this.vector[y].obtener(x);
            
        }
        
        return 0;
        
    }
    
    /**
     * Suma los valores numéricos de dos matrices
     * @param matriz matriz con la cual se desea sumar la matriz actual
     * @return la matriz que contiene la suma de ambas matrices
     */
    public Matriz sumar(Matriz matriz) throws ExcDimensionImposible {
        
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
    
    /**
     * Resta los valores numéricos de dos matrices
     * @param matriz matriz con la cual se desea restar la matriz actual
     * @return la matriz que contiene la resta de ambas matrices
     */
    public Matriz restar(Matriz matriz) throws ExcDimensionImposible {
        
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
    
    /**
     * Multiplica los valores numéricos de ambas matrices
     * @param matriz matriz con la cual se desea multiplicar la matriz actual
     * @return la matriz que contiene la multiplicacion de ambas matrices
     */
    public Matriz multiplicar(Matriz matriz) throws ExcDimensionImposible, ExcMultiplicacionImposible {
        
        if ( this.ancho() != matriz.largo() ) {
            
            throw new ExcMultiplicacionImposible( this.ancho(), matriz.largo() );
            
        }
        
        Matriz multiplicacion = new Matriz(this.ancho(), matriz.largo());

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
    
    /**
     * Multiplica los valores numéricos de la matriz por un número
     * @param numero el número con el cual se desea multiplicar la matriz actual
     * @return la matriz que contiene la multiplicacion de la matriz con el número
     */
    public Matriz multiplicar(double numero) throws ExcDimensionImposible {
        
        Matriz multiplicacion = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0, A = multiplicacion.ancho(); x < A; x++){
            
            multiplicacion.establecer(x, this.obtener(x).multiplicar(numero));
            
        }
        
        return multiplicacion;
        
    }
    
    /**
     * Multiplica los valores numéricos de la matriz por si mismos la cantidad de veces especificada
     * @param exponente el número de veces que la matriz se multiplica por si misma
     * @return el resultado de la multiplicación
     */
    public Matriz elevar(int exponente) throws ExcDimensionImposible, ExcMultiplicacionImposible {
        
        Matriz operacion = this;
        
        for (int x = 0; x < exponente - 1; x++){
            
            operacion = operacion.multiplicar(this);
            
        }
        
        return operacion;
        
    }
    
    /**
     * Transpone los valores numéricos diagonalmente de la matriz
     * @return la matriz transpuesta
     */
    public Matriz transpuesta() throws ExcDimensionImposible {
        
        Matriz transpuesta = new Matriz(this.largo(), this.ancho());
        
        for (int x = 0, A = this.ancho(), L = this.largo(); x < A; x++){
            
            for (int y = 0; y < A; y++){
                
                transpuesta.establecer(y, x, this.obtener(x, y));
                
            }
            
        }
        
        return transpuesta;
        
    }
    
    /**
     * Método interno, agrega instrucciones cada vez que se escalona o invierte una matriz
     * @param Instruccion 
     */
    private void agregarInstruccion(String Instruccion){
        
        this.instrucciones.add(Instruccion);
        
    }
    
    /**
     * Genera un arreglo de instrucciones
     * @return el arreglo que contiene las instrucciones
     */
    public Object [] obtenerInstrucciones(){
        
        return this.instrucciones.toArray();
        
    }
    
    /**
     * Genera solo una instrucción de invertida
     * @param escalonada la matriz escalonada base que se está utilizando para invertir
     * @param inversa la matriz invertida que se está procesando
     * @return true si se pudo generar una instrucción, caso contrario retorna false
     */
    private boolean invertirParte(Matriz escalonada, Matriz inversa) {
        
        for (int ancho = escalonada.ancho(), indiceNulo = ancho - 1; indiceNulo >= 0 ; indiceNulo--){
            
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
                        
                        return true;
                            
                    }
                        
                }
                    
            }
                
        }

        for (int diagonal = 0, ancho = escalonada.ancho(); diagonal < ancho; diagonal++){
            // Verificar que diagonalmente los valores son 1
            // Si son 0, no se puede hacer nada
            // Si son diferentes de 1 se debe dividir la fila por tal numero

            double valorDiagonal = escalonada.obtener(diagonal, diagonal);
            
            if (valorDiagonal != 0 & valorDiagonal != 1){
                
                inversa.agregarInstruccion("L" + (diagonal + 1) + "(1/" + valorDiagonal + ")");
                escalonada.agregarInstruccion("L" + (diagonal + 1) + "(1/" + valorDiagonal + ")");
                
                double valorInverso = 1 / valorDiagonal;
                
                inversa.establecer(diagonal, inversa.obtener(diagonal).multiplicar(valorInverso));
                escalonada.establecer(diagonal, escalonada.obtener(diagonal).multiplicar(valorInverso));
                
                return true;
                    
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
                    
                    return true;
                    
                }
                    
            }
                
        }
        
        return false;
        
    }
    
    /**
     * Genera una matriz inversa a la matriz actual
     * @return la matriz inversa
     */
    public Matriz inversa() throws ExcDimensionImposible {
        
        Matriz escalonada = this.clonar();
        Matriz inversa = new Matriz(this.ancho());
        
        for (boolean repetir = true; repetir;){
            
            repetir = this.invertirParte(escalonada, inversa);
            
        }
        
        return inversa;
    }
    
    /**
     * Genera solo una instrucción de escalonado
     * @param escalonada la matriz que se está procesando
     * @return true si se pudo generar una instrucción de escalonado, caso contrario false
     */
    private boolean escalonarParte(Matriz escalonada) {
        
        for (int ancho = escalonada.ancho(), indiceNulo = ancho - 1; indiceNulo >= 0 ; indiceNulo--){
            
            // Cambiar filas por filas mas convenientes
            
            if ( escalonada.obtener(indiceNulo, indiceNulo) == 0 ) {
                
                // Verificar que el valor diagonal es cero
                
                for (int i = ancho; i > 0; i--){
                    
                    if ( escalonada.obtener(i, indiceNulo) != 0 ) {
                        
                        // Si verticalmente otra fila tiene un valor diferente de cero, cambiar las filas
                        
                        escalonada.agregarInstruccion("L" + (indiceNulo + 1) + " " + (i + 1));

                        Vector vector = escalonada.obtener(i);
                        escalonada.establecer(i, escalonada.obtener(indiceNulo));
                        escalonada.establecer(indiceNulo, vector);
                        
                        return true;
                        
                    }
                        
                }
                
            }
                
        }
        
        for (int diagonal = 0, ancho = escalonada.ancho(); diagonal < ancho; diagonal++){
            // Verificar que diagonalmente (en la matriz) los valores son 1
            // Si son 0, no se puede hacer nada
            // Si son diferentes de 1 se debe dividir la fila por tal numero

            double valorDiagonal = escalonada.obtener(diagonal, diagonal);
            
            if ( valorDiagonal != 0 & valorDiagonal != 1 ) {
                
                escalonada.agregarInstruccion("L" + (diagonal + 1) + "(1/" + valorDiagonal + ")");
                escalonada.establecer(diagonal, escalonada.obtener(diagonal).multiplicar(1 / valorDiagonal));
                
                return true;
                
            }
                
        }
        
        for (int x = 0, ancho = escalonada.ancho(); x < ancho; x++){
            
            for (int y = 0; y < ancho; y++) {
                
                if (escalonada.obtener(x, y) != 0 & escalonada.obtener(y, y) == 1 & x != y) {
                    
                    double Multiplo = escalonada.obtener(x, y);

                    escalonada.agregarInstruccion("L" + (x + 1) + (y + 1) + "(-" + Multiplo + ")");
                    escalonada.establecer(x, escalonada.obtener(x).restar(escalonada.obtener(y).multiplicar(Multiplo)));
                    
                    return true;
                    
                }
                    
            }
                
        }
        
        return false;
        
    }
    
    /**
     * Genera una matriz escalonada
     * @return la matriz escalonada
     */
    public Matriz escalonar() throws ExcDimensionImposible {
        
        Matriz escalonada = this.clonar();
        
        for (boolean Repetir = true; Repetir;){
            
            Repetir = this.escalonarParte(escalonada);
            
        }
        
        return escalonada;
        
    }
    
    /**
     * Genera una matriz idéntica a la matriz actual, con la diferencia de que sus valores numéricos tienen signos opuestos
     * @return la matriz opuesta
     */
    public Matriz opuesta() throws ExcDimensionImposible {
        
        Matriz opuesta = new Matriz(this.ancho(), this.largo());
        
        for (int x = 0, A = this.ancho(); x < A; x++){
            
            opuesta.establecer(x, this.obtener(x).opuesto());
            
        }
        
        return opuesta;
        
    }
    
    /**
     * Calcula la determinante de la matriz
     * @return la determinante
     */
    public double determinante() {
        
        if (this.ancho() != this.largo()){
            
            return 0;
            
        }else if (this.ancho() == 2){
            
            return this.obtener(0, 0) * this.obtener(1, 1) - this.obtener(0, 1) * this.obtener(1, 0);
            
        }
        
        double determinante = 0;
        byte signo = 1;
        
        try {
            
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
            
        } catch (ExcDimensionImposible e) {
            
            return 0;
            
        }
        
        return determinante;
        
    }
    
    /**
     * Verifica si la matriz es idempotente
     * @return true si la matriz es idempotente, caso contrario false
     */
    public boolean esIdempotente() {
        
        try {
            
            return this.equals(this.multiplicar(this));
        
        } catch (ExcDimensionImposible | ExcMultiplicacionImposible e) {
            
            return false;
            
        }
        
    }
    
    /**
     * Verifica si la matriz es involutiva
     * @return true si la matriz es involutiva, caso contrario false
     */
    public boolean esInvolutiva(){
        
        try {
            
            return this.equals(this.inversa());
        
        } catch (ExcDimensionImposible e) {
            
            return false;
            
        }
        
    }
    
    /**
     * Verifica si la matriz es simétrica
     * @return true si la matriz es simétrica, caso contrario false
     */
    public boolean esSimetrica(){
        
        try {
            
            return this.transpuesta().equals(this);
        
        } catch (ExcDimensionImposible e) {
            
            return false;
            
        }
        
    }
    
    /**
     * Verifica si la matriz es antisimétrica
     * @return true si la matriz es antisimétrica, caso contrario false
     */
    public boolean esAntisimetrica(){
        
        try {
            
            return this.transpuesta().equals(this.opuesta());
        
        } catch (ExcDimensionImposible e) {
            
            return false;
            
        }
        
    }
    
    /**
     * Verifica si la matriz es ortogonal
     * @return true si la matriz es ortogonal, caso contrario false
     */
    public boolean esOrtogonal(){
        
        try {
            
            return this.transpuesta().equals(this.inversa());
        
        } catch (ExcDimensionImposible e) {
            
            return false;
            
        }
        
    }
    
    /**
     * Inserta los valores numéricos de la matriz en una tabla
     * @param tabla la tabla donde se desea insertar los datos
     */
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
    
    /**
     * Guarda los valores numéricos de una matriz en un archivo
     * @param archivo el archivo donde se desea guardar los valores numéricos
     */
    public void guardar(RandomAccessFile archivo) {
        
        ArrayList<ArrayList<Double>> lista = this.obtenerLista();
        
        JSONArray arreglo = new JSONArray(lista);
        
        try {
            
            archivo.writeBytes( arreglo.toString() );
            archivo.close();
        
        } catch (IOException e) {
            
            
        }
        
    }
    
    /**
     * Transforma la matriz a una lista (de listas) de clase Double
     * @return la lista transformada
     */
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
    
}

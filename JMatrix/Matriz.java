public class Matriz <A>
{
    Vector <A> [] Datos;

    public int Largo()
    {
        // put your code here
        return Datos.length;
    }
    
    public int Ancho(){
        if (Datos.length > 0){
            return Datos[0].Ancho();
        }
        return 0;
    }
}

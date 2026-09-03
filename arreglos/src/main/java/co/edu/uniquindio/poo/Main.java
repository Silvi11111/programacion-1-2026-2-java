package co.edu.uniquindio.poo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void main() {

        //1. Crear un arreglo e inicializarlo
        // 2. realizar un metodo que sume todos los numeros del arreglo y retornar la suma.

       // int miArreglo [] = {2,3,1,5,3,6};
        //int sumatoria = sumar(miArreglo);
        //System.out.println("El resultado es "+ sumatoria);


        String [] habitaciones = {"blanco","blanco","blanco","blanco"};

        imprimirArreglo(habitaciones);

        pintar(habitaciones,"caferojorosado");

        imprimirArreglo(habitaciones);


        // realizar un metodo que diga si existe un numero dado en el arreglo


    }

    private static void imprimirArreglo(String[] habitaciones) {

        for (int i = 0; i < habitaciones.length ; i++) {
            System.out.println("Habitacion color :"+habitaciones[i]);
        }
    }

    private static String pintar(String[] habitaciones, String color) {

        String resultado = "";

        for (int i = 0; i < habitaciones.length ; i++) {
            habitaciones[i] = color;
        }
        resultado = "Termine";

        return resultado;

    }

    // 1. modificador de acceso(publico privado)
    // 2. tipo de retorno
    // 3. nombre del metodo
    // 4. los parametros

    public  static int sumar (int [] numeros){
        int resultado = 0;

        for(int index = 0; index < numeros.length; index++){
            resultado += numeros[index];
        }
        return resultado;
    }


    public static boolean existeNumero(int arreglo[],int numeroBuscar){
        boolean resultado = false;
        for(int i = 0; i < arreglo.length; i++){
           if(arreglo[i] == numeroBuscar){
               resultado = true;
               return resultado;
           }
        }
        return resultado;
    }











}

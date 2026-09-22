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


        int matriz [][] = {{2,3,45,6},
                           {4,6,7,3},
                           {4,6,7,3}
                          };

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


    /**
     *
     */
     public static boolean existeRepetido(int arreglo[]){
        boolean repetido = false;
        for(int i = 0; i < arreglo.length; i++ ){
             int numero1 = arreglo[i];
             for (int j = i+1 ; j < arreglo.length;j++){
                 int numero2 = arreglo[j];
                 if(numero1 == numero2){
                    return  true;
                 }
             }
        }
        return repetido;
     }
    public static boolean existeRepetido2(int arreglo[]){
        for(int i = 0; i < arreglo.length; i++ ){
            int numero1 = arreglo[i];
            for (int j = i+1 ; j < arreglo.length;j++){
                int numero2 = arreglo[j];
                if(numero1 == numero2){
                    return  true;
                }
            }
        }
        return false;
    }

    public static boolean existeRepetido3(int arreglo[]){
        boolean repetido = false;
         for(int i = 0; i < arreglo.length; i++ ){
            int numero1 = arreglo[i];
            for (int j = i+1 ; j < arreglo.length;j++){
                int numero2 = arreglo[j];
                if(numero1 == numero2){
                    repetido = true;
                    break;
                }
            }
            if(repetido){
                break;
            }
        }
        return repetido;
    }

    public static boolean existeRepetido4(int arreglo[]){
        boolean repetido = false;
        for(int i = 0; i < arreglo.length && repetido == false; i++ ){
            int numero1 = arreglo[i];
            for (int j = i+1 ; j < arreglo.length;j++){
                int numero2 = arreglo[j];
                if(numero1 == numero2){
                    repetido = true;
                    break;
                }
            }
        }
        System.err.println("Mensaje de error");
        return repetido;
    }


    //tarea: Investigacion- Estudiarlo
    // 1.imprimir una matriz
    // 2. sumar todos los numeros de una matriz
    // 3. Sumar los numeros de la diagonal de una matriz
    // 4. dibujar una x en una matriz
    // 5. Cuadro superior en una matriz
    // 6. Dibujar en una matriz un espiral de numeros

    // debe estudiar ciclos- (if, else, case), variables-arreglos y matrices




}

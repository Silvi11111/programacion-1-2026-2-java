package co.edu.uniquindio.poo.app;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String args[]) {

        String cadena = new String(args[0]);
        System.out.println(cadena);
        cadena = cadena.toLowerCase();
        cadena = cadena.toUpperCase();
        int size = cadena.length();

        String nuevaCadena = cadena.trim();

        String[] split = nuevaCadena.split(",");

        char c = nuevaCadena.charAt(nuevaCadena.length() - 1);

        // un String es una clase que contiene un arreglo de char

        char[] charArray = nuevaCadena.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char letra = cadena.charAt(i);
        }

        for (int i = 0; i < cadena.length(); i++) {
            char letra = cadena.charAt(i);
        }

        String a = "Hola";// es una instancia
        String b = "Hola";// es una instancia

        if(a.equals(b)){
            System.out.println("Son iguales");
        }
    }
}

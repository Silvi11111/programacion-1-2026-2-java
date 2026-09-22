package co.edu.uniquindio.poo.app;


import co.edu.uniquindio.poo.model.Curso;

import javax.swing.*;

public class Main {

    static void main() {

        JOptionPane.showMessageDialog(null,"Bienvenidos al sistema de gestion academica");
        String nombreCurso = JOptionPane.showInputDialog(null,"Por favor ingresar el nombre del curso");
        String codigoCurso = JOptionPane.showInputDialog(null,"Por favor ingresar el codigo del curso");

        Curso curso = new Curso(nombreCurso,codigoCurso);

        //CRUD create, read, update,delete
        int opcion;

        do{
            opcion = Integer.valueOf(JOptionPane.showInputDialog(null,
                    "Por favor selecciones una opcion :\n ---Menu--\n"+
                     "1. Agregar un estudiante"+

                    ""));

            switch (opcion){
                case 1:
                    crearEstudiante(curso);
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null,"Muchas gracias por usar nuestro sistema");
                    break;
                default:JOptionPane.showMessageDialog(null,"Opcion Invalida");

            }

        }while(opcion != 0);


    }

    private static void crearEstudiante(Curso curso) {

        String nombres = JOptionPane.showInputDialog(null,"Por favor ingresar los nombres del estudiante nuevo");
        String apellidos = JOptionPane.showInputDialog(null,"Por favor ingresar los apellidos del estudiante nuevo");
        String identificacion = JOptionPane.showInputDialog(null,"Por favor ingresar la indentificaion del estudiante nuevo");
        String edad = JOptionPane.showInputDialog(null,"Por favor ingresar la edad del estudiante nuevo");
        byte edadEstudiante = Byte.valueOf(edad);
        String correo = JOptionPane.showInputDialog(null,"Por favor ingresar el correo del estudiante nuevo");
        String telefono = JOptionPane.showInputDialog(null,"Por favor ingresar el telefono del estudiante nuevo");

        String resultado = curso.registrarEstudiante(nombres,apellidos,identificacion,edadEstudiante,correo,telefono);

        JOptionPane.showMessageDialog(null,resultado);


    }
}

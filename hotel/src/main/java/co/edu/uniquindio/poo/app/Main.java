package co.edu.uniquindio.poo.app;

import co.edu.uniquindio.poo.model.Habitacion;
import co.edu.uniquindio.poo.model.Hotel;
import co.edu.uniquindio.poo.model.Huesped;
import co.edu.uniquindio.poo.model.Reserva;

import javax.swing.*;
import java.awt.*;

public class Main {

    static Hotel hotel = new Hotel("StayPlus", "900.123.456-7", "Calle 21 # 14-30, Armenia", "6067451234", 20, 100);

    public static void main(String[] args) {
        cargarDatosDePrueba();

        String menu = "===== HOTEL STAYPLUS =====\n\n"
                + "1. Registrar huésped\n"
                + "2. Registrar habitación\n"
                + "3. Crear reserva\n"
                + "4. Cambiar estado de una reserva\n"
                + "5. Cambiar estado de una habitación\n"
                + "6. Consultar huésped por teléfono\n"
                + "7. Control de disponibilidad de habitaciones\n"
                + "8. Matriz de ocupación del hotel\n"
                + "9. Reservas especiales (capicúa)\n"
                + "10. Ingresos del hotel por fecha\n"
                + "11. Información del hotel\n"
                + "0. Salir\n\n"
                + "Seleccione una opción:";

        int opcion;
        do {
            String entrada = JOptionPane.showInputDialog(null, menu, "Menú principal", JOptionPane.QUESTION_MESSAGE);
            if (entrada == null) {
                opcion = 0; // si presiona Cancelar se sale
            } else if (esEntero(entrada.trim())) {
                opcion = Integer.parseInt(entrada.trim());
            } else {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    registrarHuesped();
                    break;
                case 2:
                    registrarHabitacion();
                    break;
                case 3:
                    crearReserva();
                    break;
                case 4:
                    cambiarEstadoReserva();
                    break;
                case 5:
                    cambiarEstadoHabitacion();
                    break;
                case 6:
                    consultarHuesped();
                    break;
                case 7:
                    mostrar(hotel.controlDisponibilidad(), "Disponibilidad");
                    break;
                case 8:
                    menuMatriz();
                    break;
                case 9:
                    mostrar(hotel.reservasEspeciales(), "Reservas especiales");
                    break;
                case 10:
                    consultarIngresos();
                    break;
                case 11:
                    mostrar(hotel.informacionHotel(), "Información del hotel");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema StayPlus.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 0);
    }

    // ===================== OPCIONES DEL MENÚ =====================

    static void registrarHuesped() {
        String documento = leerTexto("Documento de identidad:");
        if (hotel.buscarHuespedPorDocumento(documento) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe un huésped con ese documento.");
            return;
        }
        String nombre = leerTexto("Nombre completo:");
        int edad = leerEntero("Edad:");
        String telefono = leerTexto("Número de teléfono:");
        String ciudad = leerTexto("Ciudad de procedencia:");

        Huesped huesped = new Huesped(documento, nombre, edad, telefono, ciudad);
        hotel.registrarHuesped(huesped);
        JOptionPane.showMessageDialog(null, "Huésped registrado:\n" + huesped);
    }

    static void registrarHabitacion() {
        if (!hotel.hayEspacioHabitaciones()) {
            JOptionPane.showMessageDialog(null, "No hay espacio para más habitaciones.");
            return;
        }
        int numero = leerEntero("Número de habitación:");
        if (hotel.buscarHabitacion(numero) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe una habitación con ese número.");
            return;
        }
        String tipo = elegirOpcion("Tipo de habitación:", new String[]{"Individual", "Doble", "Suite"});
        int piso = leerEntero("Piso:");
        int capacidad = leerEntero("Capacidad máxima de personas:");
        double precio = leerDecimal("Precio por noche:");

        Habitacion habitacion = new Habitacion(numero, tipo, piso, capacidad, precio);
        hotel.agregarHabitacion(habitacion);
        JOptionPane.showMessageDialog(null, "Habitación registrada:\n" + habitacion);
    }

    static void crearReserva() {
        String documento = leerTexto("Huéspedes registrados:\n" + hotel.listarHuespedes()
                + "\nDocumento del huésped que realiza la reserva:");
        Huesped huesped = hotel.buscarHuespedPorDocumento(documento);
        if (huesped == null) {
            JOptionPane.showMessageDialog(null, "El huésped no existe. Regístrelo primero (opción 1).");
            return;
        }

        int codigo = leerEntero("Código de la reserva (número):");
        if (hotel.buscarReserva(codigo) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe una reserva con ese código.");
            return;
        }
        String fecha = leerTexto("Fecha de la reserva (dd/mm/aaaa):");
        int noches = leerEntero("Número de noches:");
        int cantidadHuespedes = leerEntero("Cantidad de huéspedes:");
        String metodoPago = elegirOpcion("Método de pago:", new String[]{"Efectivo", "Tarjeta", "Transferencia bancaria"});

        Reserva reserva = new Reserva(codigo, fecha, noches, cantidadHuespedes, metodoPago, huesped);

        // Agregar una o varias habitaciones
        boolean seguir = true;
        while (seguir) {
            String disponibles = hotel.listarHabitacionesDisponibles();
            if (disponibles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay habitaciones disponibles.");
                break;
            }
            int numero = leerEntero("Habitaciones disponibles:\n" + disponibles
                    + "\nHabitaciones ya agregadas: " + reserva.numerosHabitaciones()
                    + "\n\nNúmero de la habitación a agregar:");
            Habitacion habitacion = hotel.buscarHabitacion(numero);

            if (habitacion == null) {
                JOptionPane.showMessageDialog(null, "La habitación no existe.");
            } else if (!habitacion.estaDisponible()) {
                JOptionPane.showMessageDialog(null, "La habitación no está disponible.");
            } else if (reserva.contieneHabitacion(habitacion)) {
                JOptionPane.showMessageDialog(null, "Esa habitación ya fue agregada a la reserva.");
            } else {
                reserva.agregarHabitacion(habitacion);
                JOptionPane.showMessageDialog(null, "Habitación " + numero + " agregada.");
            }

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra habitación?",
                    "Habitaciones", JOptionPane.YES_NO_OPTION);
            seguir = (respuesta == JOptionPane.YES_OPTION);
        }

        if (reserva.getHabitaciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La reserva no tiene habitaciones. No se guardó.");
            return;
        }
        if (reserva.calcularCapacidadTotal() < cantidadHuespedes) {
            JOptionPane.showMessageDialog(null, "La capacidad de las habitaciones (" + reserva.calcularCapacidadTotal()
                    + ") no alcanza para " + cantidadHuespedes + " huéspedes. No se guardó la reserva.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea confirmar la reserva de una vez?",
                "Estado de la reserva", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            reserva.confirmar();
        }

        hotel.agregarReserva(reserva);
        mostrar("Reserva registrada correctamente:\n\n" + reserva, "Reserva");
    }

    static void cambiarEstadoReserva() {
        int codigo = leerEntero("Reservas:\n" + hotel.listarReservas() + "\nCódigo de la reserva:");
        Reserva reserva = hotel.buscarReserva(codigo);
        if (reserva == null) {
            JOptionPane.showMessageDialog(null, "La reserva no existe.");
            return;
        }
        if (reserva.getEstado().equals("Finalizada")) {
            JOptionPane.showMessageDialog(null, "La reserva ya está finalizada.");
            return;
        }

        String nuevo = elegirOpcion("Estado actual: " + reserva.getEstado() + "\nNuevo estado:",
                new String[]{"Confirmada", "Finalizada"});

        if (nuevo.equals("Confirmada")) {
            if (reserva.getEstado().equals("Confirmada")) {
                JOptionPane.showMessageDialog(null, "La reserva ya está confirmada.");
            } else if (!reserva.puedeConfirmarse()) {
                JOptionPane.showMessageDialog(null, "No se puede confirmar: alguna habitación ya no está disponible.");
            } else {
                reserva.confirmar();
                JOptionPane.showMessageDialog(null, "Reserva confirmada. Habitaciones " + reserva.numerosHabitaciones()
                        + " ahora están reservadas.");
            }
        } else {
            reserva.finalizar();
            JOptionPane.showMessageDialog(null, "Reserva finalizada. Habitaciones " + reserva.numerosHabitaciones()
                    + " quedan disponibles.");
        }
    }

    static void cambiarEstadoHabitacion() {
        int numero = leerEntero("Habitaciones:\n" + hotel.listarHabitaciones() + "\nNúmero de habitación:");
        Habitacion habitacion = hotel.buscarHabitacion(numero);
        if (habitacion == null) {
            JOptionPane.showMessageDialog(null, "La habitación no existe.");
            return;
        }
        String estado = elegirOpcion("Estado actual: " + habitacion.getEstado() + "\nNuevo estado:",
                new String[]{"Disponible", "Reservada", "Ocupada", "Mantenimiento"});
        habitacion.setEstado(estado);
        JOptionPane.showMessageDialog(null, "Estado actualizado:\n" + habitacion);
    }

    static void consultarHuesped() {
        String telefono = leerTexto("Número de teléfono del huésped:");
        String resultado = hotel.consultarHuespedPorTelefono(telefono);
        if (resultado == null) {
            JOptionPane.showMessageDialog(null, "No existe un huésped con ese teléfono.");
        } else {
            mostrar(resultado, "Consulta de huésped");
        }
    }

    static void menuMatriz() {
        String menu = "MATRIZ DE OCUPACIÓN\n\n"
                + "1. Ver matriz\n"
                + "2. Marcar ocupación de una habitación\n"
                + "3. Análisis de ocupación (mayor, menor y total)\n"
                + "0. Volver";
        int opcion;
        do {
            opcion = leerEntero(menu);
            switch (opcion) {
                case 1:
                    mostrar(hotel.mostrarMatriz(), "Matriz de ocupación");
                    break;
                case 2:
                    marcarOcupacion();
                    break;
                case 3:
                    mostrar(hotel.analisisOcupacion(), "Análisis de ocupación");
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } while (opcion != 0);
    }

    static void marcarOcupacion() {
        int numero = leerEntero(hotel.mostrarMatriz() + "\n\nNúmero de habitación:");
        if (hotel.buscarHabitacion(numero) == null) {
            JOptionPane.showMessageDialog(null, "La habitación no existe.");
            return;
        }
        String[] dias = hotel.getDiasSemana();
        String diaElegido = elegirOpcion("Día de la semana:", dias);
        int dia = 0;
        for (int i = 0; i < dias.length; i++) {
            if (dias[i].equals(diaElegido)) {
                dia = i;
            }
        }
        String estado = elegirOpcion("Estado para ese día:", new String[]{"O (Ocupada)", "D (Disponible)"});
        hotel.marcarOcupacion(numero, dia, estado.charAt(0));
        JOptionPane.showMessageDialog(null, "Matriz actualizada.");
    }

    static void consultarIngresos() {
        String fecha = leerTexto("Fecha a consultar (dd/mm/aaaa):");
        int cantidad = hotel.contarReservasPorFecha(fecha);
        double total = hotel.calcularIngresosPorFecha(fecha);
        JOptionPane.showMessageDialog(null, "Fecha: " + fecha + "\n"
                + "Reservas encontradas: " + cantidad + "\n"
                + "Ingreso total del día: $" + String.format("%,.0f", total));
    }

    // ===================== MÉTODOS DE APOYO =====================

    // Muestra textos largos con letra de ancho fijo para que las columnas queden alineadas
    static void mostrar(String texto, String titulo) {
        JTextArea area = new JTextArea(texto);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, area, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    static String leerTexto(String mensaje) {
        String texto = JOptionPane.showInputDialog(mensaje);
        while (texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El dato no puede estar vacío.");
            texto = JOptionPane.showInputDialog(mensaje);
        }
        return texto.trim();
    }

    static int leerEntero(String mensaje) {
        String texto = leerTexto(mensaje);
        while (!esEntero(texto)) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número entero positivo.");
            texto = leerTexto(mensaje);
        }
        return Integer.parseInt(texto);
    }

    static double leerDecimal(String mensaje) {
        String texto = leerTexto(mensaje);
        while (!esDecimal(texto)) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número válido (use punto para decimales).");
            texto = leerTexto(mensaje);
        }
        return Double.parseDouble(texto);
    }

    static boolean esEntero(String texto) {
        if (texto.isEmpty() || texto.length() > 9) {
            return false;
        }
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static boolean esDecimal(String texto) {
        if (texto.isEmpty() || texto.length() > 12) {
            return false;
        }
        int puntos = 0;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '.') {
                puntos++;
            } else if (!Character.isDigit(c)) {
                return false;
            }
        }
        return puntos <= 1 && !texto.equals(".");
    }

    // Muestra un menú numerado con las opciones y devuelve la elegida
    static String elegirOpcion(String mensaje, String[] opciones) {
        String texto = mensaje + "\n";
        for (int i = 0; i < opciones.length; i++) {
            texto = texto + (i + 1) + ". " + opciones[i] + "\n";
        }
        int opcion = leerEntero(texto);
        while (opcion < 1 || opcion > opciones.length) {
            JOptionPane.showMessageDialog(null, "Opción no válida.");
            opcion = leerEntero(texto);
        }
        return opciones[opcion - 1];
    }

    // ===================== DATOS DE PRUEBA =====================

    static void cargarDatosDePrueba() {
        Huesped h1 = new Huesped("1094000111", "Ana María López", 28, "3001112233", "Armenia");
        Huesped h2 = new Huesped("1094000222", "Carlos Pérez", 35, "3104445566", "Pereira");
        Huesped h3 = new Huesped("1094000333", "Laura Gómez", 22, "3207778899", "Bogotá");
        hotel.registrarHuesped(h1);
        hotel.registrarHuesped(h2);
        hotel.registrarHuesped(h3);

        Habitacion hab101 = new Habitacion(101, "Individual", 1, 1, 120000);
        Habitacion hab102 = new Habitacion(102, "Doble", 1, 2, 180000);
        Habitacion hab103 = new Habitacion(103, "Individual", 1, 1, 110000);
        Habitacion hab201 = new Habitacion(201, "Doble", 2, 2, 190000);
        Habitacion hab202 = new Habitacion(202, "Suite", 2, 4, 350000);
        Habitacion hab301 = new Habitacion(301, "Suite", 3, 4, 420000);
        hotel.agregarHabitacion(hab101);
        hotel.agregarHabitacion(hab102);
        hotel.agregarHabitacion(hab103);
        hotel.agregarHabitacion(hab201);
        hotel.agregarHabitacion(hab202);
        hotel.agregarHabitacion(hab301);
        hab201.setEstado("Ocupada");
        hab301.setEstado("Mantenimiento");

        Reserva r1 = new Reserva(1221, "22/09/2026", 2, 2, "Tarjeta", h1);
        r1.agregarHabitacion(hab102);
        r1.confirmar();
        hotel.agregarReserva(r1);

        Reserva r2 = new Reserva(4567, "22/09/2026", 3, 1, "Efectivo", h2);
        r2.agregarHabitacion(hab101);
        hotel.agregarReserva(r2);

        Reserva r3 = new Reserva(3003, "23/09/2026", 1, 4, "Transferencia bancaria", h1);
        r3.agregarHabitacion(hab202);
        r3.confirmar();
        hotel.agregarReserva(r3);

        // Matriz de ocupación de ejemplo (0 = Lunes ... 6 = Domingo)
        hotel.marcarOcupacion(101, 0, 'O');
        hotel.marcarOcupacion(101, 1, 'O');
        hotel.marcarOcupacion(102, 1, 'O');
        hotel.marcarOcupacion(102, 2, 'O');
        hotel.marcarOcupacion(201, 4, 'O');
        hotel.marcarOcupacion(201, 5, 'O');
        hotel.marcarOcupacion(202, 5, 'O');
        hotel.marcarOcupacion(202, 6, 'O');
        hotel.marcarOcupacion(103, 5, 'O');
    }
}

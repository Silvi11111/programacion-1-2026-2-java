package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class Hotel {

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;

    private ArrayList<Huesped> huespedes;   // Lista de huéspedes
    private Habitacion[] habitaciones;      // Arreglo de habitaciones
    private int cantidadHabitaciones;
    private Reserva[] reservas;             // Arreglo de reservas
    private int cantidadReservas;
    private char[][] matrizOcupacion;       // filas = habitaciones, columnas = días
    private String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

    public Hotel(String nombreComercial, String nit, String direccion, String telefono,
                 int maxHabitaciones, int maxReservas) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.huespedes = new ArrayList<>();
        this.habitaciones = new Habitacion[maxHabitaciones];
        this.cantidadHabitaciones = 0;
        this.reservas = new Reserva[maxReservas];
        this.cantidadReservas = 0;
        this.matrizOcupacion = new char[maxHabitaciones][diasSemana.length];
    }

    // ===================== HUÉSPEDES =====================

    public boolean registrarHuesped(Huesped huesped) {
        if (buscarHuespedPorDocumento(huesped.getDocumento()) != null) {
            return false;
        }
        huespedes.add(huesped);
        return true;
    }

    public Huesped buscarHuespedPorDocumento(String documento) {
        for (int i = 0; i < huespedes.size(); i++) {
            if (huespedes.get(i).getDocumento().equals(documento)) {
                return huespedes.get(i);
            }
        }
        return null;
    }

    public Huesped buscarHuespedPorTelefono(String telefono) {
        for (int i = 0; i < huespedes.size(); i++) {
            if (huespedes.get(i).getTelefono().equals(telefono)) {
                return huespedes.get(i);
            }
        }
        return null;
    }

    // Funcionalidad 1: consultar huésped por teléfono
    public String consultarHuespedPorTelefono(String telefono) {
        Huesped huesped = buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            return null;
        }
        String texto = "Nombre: " + huesped.getNombreCompleto() + "\n"
                + "Documento: " + huesped.getDocumento() + "\n"
                + "Ciudad: " + huesped.getCiudad() + "\n\n"
                + "Reservas realizadas:\n";
        int contador = 0;
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getHuesped().getDocumento().equals(huesped.getDocumento())) {
                texto = texto + "  - " + reservas[i].toString() + "\n";
                contador++;
            }
        }
        if (contador == 0) {
            texto = texto + "  (No tiene reservas registradas)";
        }
        return texto;
    }

    public String listarHuespedes() {
        String texto = "";
        for (int i = 0; i < huespedes.size(); i++) {
            texto = texto + huespedes.get(i).toString() + "\n";
        }
        return texto;
    }

    // ===================== HABITACIONES =====================

    public boolean agregarHabitacion(Habitacion habitacion) {
        if (cantidadHabitaciones >= habitaciones.length) {
            return false;
        }
        if (buscarHabitacion(habitacion.getNumero()) != null) {
            return false;
        }
        habitaciones[cantidadHabitaciones] = habitacion;
        // La nueva fila de la matriz inicia disponible todos los días
        for (int d = 0; d < diasSemana.length; d++) {
            matrizOcupacion[cantidadHabitaciones][d] = 'D';
        }
        cantidadHabitaciones++;
        return true;
    }

    public boolean hayEspacioHabitaciones() {
        return cantidadHabitaciones < habitaciones.length;
    }

    public Habitacion buscarHabitacion(int numero) {
        int posicion = buscarPosicionHabitacion(numero);
        if (posicion == -1) {
            return null;
        }
        return habitaciones[posicion];
    }

    public int buscarPosicionHabitacion(int numero) {
        for (int i = 0; i < cantidadHabitaciones; i++) {
            if (habitaciones[i].getNumero() == numero) {
                return i;
            }
        }
        return -1;
    }

    public String listarHabitaciones() {
        String texto = "";
        for (int i = 0; i < cantidadHabitaciones; i++) {
            texto = texto + habitaciones[i].toString() + "\n";
        }
        return texto;
    }

    public String listarHabitacionesDisponibles() {
        String texto = "";
        for (int i = 0; i < cantidadHabitaciones; i++) {
            if (habitaciones[i].estaDisponible()) {
                texto = texto + habitaciones[i].toString() + "\n";
            }
        }
        return texto;
    }

    // Funcionalidad 2: control de disponibilidad
    public String controlDisponibilidad() {
        if (cantidadHabitaciones == 0) {
            return "No hay habitaciones registradas.";
        }
        int disponibles = 0;
        int reservadas = 0;
        int ocupadas = 0;
        int mantenimiento = 0;
        Habitacion mayor = habitaciones[0];
        Habitacion menor = habitaciones[0];

        for (int i = 0; i < cantidadHabitaciones; i++) {
            Habitacion h = habitaciones[i];
            if (h.getEstado().equals("Disponible")) {
                disponibles++;
            } else if (h.getEstado().equals("Reservada")) {
                reservadas++;
            } else if (h.getEstado().equals("Ocupada")) {
                ocupadas++;
            } else if (h.getEstado().equals("Mantenimiento")) {
                mantenimiento++;
            }

            if (h.getPrecioNoche() > mayor.getPrecioNoche()) {
                mayor = h;
            }
            if (h.getPrecioNoche() < menor.getPrecioNoche()) {
                menor = h;
            }
        }

        return "CONTROL DE DISPONIBILIDAD\n\n"
                + "Habitaciones disponibles: " + disponibles + "\n"
                + "Habitaciones reservadas: " + reservadas + "\n"
                + "Habitaciones ocupadas: " + ocupadas + "\n"
                + "Habitaciones en mantenimiento: " + mantenimiento + "\n\n"
                + "Mayor precio por noche:\n  " + mayor.toString() + "\n"
                + "Menor precio por noche:\n  " + menor.toString();
    }

    // ===================== RESERVAS =====================

    public boolean agregarReserva(Reserva reserva) {
        if (cantidadReservas >= reservas.length) {
            return false;
        }
        if (buscarReserva(reserva.getCodigo()) != null) {
            return false;
        }
        reservas[cantidadReservas] = reserva;
        cantidadReservas++;
        return true;
    }

    public Reserva buscarReserva(int codigo) {
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getCodigo() == codigo) {
                return reservas[i];
            }
        }
        return null;
    }

    public String listarReservas() {
        String texto = "";
        for (int i = 0; i < cantidadReservas; i++) {
            texto = texto + reservas[i].toString() + " | " + reservas[i].getHuesped().getNombreCompleto() + "\n";
        }
        return texto;
    }

    // Funcionalidad 4: reservas con código capicúa
    public String reservasEspeciales() {
        String texto = "";
        int contador = 0;
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].esCapicua()) {
                texto = texto + "  * " + reservas[i].toString() + "\n";
                contador++;
            }
        }
        if (contador == 0) {
            return "No hay reservas especiales (código capicúa).";
        }
        return "RESERVAS ESPECIALES (" + contador + ")\n\n" + texto;
    }

    // Funcionalidad 5: ingresos en una fecha
    public double calcularIngresosPorFecha(String fecha) {
        double total = 0;
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getFechaReserva().equals(fecha)) {
                total = total + reservas[i].getValorTotal();
            }
        }
        return total;
    }

    public int contarReservasPorFecha(String fecha) {
        int contador = 0;
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getFechaReserva().equals(fecha)) {
                contador++;
            }
        }
        return contador;
    }

    // ===================== MATRIZ DE OCUPACIÓN =====================

    public boolean marcarOcupacion(int numeroHabitacion, int dia, char estado) {
        int fila = buscarPosicionHabitacion(numeroHabitacion);
        if (fila == -1 || dia < 0 || dia >= diasSemana.length) {
            return false;
        }
        matrizOcupacion[fila][dia] = estado;
        return true;
    }

    public String mostrarMatriz() {
        if (cantidadHabitaciones == 0) {
            return "No hay habitaciones registradas.";
        }
        String texto = String.format("%-12s", "Habitación");
        for (int d = 0; d < diasSemana.length; d++) {
            texto = texto + String.format("%-5s", diasSemana[d].substring(0, 3));
        }
        texto = texto + "\n";
        for (int f = 0; f < cantidadHabitaciones; f++) {
            texto = texto + String.format("%-12s", "Hab. " + habitaciones[f].getNumero());
            for (int d = 0; d < diasSemana.length; d++) {
                texto = texto + String.format("%-5s", matrizOcupacion[f][d]);
            }
            texto = texto + "\n";
        }
        texto = texto + "\nO = Ocupada   D = Disponible";
        return texto;
    }

    public int ocupadasPorDia(int dia) {
        int contador = 0;
        for (int f = 0; f < cantidadHabitaciones; f++) {
            if (matrizOcupacion[f][dia] == 'O') {
                contador++;
            }
        }
        return contador;
    }

    // Funcionalidad 3: análisis de la matriz
    public String analisisOcupacion() {
        if (cantidadHabitaciones == 0) {
            return "No hay habitaciones registradas.";
        }
        int[] ocupadas = new int[diasSemana.length];
        int total = 0;
        for (int d = 0; d < diasSemana.length; d++) {
            ocupadas[d] = ocupadasPorDia(d);
            total = total + ocupadas[d];
        }

        int diaMayor = 0;
        int diaMenor = 0;
        for (int d = 1; d < diasSemana.length; d++) {
            if (ocupadas[d] > ocupadas[diaMayor]) {
                diaMayor = d;
            }
            if (ocupadas[d] < ocupadas[diaMenor]) {
                diaMenor = d;
            }
        }

        String texto = "ANÁLISIS DE OCUPACIÓN SEMANAL\n\n";
        for (int d = 0; d < diasSemana.length; d++) {
            texto = texto + String.format("%-10s", diasSemana[d]) + ": " + ocupadas[d] + " ocupada(s)\n";
        }
        texto = texto + "\nDía con mayor ocupación: " + diasSemana[diaMayor] + " (" + ocupadas[diaMayor] + ")\n"
                + "Día con menor ocupación: " + diasSemana[diaMenor] + " (" + ocupadas[diaMenor] + ")\n"
                + "Total de habitaciones ocupadas en la semana: " + total;
        return texto;
    }

    // ===================== INFORMACIÓN =====================

    public String informacionHotel() {
        return "HOTEL " + nombreComercial.toUpperCase() + "\n\n"
                + "NIT: " + nit + "\n"
                + "Dirección: " + direccion + "\n"
                + "Teléfono: " + telefono + "\n\n"
                + "Huéspedes registrados: " + huespedes.size() + "\n"
                + "Habitaciones registradas: " + cantidadHabitaciones + " de " + habitaciones.length + "\n"
                + "Reservas registradas: " + cantidadReservas + " de " + reservas.length;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Huesped> getHuespedes() {
        return huespedes;
    }

    public Habitacion[] getHabitaciones() {
        return habitaciones;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public Reserva[] getReservas() {
        return reservas;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public char[][] getMatrizOcupacion() {
        return matrizOcupacion;
    }

    public String[] getDiasSemana() {
        return diasSemana;
    }
}

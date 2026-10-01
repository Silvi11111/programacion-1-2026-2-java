package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class Reserva {

    private int codigo;
    private String fechaReserva;      // formato dd/mm/aaaa
    private int numeroNoches;
    private int cantidadHuespedes;
    private String estado;            // Pendiente, Confirmada o Finalizada
    private String metodoPago;        // Efectivo, Tarjeta o Transferencia bancaria
    private double valorTotal;
    private Huesped huesped;
    private ArrayList<Habitacion> habitaciones;

    public Reserva(int codigo, String fechaReserva, int numeroNoches, int cantidadHuespedes,
                   String metodoPago, Huesped huesped) {
        this.codigo = codigo;
        this.fechaReserva = fechaReserva;
        this.numeroNoches = numeroNoches;
        this.cantidadHuespedes = cantidadHuespedes;
        this.metodoPago = metodoPago;
        this.huesped = huesped;
        this.estado = "Pendiente";
        this.valorTotal = 0;
        this.habitaciones = new ArrayList<>();
    }

    // Agrega una habitación. Si la reserva ya está confirmada, la habitación deja de estar disponible.
    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
        if (estado.equals("Confirmada")) {
            habitacion.setEstado("Reservada");
        }
        calcularValorTotal();
    }

    public boolean contieneHabitacion(Habitacion habitacion) {
        for (int i = 0; i < habitaciones.size(); i++) {
            if (habitaciones.get(i).getNumero() == habitacion.getNumero()) {
                return true;
            }
        }
        return false;
    }

    // Valor total = suma de (precio por noche de cada habitación * número de noches)
    public double calcularValorTotal() {
        double suma = 0;
        for (int i = 0; i < habitaciones.size(); i++) {
            suma = suma + habitaciones.get(i).getPrecioNoche() * numeroNoches;
        }
        valorTotal = suma;
        return valorTotal;
    }

    public int calcularCapacidadTotal() {
        int capacidad = 0;
        for (int i = 0; i < habitaciones.size(); i++) {
            capacidad = capacidad + habitaciones.get(i).getCapacidadMaxima();
        }
        return capacidad;
    }

    // Solo se puede confirmar si todas sus habitaciones siguen disponibles
    public boolean puedeConfirmarse() {
        for (int i = 0; i < habitaciones.size(); i++) {
            if (!habitaciones.get(i).estaDisponible()) {
                return false;
            }
        }
        return true;
    }

    public void confirmar() {
        estado = "Confirmada";
        for (int i = 0; i < habitaciones.size(); i++) {
            habitaciones.get(i).setEstado("Reservada");
        }
    }

    public void finalizar() {
        estado = "Finalizada";
        for (int i = 0; i < habitaciones.size(); i++) {
            habitaciones.get(i).setEstado("Disponible");
        }
    }

    // Un código es capicúa si al invertir sus dígitos se obtiene el mismo número
    public boolean esCapicua() {
        int numero = codigo;
        int invertido = 0;
        while (numero > 0) {
            int digito = numero % 10;
            invertido = invertido * 10 + digito;
            numero = numero / 10;
        }
        return invertido == codigo;
    }

    public String numerosHabitaciones() {
        String texto = "";
        for (int i = 0; i < habitaciones.size(); i++) {
            texto = texto + habitaciones.get(i).getNumero();
            if (i < habitaciones.size() - 1) {
                texto = texto + ", ";
            }
        }
        return texto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getNumeroNoches() {
        return numeroNoches;
    }

    public void setNumeroNoches(int numeroNoches) {
        this.numeroNoches = numeroNoches;
        calcularValorTotal();
    }

    public int getCantidadHuespedes() {
        return cantidadHuespedes;
    }

    public void setCantidadHuespedes(int cantidadHuespedes) {
        this.cantidadHuespedes = cantidadHuespedes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    @Override
    public String toString() {
        return "Código " + codigo + " | " + fechaReserva + " | " + numeroNoches + " noche(s) | "
                + cantidadHuespedes + " huésped(es) | Hab: " + numerosHabitaciones() + " | "
                + estado + " | " + metodoPago + " | Total: $" + String.format("%,.0f", valorTotal);
    }
}

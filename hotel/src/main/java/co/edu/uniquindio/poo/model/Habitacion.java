package co.edu.uniquindio.poo.model;

public class Habitacion {

    private int numero;
    private String tipo;          // Individual, Doble o Suite
    private int piso;
    private int capacidadMaxima;
    private double precioNoche;
    private String estado;        // Disponible, Reservada, Ocupada o Mantenimiento

    public Habitacion(int numero, String tipo, int piso, int capacidadMaxima, double precioNoche) {
        this.numero = numero;
        this.tipo = tipo;
        this.piso = piso;
        this.capacidadMaxima = capacidadMaxima;
        this.precioNoche = precioNoche;
        this.estado = "Disponible";
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return estado.equals("Disponible");
    }

    @Override
    public String toString() {
        return "Hab. " + numero + " | " + tipo + " | Piso " + piso + " | Cap: " + capacidadMaxima
                + " | $" + String.format("%,.0f", precioNoche) + "/noche | " + estado;
    }
}

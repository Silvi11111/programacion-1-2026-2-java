package co.edu.uniquindio.poo.model;

public class Huesped {

    private String documento;
    private String nombreCompleto;
    private int edad;
    private String telefono;
    private String ciudad;

    public Huesped(String documento, String nombreCompleto, int edad, String telefono, String ciudad) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return documento + " - " + nombreCompleto + " (" + edad + " años) - Tel: " + telefono + " - " + ciudad;
    }
}

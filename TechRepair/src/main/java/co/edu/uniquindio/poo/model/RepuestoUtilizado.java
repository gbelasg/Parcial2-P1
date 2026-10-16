package co.edu.uniquindio.poo.model;

public record RepuestoUtilizado(int cantidad, double precio) {

    public double calcularSubtotal(){
        return cantidad*precio;
    }
}

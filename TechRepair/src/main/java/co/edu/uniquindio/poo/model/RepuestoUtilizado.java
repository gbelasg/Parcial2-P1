package co.edu.uniquindio.poo.model;

public record RepuestoUtilizado ( Repuesto repuesto,int cantidad, double precio) {

    public double calcularSubtotal(){
        return cantidad*precio;
    }


}

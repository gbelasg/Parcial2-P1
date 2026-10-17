package co.edu.uniquindio.poo.model;

public class Diagnostico extends Servicio{
    private double costoFijo;

    public Diagnostico(String codigo, String nombre, String descripcion,
                       double tarifaBase, double costoFijo) {
        super(codigo, nombre, descripcion, tarifaBase);
        this.costoFijo = 35000;
    }

    public double getCostoFijo() {
        return costoFijo;
    }

    public void setCostoFijo(double costoFijo) {
        this.costoFijo = costoFijo;
    }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        return costoFijo;
    }

    @Override
    public TipoServicio getTipo() {
        return TipoServicio.DIAGNOSTICO;
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "costoFijo=" + costoFijo +
                '}';
    }
}

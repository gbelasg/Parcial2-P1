package co.edu.uniquindio.poo.model;

public class ReparacionCorrectiva extends Servicio{
    private double valorHora;

    public ReparacionCorrectiva(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, 45000);
        this.valorHora = 45000;
    }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        return detalle.getHorasT() * valorHora
                + detalle.calcularCostoRepuestos();
    }
    @Override
    public TipoServicio getTipo() {
        return TipoServicio.REPARACION_CORRECTIVA;
    }
    @Override
    public boolean permiteRepuestos() {
        return true;
    }

    @Override
    public boolean cobraPorHoras() {
        return true;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    @Override
    public String toString() {
        return "ReparacionCorrectiva{" +
                "valorHora=" + valorHora +
                '}';
    }

}

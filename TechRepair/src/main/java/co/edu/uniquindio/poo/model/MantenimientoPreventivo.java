package co.edu.uniquindio.poo.model;

public class MantenimientoPreventivo extends Servicio {
    private double tarifaPortatil;
    private double tarifaEscritorio;
    private double tarifaTelefono;

    public MantenimientoPreventivo(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, 60000);
        this.tarifaPortatil = 60000;
        this.tarifaEscritorio = 70000;
        this.tarifaTelefono = 40000;
    }

    public double getTarifaPortatil() {
        return tarifaPortatil;
    }

    public void setTarifaPortatil(double tarifaPortatil) {
        this.tarifaPortatil = tarifaPortatil;
    }

    public double getTarifaEscritorio() {
        return tarifaEscritorio;
    }

    public void setTarifaEscritorio(double tarifaEscritorio) {
        this.tarifaEscritorio = tarifaEscritorio;
    }

    public double getTarifaTelefono() {
        return tarifaTelefono;
    }

    public void setTarifaTelefono(double tarifaTelefono) {
        this.tarifaTelefono = tarifaTelefono;
    }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        CategoriaEquipo categoria = detalle.getEquipo().getCategoria();
        if (categoria == CategoriaEquipo.COMPUTADORES_PORTATILES) {
            return tarifaPortatil;
        } else if (categoria == CategoriaEquipo.COMPUTADORES_ESCRITORIO) {
            return tarifaEscritorio;
        } else if (categoria == CategoriaEquipo.TELEFONO_INTELIGENTE) {
            return tarifaTelefono;
        }
        return 0;
    }

    @Override
    public TipoServicio getTipo() {
        return TipoServicio.MANTENIMIENTO_PREVENTIVO;
    }

    @Override
    public String toString() {
        return "MantenimientoPreventivo{" +
                "tarifaPortatil=" + tarifaPortatil +
                ", tarifaEscritorio=" + tarifaEscritorio +
                ", tarifaTelefono=" + tarifaTelefono +
                '}';
    }
}

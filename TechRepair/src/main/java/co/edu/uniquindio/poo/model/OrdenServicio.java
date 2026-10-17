package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdenServicio {
    private String codigo;
    private LocalDate fecha;
    private String observaciones;
    private EstadoOrden estado;
    private Cliente cliente;
    private List<DetalleServicio> listaDetalles;
    private TechRepair ownedByTechRepair;

    public OrdenServicio(String codigo, LocalDate fecha, String observaciones,
                         EstadoOrden estado, Cliente cliente,
                         List<DetalleServicio> listaDetalles, TechRepair ownedByTechRepair) {
        this.codigo = codigo;
        this.fecha = LocalDate.now();
        this.observaciones = observaciones;
        this.estado = EstadoOrden.ABIERTA;
        this.cliente = cliente;
        this.listaDetalles = new ArrayList<>();
        this.ownedByTechRepair = ownedByTechRepair;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleServicio> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<DetalleServicio> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public TechRepair getOwnedByTechRepair() {
        return ownedByTechRepair;
    }

    public void setOwnedByTechRepair(TechRepair ownedByTechRepair) {
        this.ownedByTechRepair = ownedByTechRepair;
    }
    public void validarEsEditable(){
        if(EstadoOrden.ABIERTA != estado && EstadoOrden.EN_PROCESO != estado){
            throw new IllegalStateException("La orden esta" + estado + "ya no se puede modificar");
        }
    }
    public void agregarDetalle(){
        if(buscarDetalle(detalle.getCodigo()).isPresent()){
            throw new IllegalStateException("El detalle" + detalle.getCodigo()
                    + "ya existe en la orden" + codigo);
        }
        if (!cliente.esDuenoDe(detalle.getEquipo())) {
            throw new IllegalArgumentException("El equipo " + detalle.getEquipo().getCodigo()
                    + " no pertenece al cliente " + cliente.getNombreCompleto());
        }
        detalle.setOwnedByOrden(this);
        listaDetalles.add(detalle);
    }
    public Optional<DetalleServicio> buscarDetalle (String codigoDetalle){
        return listaDetalles.stream().
                filter(d -> d.getCodigo().equalsIgnoreCase(codigoDetalle))
                .findFirst();
        }
    public void actualizarEstadoSegunDetalles() {
        if (estado == EstadoOrden.ABIERTA) {
            for (DetalleServicio d : listaDetalles) {
                if (d.getEstado() == EstadoDetalle.EN_PROCESO || d.getEstado() == EstadoDetalle.FINALIZADO) {
                    estado = EstadoOrden.EN_PROCESO;
                    return;
                }
            }
        }
    }
    public void finalizar() {
        if (listaDetalles.isEmpty()) {
            throw new IllegalStateException("La orden no tiene detalles");
        }
        for (DetalleServicio d : listaDetalles) {
            if (d.estaPendiente()) {
                throw new IllegalStateException("No se puede finalizar: el detalle " + d.getCodigo()
                        + " está " + d.getEstado() + ".");
            }
        }
        estado = EstadoOrden.FINALIZADA;
    }
    public void entregar() {
        if (estado != EstadoOrden.FINALIZADA) {
            throw new IllegalStateException("Solo se pueden entregar órdenes finalizadas. Estado actual: " + estado);
        }
        estado = EstadoOrden.ENTREGADA;
    }

    public void cancelar() {
        for (DetalleServicio d : listaDetalles) {
            d.cancelarPorOrden();
        }
        estado = EstadoOrden.CANCELADA;
    }
    public double calcularTotal() {
        double total = 0;
        for (DetalleServicio d : listaDetalles) {
            if (d.getEstado() != EstadoDetalle.CANCELADO) {
                total += d.calcularValor();
            }
        }
        return total;
    }

    public boolean contieneEquipo(Equipo equipo) {
        for (DetalleServicio d : listaDetalles) {
            if (d.getEquipo() == equipo) {
                return true;
            }
        }
        return false;
    }

    public String getInformacion() {
        String texto = "ORDEN " + codigo + " [" + estado + "]";
        texto += "\nFecha: " + fecha;
        texto += "\nCliente: " + cliente;
        texto += "\nObservaciones: " + observaciones;

        texto += "\nEquipos:";
        for (Equipo e : obtenerEquipos()) {
            texto += "\n   - " + e;
        }

        texto += "\nDetalles:";
        for (DetalleServicio d : listaDetalles) {
            texto += "\n" + d.getInformacion();
        }

        texto += "\nTOTAL ORDEN: $" + calcularTotal();
        return texto;
    }
    @Override
    public String toString() {
        return "OrdenServicio{" +
                "codigo='" + codigo + '\'' +
                ", fecha=" + fecha +
                ", observaciones='" + observaciones + '\'' +
                ", estado=" + estado +
                ", cliente=" + cliente +
                ", listaDetalles=" + listaDetalles +
                ", ownedByTechRepair=" + ownedByTechRepair +
                '}';
    }
}


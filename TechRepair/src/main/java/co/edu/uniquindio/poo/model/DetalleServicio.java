package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;

public class DetalleServicio {
    private String codigo;
    private String descripcionFalla;
    private double horasT;
    private String observaciones;
    private EstadoDetalle estado;
    private Equipo equipo;
    private Servicio servicio;
    private Tecnico tecnico;
    private List<Repuesto> listaRepuestosUtilizados;
    private OrdenServicio ownedByOrden;

    public DetalleServicio(String codigo, String descripcionFalla, double horasT,
                           String observaciones, EstadoDetalle estado, Equipo equipo,
                           Servicio servicio, Tecnico tecnico,
                           List<Repuesto> listaRepuestosUtilizados, OrdenServicio ownedByOrden) {
        this.codigo = codigo;
        this.descripcionFalla = descripcionFalla;
        this.horasT = 0;
        this.observaciones = observaciones;
        this.estado = EstadoDetalle.PENDIENTE;
        this.equipo = equipo;
        this.servicio = servicio;
        this.tecnico = tecnico;
        this.listaRepuestosUtilizados = new ArrayList<>();
        this.ownedByOrden = ownedByOrden;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    public void setDescripcionFalla(String descripcionFalla) {
        this.descripcionFalla = descripcionFalla;
    }

    public double getHorasT() {
        return horasT;
    }

    public void setHorasT(double horasT) {
        this.horasT = horasT;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(EstadoDetalle estado) {
        this.estado = estado;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public List<Repuesto> getListaRepuestosUtilizados() {
        return listaRepuestosUtilizados;
    }

    public void setListaRepuestosUtilizados(List<Repuesto> listaRepuestosUtilizados) {
        this.listaRepuestosUtilizados = listaRepuestosUtilizados;
    }

    public OrdenServicio getOwnedByOrden() {
        return ownedByOrden;
    }

    public void setOwnedByOrden(OrdenServicio ownedByOrden) {
        this.ownedByOrden = ownedByOrden;
    }
    public void asignarTecnico(Tecnico nuevoTecnico){
        nuevoTecnico.validarPuedeAtender(equipo);
        if (tecnico != null) {
            tecnico.quitarDetalle(this);
        }
        tecnico = nuevoTecnico;
        nuevoTecnico.agregarDetalle(this);
    }
    public void agregarRepuestoUtilizado(Repuesto repuesto, int cantidad) {
        if (!servicio.permiteRepuestos()) {
            throw new IllegalStateException("Solo las reparaciones correctivas " +
                    "pueden usar repuestos");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (!repuesto.esCompatibleCon(equipo)) {
            throw new IllegalStateException("El repuesto " + repuesto.getNombre() + " es para "
                    + repuesto.getCategoriaCompatibilidad() + " y el equipo es " +
                    equipo.getCategoria() + );
        }
        repuesto.descontar(cantidad);
        listaRepuestosUtilizados.add(new RepuestoUtilizado(repuesto, cantidad));
    }
    public void registrarHoras(double horas){
        if (horas <= 0) {
            throw new IllegalStateException("Las horas trabajadas deben ser positivas");
        }
        this.horasT = horas;
    }
    public void cambiarEstado (EstadoDetalle nuevoEstado, String observaciones){
        if (nuevoEstado == EstadoDetalle.PENDIENTE) {
            throw new IllegalStateException("Un detalle no puede volver a pendiente");
        }
        if ((nuevoEstado == EstadoDetalle.EN_PROCESO || nuevoEstado == EstadoDetalle.FINALIZADO) && tecnico == null) {
            throw new IllegalStateException("Debe asignar un técnico antes de cambiar el detalle a " + nuevoEstado);
        }
        if (nuevoEstado == EstadoDetalle.FINALIZADO && servicio.cobraPorHoras() && horasT <= 0) {
            throw new IllegalStateException("Debe registrar las horas de trabajo antes de finalizar la reparación.");
        }
        if (nuevoEstado == EstadoDetalle.CANCELADO) {
            devolverRepuestos();
        }
        if (observaciones != null && !observaciones.isBlank()) {
            observaciones = observaciones;
        }
        estado = nuevoEstado;
        if (ownedByOrden != null) {
            ownedByOrden.actualizarEstadoSegunDetalles();
        }
    }

    private void devolverRepuestos() {
        for (RepuestoUtilizado ru : listaRepuestosUtilizados) {
            ru.repuesto().reponer(ru.cantidad());
        }
        listaRepuestosUtilizados.clear();
    }

    void cancelarPorOrden() {
        if (estaPendiente()) {
            devolverRepuestos();
            estado = EstadoDetalle.CANCELADO;
        }
    }
    public double calcularCostoRepuestos() {
        double total = 0;
        for (RepuestoUtilizado ru : listaRepuestosUtilizados) {
            total += ru.calcularSubtotal();
        }
        return total;
    }

    public double calcularValor() {
        return servicio.calcularCosto(this);
    }

    public boolean estaPendiente() {
        return estado == EstadoDetalle.PENDIENTE || estado == EstadoDetalle.EN_PROCESO;
    }

    public String getInformacion() {
        String texto = "Detalle " + codigo + " [" + estado + "]";
        texto += "\n   Equipo: " + equipo;
        texto += "\n   Servicio: " + servicio.getNombre();
        texto += "\n   Falla: " + descripcionFalla;

        if (tecnico != null) {
            texto += "\n   Técnico: " + tecnico.getNombre();
        } else {
            texto += "\n   Técnico: Sin asignar";
        }
        texto += "\n   Horas: " + horasT;
        for (RepuestoUtilizado ru : listaRepuestosUtilizados) {
            texto += "\n   Repuesto: " + ru;
        }
        texto += "\n   Observaciones: " + observaciones;
        texto += "\n   Valor: $" + calcularValor();
        return texto;
    }

    @Override
    public String toString() {
        return "DetalleServicio{" +
                "codigo='" + codigo + '\'' +
                ", descripcionFalla='" + descripcionFalla + '\'' +
                ", horasT=" + horasT +
                ", observaciones='" + observaciones + '\'' +
                ", estado=" + estado +
                ", equipo=" + equipo +
                ", servicio=" + servicio +
                ", tecnico=" + tecnico +
                ", listaRepuestosUtilizados=" + listaRepuestosUtilizados +
                ", ownedByOrden=" + ownedByOrden +
                '}';
    }
}

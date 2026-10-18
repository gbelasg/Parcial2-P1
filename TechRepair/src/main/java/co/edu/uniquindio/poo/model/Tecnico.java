package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;

public class Tecnico {
    private String codigo;
    private String nombre;
    private String documentoIdentidad;
    private Especialidad especialidad;
    private EstadoTecnico estado;
    private List<DetalleServicio> listaDetallesAsignados;
    private Empresa ownedByEmpresa;

    public Tecnico(String codigo, String nombre, String documentoIdentidad,
                   EstadoTecnico estado, List<DetalleServicio> listaDetallesAsignados,
                   Empresa ownedByEmpresa) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.estado = estado;
        this.listaDetallesAsignados = listaDetallesAsignados;
        this.ownedByEmpresa = ownedByEmpresa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public EstadoTecnico getEstado() {
        return estado;
    }

    public void setEstado(EstadoTecnico estado) {
        this.estado = estado;
    }

    public List<DetalleServicio> getListaDetallesAsignados() {
        return listaDetallesAsignados;
    }

    public void setListaDetallesAsignados(List<DetalleServicio> listaDetallesAsignados) {
        this.listaDetallesAsignados = listaDetallesAsignados;
    }

    public Empresa getOwnedByTechRepair() {
        return ownedByEmpresa;
    }

    public void setOwnedByTechRepair(Empresa ownedByTechRepair) {
        this.ownedByEmpresa = ownedByTechRepair;
    }

    public boolean estaActivo() {
        if (estado == EstadoTecnico.ACTIVO) {
            return true;
        }
        return false;
    }

    public boolean puedeAtender(Equipo equipo) {
        CategoriaEquipo categoria = equipo.getCategoria();
        if (especialidad == Especialidad.COMPUTADORES) {
            return categoria == CategoriaEquipo.COMPUTADORES_PORTATILES ||
                    categoria == CategoriaEquipo.COMPUTADORES_ESCRITORIO;
        }
        if (especialidad == Especialidad.DISPOSITIVOS_MOVILES) {
            return categoria == CategoriaEquipo.TELEFONO_INTELIGENTE;
        }
        if (especialidad == Especialidad.SOPORTE_GENERAL) {
            return true;
        }
        return false;
    }

    public void validarPuedeAtender(Equipo equipo) {
        if (!estaActivo()) {
            throw new IllegalStateException("El tecnico no esta activo");
        }
        if (!puedeAtender(equipo)) {
            throw new IllegalStateException("la especialidad del tecnico" + especialidad +
                    "no corresponde al equipo: " + equipo.getCategoria());
        }
    }

    public void agregarDetalle(DetalleServicio detalle) {
        if (!listaDetallesAsignados.contains(detalle)) {
            listaDetallesAsignados.add(detalle);
        }
    }

    public void quitarDetalle(DetalleServicio detalle) {
        listaDetallesAsignados.remove(detalle);
    }

    public List<DetalleServicio> obtenerDetallesPorEstado(EstadoDetalle estado) {
        List<DetalleServicio> resultado = new ArrayList<>();
        for (DetalleServicio d : listaDetallesAsignados) {
            if (d.getEstado() == estado) {
                resultado.add(d);
            }
        }
        return resultado;
    }

    public int contarIntervencionesFinalizadas() {
        int contador = 0;
        for (DetalleServicio d : listaDetallesAsignados) {
            if (EstadoDetalle.FINALIZADO == d.getEstado()) {
                contador++;
            }
        }
        return contador;
    }
    public double calcularValorServiciosAtendidos(){
        double valorT = 0;
        for (DetalleServicio d : listaDetallesAsignados) {
            if (d.getEstado() == EstadoDetalle.FINALIZADO){
                valorT+= d.calcularValor();
            }
        }
        return valorT;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", especialidad=" + especialidad +
                ", estado=" + estado +
                ", listaDetallesAsignados=" + listaDetallesAsignados +
                ", ownedByTechRepair=" + ownedByEmpresa +
                '}';
    }
}

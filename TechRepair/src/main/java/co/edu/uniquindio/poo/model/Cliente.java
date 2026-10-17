package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cliente {
    private String documentoIdentidad;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion;
    private Equipo equipo;
    private List<Equipo> listaEquipos;
    private TechRepair ownedByTechRepair;

    public Cliente(String documentoIdentidad, String nombreCompleto, String telefono,
                   String correo, String direccion, List<Equipo> listaEquipos,
                   TechRepair ownedByTechRepair) {
        this.documentoIdentidad = documentoIdentidad;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.listaEquipos = new ArrayList<>();
        this.ownedByTechRepair = ownedByTechRepair;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Equipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public TechRepair getOwnedByTechRepair() {
        return ownedByTechRepair;
    }

    public void setOwnedByTechRepair(TechRepair ownedByTechRepair) {
        this.ownedByTechRepair = ownedByTechRepair;
    }

    public void agregarEquipo(){
        equipo.setOwnedByCliente(this);
        listaEquipos.add(equipo);
    }

    public Optional<Equipo> buscarEquipo(String codigo) {
        return listaEquipos.stream()
                .filter(e -> e.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public boolean eliminarEquipo(Equipo equipo) {
        return listaEquipos.remove(equipo);
    }
    public boolean esDuenoDe(Equipo equipo){
            for (Equipo e : listaEquipos) {
                if (e == equipo) {
                    return true;
                }
            }
            return false;
        }

    public String getInformacion() {
        String texto = "Documento: " + documentoIdentidad;
        texto += "\nNombre: " + nombreCompleto;
        texto += "\nTeléfono: " + telefono;
        texto += "\nCorreo: " + correo;
        texto += "\nDirección: " + direccion;
        texto += "\nEquipos:";
        for (Equipo e : listaEquipos) {
            texto += "\n   - " + e;
        }
        return texto;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "documentoIdentidad='" + documentoIdentidad + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", equipo=" + equipo +
                ", listaEquipos=" + listaEquipos +
                ", ownedByTechRepair=" + ownedByTechRepair +
                '}';
    }
}

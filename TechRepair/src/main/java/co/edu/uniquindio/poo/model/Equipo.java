package co.edu.uniquindio.poo.model;

public abstract class Equipo {
    private String codigo;
    private String numeroSerie;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private String estadoFisico;
    private Cliente ownedByCliente;

    public Equipo(String codigo, String numeroSerie, String marca, String modelo,
                  int anioFabricacion, String estadoFisico, Cliente ownedByCliente) {
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.estadoFisico = estadoFisico;
        this.ownedByCliente = ownedByCliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public Cliente getOwnedByCliente() {
        return ownedByCliente;
    }

    public void setOwnedByCliente(Cliente ownedByCliente) {
        this.ownedByCliente = ownedByCliente;
    }

    public abstract CategoriaEquipo getCategoria();
    public abstract String getCaracteristicas();

    public String getInformacion() {
        return "Código: " + codigo + " | Serie: " + numeroSerie
                + "\nCategoría: " + getCategoria()
                + "\nMarca/Modelo: " + marca + " " + modelo + " (" + anioFabricacion + ")"
                + "\nEstado físico: " + estadoFisico
                + "\n" + getCaracteristicas()
                + "\nDueño: " + (ownedByCliente != null ? ownedByCliente.getNombreCompleto() : "-");
    }
    @Override
    public String toString() {
        return "Equipo{" +
                "codigo='" + codigo + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anioFabricacion=" + anioFabricacion +
                ", estadoFisico='" + estadoFisico + '\'' +
                '}';
    }
}

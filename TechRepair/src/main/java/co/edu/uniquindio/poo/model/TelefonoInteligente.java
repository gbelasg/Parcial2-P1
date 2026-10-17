package co.edu.uniquindio.poo.model;

public class TelefonoInteligente extends Equipo {
    private String sistemaOperativo;
    private int almacenamiento;
    private int capacidadBateria;
    private boolean compatible5G;

    public TelefonoInteligente(String codigo, String numeroSerie, String marca,
                               String modelo, int anioFabricacion, String estadoFisico,
                               Cliente ownedByCliente, String sistemaOperativo,
                               int almacenamiento, int capacidadBateria, boolean compatible5G) {
        super(codigo, numeroSerie, marca, modelo, anioFabricacion,
                estadoFisico, ownedByCliente);
        this.sistemaOperativo = sistemaOperativo;
        this.almacenamiento = almacenamiento;
        this.capacidadBateria = capacidadBateria;
        this.compatible5G = compatible5G;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public int getCapacidadBateria() {
        return capacidadBateria;
    }

    public void setCapacidadBateria(int capacidaBateria) {
        this.capacidadBateria = capacidaBateria;
    }

    public boolean isCompatible5G() {
        return compatible5G;
    }

    public void setCompatible5G(boolean compatible5G) {
        this.compatible5G = compatible5G;
    }

    @Override
    public CategoriaEquipo getCategoria() {
        return CategoriaEquipo.TELEFONO_INTELIGENTE;
    }

    @Override
    public String getCaracteristicas() {
        return "Sistema operativo: " + sistemaOperativo + " | Almacenamiento: " + almacenamiento +
                " GB | Batería: "
                + capacidadBateria + " mAh | 5G: " + (compatible5G ? "Sí" : "No");
    }

}


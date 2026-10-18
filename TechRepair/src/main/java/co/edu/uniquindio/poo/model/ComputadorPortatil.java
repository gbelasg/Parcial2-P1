package co.edu.uniquindio.poo.model;

public class ComputadorPortatil extends Equipo {
    private double tamanoPantalla;
    private int memoriaRam;
    private int almacenamiento;
    private boolean bateriaExtraible;

    public ComputadorPortatil(String codigo, String numeroSerie, String marca,
                              String modelo, int anioFabricacion, String estadoFisico,
                              Cliente ownedByCliente, double tamanoPantalla, int memoriaRam,
                              int almacenamiento, boolean bateriaExtraible) {
        super(codigo, numeroSerie, marca, modelo, anioFabricacion, estadoFisico, ownedByCliente);
        this.tamanoPantalla = tamanoPantalla;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.bateriaExtraible = bateriaExtraible;
    }

    public double getTamanoPantalla() {
        return tamanoPantalla;
    }

    public void setTamanoPantalla(double tamanoPantalla) {
        this.tamanoPantalla = tamanoPantalla;
    }

    public int getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(int memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public boolean isBateriaExtraible() {
        return bateriaExtraible;
    }

    public void setBateriaExtraible(boolean bateriaExtraible) {
        this.bateriaExtraible = bateriaExtraible;
    }
    @Override
    public CategoriaEquipo getCategoria() {
        return CategoriaEquipo.COMPUTADORES_PORTATILES;
    }

    @Override
    public String getCaracteristicas() {
        return "Pantalla: " + tamanoPantalla + "\" | RAM: " + memoriaRam +
                " GB | Almacenamiento: " + almacenamiento + " GB | Batería extraíble: "
                + (bateriaExtraible ? "Sí" : "No");
    }
}

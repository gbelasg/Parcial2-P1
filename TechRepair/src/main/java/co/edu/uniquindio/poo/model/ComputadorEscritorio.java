package co.edu.uniquindio.poo.model;

public class ComputadorEscritorio extends Equipo {
    private String tipoProcesador;
    private int memoriaRam;
    private int almacenamiento;
    private boolean tarjetaGraficaDedicada;

    public ComputadorEscritorio(String codigo, String numeroSerie, String marca,
                                String modelo, int anioFabricacion, String estadoFisico,
                                Cliente ownedByCliente, String tipoProcesador,
                                int memoriaRam, int almacenamiento,
                                boolean tarjetaGraficaDedicada) {
        super(codigo, numeroSerie, marca, modelo, anioFabricacion, estadoFisico,
                ownedByCliente);
        this.tipoProcesador = tipoProcesador;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.tarjetaGraficaDedicada = tarjetaGraficaDedicada;
    }

    @Override
    public CategoriaEquipo getCategoria() {
        return CategoriaEquipo.COMPUTADOR_ESCRITORIO;
    }

    @Override
    public String getCaracteristicas() {
        return "Procesador: " + tipoProcesador + " | RAM: " + memoriaRam +
                " GB | Almacenamiento: " + almacenamiento +
                " GB | Gráfica dedicada: " + (tarjetaGraficaDedicada ? "Sí" : "No");
    }
}

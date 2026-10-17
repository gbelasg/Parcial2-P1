package co.edu.uniquindio.poo.model;

public class Repuesto {

    private final String coodigo;
    private final String nombre;
    private final String descripcion;
    private double precio;
    private int cantidadDisponible;
    private final CategoriaEquipo categoriaCompatibilidad;
    private final Empresa ownedByEmpresa;

    public Repuesto(String coodigo, String nombre, String descripcion, CategoriaEquipo categoriaCompatibilidad, Empresa ownedByEmpresa, int cantidadDisponible, double precio) {
        this.coodigo = coodigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoriaCompatibilidad = categoriaCompatibilidad;
        this.ownedByEmpresa = ownedByEmpresa;
        this.cantidadDisponible = cantidadDisponible;
        this.precio = precio;
    }

    public String getCoodigo() {
        return coodigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public CategoriaEquipo getCategoriaCompatibilidad() {
        return categoriaCompatibilidad;
    }

    public Empresa getOwnedByTechRepair() {
        return ownedByEmpresa;
    }

    public boolean hayDisponabilidad(int cantidad){
         return cantidadDisponible>=cantidad;
    }
    public void descontar(int cantidad){
        if (!hayDisponabilidad(cantidad)){
            throw new IllegalStateException("no hay suficientes unidades de: " +nombre); //se lanza excepcion si no hay suficientes undidades
        }
        cantidadDisponible-= cantidad;

    }

    public void reponer(int cantidad){
        cantidadDisponible+=cantidad;
        }

        public boolean esCompatibleConEquipo(Equipo equipo){
        return equipo.getCategoria()== categoriaCompatibilidad;
    }

    public boolean esCritico(){
        return cantidadDisponible<5;
    }


}

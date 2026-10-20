package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Empresa {
    private final String nombre;
    private final String nit;
    private final String ciudad;

    private final List<Cliente> listaClientes= new ArrayList<>();
    private final  List<Tecnico> listaTecnicos= new ArrayList<>();
    private  final  List<Repuesto> listaRepuestos= new ArrayList<>();
    private final  List<OrdenServicio> listaOrdenes= new ArrayList<>();
    private final  List<Servicio> listaServicios= new ArrayList<>();

    public Empresa(String nombre, String nit, String ciudad) {
        this.nombre = nombre;
        this.nit = nit;
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getCiudad() {
        return ciudad;
    }

    //CRUDS

    //Cliente
    public String registrarCliente(Cliente cliente){
        Optional<Cliente> clienteEncontrado= buscarCliente(cliente.getDocumentoIdentidad());
        if (clienteEncontrado.isPresent()){
            return "Ya hiciste un cliente registrado con ese  documento de identidad";

        }else{
            listaClientes.add(cliente);
            return"El cliente ha sido registrado exitosamente";

        }
    }
    public Optional<Cliente> buscarCliente(String documentoIdentidad){
        return listaClientes.stream().filter
                (cliente->cliente.getDocumentoIdentidad().equals(documentoIdentidad)).findFirst();
    }
    public boolean actualizarCliente(String documentoIdentidad, String nombreNuevo, String telefonoNuevo,
                                     String correoNuevo, String direccionNuevo) {
        Optional<Cliente> clienteEncontrado = buscarCliente(documentoIdentidad);
        if (clienteEncontrado.isPresent()) {
            Cliente cliente = clienteEncontrado.get();
            cliente.setNombreCompleto(nombreNuevo);
            cliente.setTelefono(telefonoNuevo);
            cliente.setCorreo(correoNuevo);
            cliente.setDireccion(direccionNuevo);
            return true;
        } else {
            return false;
        }
    }
    public boolean eliminarCliente(String documentoIdentidad) {
        Optional<Cliente> clienteEncontrado = buscarCliente(documentoIdentidad);

        if (clienteEncontrado.isEmpty()) {
            return false;
        }
        Cliente cliente = clienteEncontrado.get();
        if (listaOrdenes.stream().anyMatch(o -> o.getCliente() == cliente)) {
            throw new IllegalStateException("No se puede eliminar: el cliente tiene órdenes registradas");
        }
        return listaClientes.remove(cliente);
    }




    //Equipo
    public boolean agregarEquipo(String documentoCliente, Equipo equipo) {
        Optional<Cliente> cliente = buscarCliente(documentoCliente);
        if (cliente.isEmpty() || equipo == null || buscarEquipo(equipo.getCodigo()).isPresent()) {
            return false;
        }
        cliente.get().agregarEquipo(equipo);
        return true;
    }

    public Optional<Equipo> buscarEquipo(String codigo) {
        return listaClientes.stream()
                .map(c -> c.buscarEquipo(codigo))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public boolean actualizarEquipo(String codigo, String numeroSerieNuevo, String marcaNueva,
                                    String modeloNuevo, int anioNuevo, String estadoFisicoNuevo) {
        Optional<Equipo> equipoEncontrado = buscarEquipo(codigo);
        if (equipoEncontrado.isPresent()) {
            Equipo equipo = equipoEncontrado.get();
            equipo.setNumeroSerie(numeroSerieNuevo);
            equipo.setMarca(marcaNueva);
            equipo.setModelo(modeloNuevo);
            equipo.setAnioFabricacion(anioNuevo);
            equipo.setEstadoFisico(estadoFisicoNuevo);
            return true;
        } else {
            return false;
        }
    }
    public void eliminarEquipo(String codigo) {
        Optional<Equipo> equipoEncontrado = buscarEquipo(codigo);

        equipoEncontrado.ifPresent(equipo -> {
            // Si el equipo aparece en algún detalle de cualquier orden, no se puede eliminar
            for (OrdenServicio orden : listaOrdenes) {
                for (DetalleServicio d : orden.getListaDetalles()) {
                    if (d.getEquipo() == equipo) {
                        throw new IllegalStateException("El equipo tiene servicios registrados y no puede eliminarse");
                    }
                }
            }

            // Buscamos al cliente dueño con esDuenoDe y le pedimos que quite el equipo
            for (Cliente cliente : listaClientes) {
                if (cliente.esDuenoDe(equipo)) {
                    cliente.eliminarEquipo(equipo);
                }
            }
        });
    }
    //Tecnicos

    public String registrarTecnico(Tecnico tecnico){
        Optional<Tecnico> tecnicoEncontrado= buscarTecnico(tecnico.getCodigo());
        if (tecnicoEncontrado.isPresent()){
            return "Ya hiciste un tecnico registrado con ese  codigo";

        }else{
            listaTecnicos.add(tecnico);
            return"El tecnico ha sido registrado exitosamente";
        }
    }
    public Optional<Tecnico> buscarTecnico(String codigo){
        return listaTecnicos.stream().filter
                (tecnico->tecnico.getCodigo().equals(codigo)).findFirst();
    }
    public boolean actualizarTecnico(String codigo, String nombreNuevo, String documentoIdentidadNuevo
                                     , EstadoTecnico estadoNuevo) {
        Optional<Tecnico> tecnicoEncontrado= buscarTecnico(codigo);
        if (tecnicoEncontrado.isPresent()) {
            Tecnico tecnico = tecnicoEncontrado.get();
            tecnico.setNombre(nombreNuevo);
            tecnico.setDocumentoIdentidad(documentoIdentidadNuevo);
            tecnico.setEstado(estadoNuevo);
            return true;
        } else {
            return false;
        }
    }
    public void eliminarTecnico(String codigo) {
        Optional<Tecnico> tecnicoEncontrado= buscarTecnico(codigo);

        tecnicoEncontrado.ifPresent(tecnico -> {
            listaOrdenes.stream()
                    .flatMap(o -> o.getListaDetalles().stream())
                    .filter(d -> d.getTecnico() == tecnico)
                    .findAny()
                    .ifPresent(d -> {
                        throw new IllegalStateException("El técnico tiene trabajos asignados y no puede eliminarse");
                    });


            listaTecnicos.remove(tecnico);
        });
    }
    //Repuesto
    public String registrarRepuesto(Repuesto repuesto){
        Optional<Repuesto> repuestoEncontrado= buscarRespuesto(repuesto.getCoodigo());
        if (repuestoEncontrado.isPresent()){
            return "Ya hiciste un repuesto registrado con ese  codigo";
        }else{
            listaRepuestos.add(repuesto);
            return"El repuesto ha sido registrado exitosamente";
        }
    }
    public Optional<Repuesto> buscarRespuesto(String codigo){
        return listaRepuestos.stream().filter
                (r->r.getCoodigo().equals(codigo)).findFirst();
    }
    public boolean actualizarPrecioRepuesto(String codigo, double precioNuevo) {
        Optional<Repuesto> repuestoEncontrado = buscarRespuesto(codigo);
        if (repuestoEncontrado.isPresent() && precioNuevo >= 0) {
            repuestoEncontrado.get().setPrecio(precioNuevo);
            return true;
        } else {
            return false;
        }
    }
    public boolean reponerRepuesto(String codigo, int cantidad) {
        Optional<Repuesto> repuestoEncontrado = buscarRespuesto(codigo);
        if (repuestoEncontrado.isPresent() && cantidad > 0) {
            repuestoEncontrado.get().reponer(cantidad);
            return true;
        } else {
            return false;
        }
    }
    public void eliminarRepuesto(String codigo) {
        Optional<Repuesto> repuestoEncontrado = buscarRespuesto(codigo);
        repuestoEncontrado.ifPresent(repuesto -> listaRepuestos.remove(repuesto));
    }
    //Ordenes
    public String crearOrden(String codigo, String documentoCliente, String observaciones) {
        if (buscarOrden(codigo).isPresent()) {
            return "Ya existe una orden registrada con ese código";
        }

        Optional<Cliente> clienteEncontrado = buscarCliente(documentoCliente);
        if (clienteEncontrado.isEmpty()) {
            return "No existe un cliente con ese documento de identidad";
        }

        listaOrdenes.add(new OrdenServicio(codigo, LocalDate.now(), observaciones, EstadoOrden.ABIERTA, clienteEncontrado.get(), new ArrayList<>(),this));
        return "La orden ha sido creada exitosamente";
    }

    private Optional<OrdenServicio> buscarOrden(String codigo) {
        return listaOrdenes.stream().filter
                (o->o.getCodigo().equals(codigo)).findFirst();
    }
    public void eliminarOrden(String codigo) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigo);

        ordenEncontrada.ifPresent(orden -> {
            if (orden.getEstado() != EstadoOrden.ABIERTA) {
                throw new IllegalStateException("Solo se pueden eliminar órdenes abiertas");
            }
            listaOrdenes.remove(orden);
        });
    }
    //Servicios
    public boolean agregarServicio(Servicio servicio) {
        if (servicio == null || buscarServicio(servicio.getCodigo()).isPresent()) {
            return false;
        }
        servicio.setOwnedByEmpresa(this);
        return listaServicios.add(servicio);
    }

    public Optional<Servicio> buscarServicio(String codigo) {
        return listaServicios.stream()
                .filter(s -> s.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public boolean eliminarServicio(String codigo) {
        Optional<Servicio> encontrado = buscarServicio(codigo);
        if (encontrado.isEmpty()) {
            return false;
        }
        Servicio servicio = encontrado.get();
        for (OrdenServicio orden : listaOrdenes) {
            for (DetalleServicio d : orden.getListaDetalles()) {
                if (d.getServicio() == servicio) {
                    throw new IllegalStateException("No se puede eliminar: el servicio ya se usa en la orden "
                            + orden.getCodigo());
                }
            }
        }
        return listaServicios.remove(servicio);
    }


    //Metodos privados

    private Cliente obtenerCliente(String documento) {
        return buscarCliente(documento)
                .orElseThrow(() -> new IllegalArgumentException("No existe el cliente " + documento));
    }

    private Equipo obtenerEquipo(String codigo) {
        return buscarEquipo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el equipo " + codigo));
    }

    private Tecnico obtenerTecnico(String codigo) {
        return buscarTecnico(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el técnico " + codigo));
    }

    private Repuesto obtenerRepuesto(String codigo) {
        return buscarRespuesto(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el repuesto " + codigo));
    }

    private OrdenServicio obtenerOrden(String codigo) {
        return buscarOrden(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe la orden " + codigo));
    }

    private Servicio obtenerServicio(String codigo) {
        return buscarServicio(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el servicio " + codigo));
    }

    private DetalleServicio obtenerDetalle(OrdenServicio orden, String codigo) {
        return orden.buscarDetalle(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe el detalle " + codigo));
    }
    public DetalleServicio agregarDetalle(String codOrden, String codDetalle, String codEquipo,
                                          String codServicio, String descripcionFalla) {
        OrdenServicio ordenServicio = obtenerOrden(codOrden); //busqueda de objetos
        Equipo equipo = obtenerEquipo(codEquipo);
        Servicio servicio = obtenerServicio(codServicio);

        DetalleServicio detalleServicio = new DetalleServicio(codDetalle, descripcionFalla, 0, "",
                EstadoDetalle.PENDIENTE, equipo, servicio, null, new ArrayList<>(), ordenServicio);

       ordenServicio.agregarDetalle(detalleServicio);

        return detalleServicio;
    }

    public void asignarTecnico(String codOrden, String codDetalle, String codTecnico){
        DetalleServicio detalleServicio= obtenerDetalle(obtenerOrden(codOrden), codDetalle);
        Tecnico tecnico=obtenerTecnico(codTecnico);
        detalleServicio.asignarTecnico(tecnico);
    }
    public void registrarRepuestoUtilizado(String codOrden, String codDetalle, String codTecnico, int cantidad){
        DetalleServicio detalleServicio=obtenerDetalle(obtenerOrden(codOrden), codDetalle);
        Repuesto repuesto= obtenerRepuesto(codTecnico);
        detalleServicio.agregarRepuestoUtilizado(repuesto, cantidad);
    }
    public void registrarHorasTrabajo(String codOrden,String codDetalle, double horas){
        DetalleServicio detalleServicio= obtenerDetalle(obtenerOrden(codOrden), codDetalle);
            detalleServicio.registrarHoras(horas);
    }
    public double calcularValorDetalle(String codOrden, String cosDetalle){
        DetalleServicio detalleServicio= obtenerDetalle(obtenerOrden(codOrden),cosDetalle);
        return detalleServicio.calcularValor();
    }
    public double calcularTotalOrden(String codOrden){
        return obtenerOrden(codOrden).calcularTotal();
    }
    public void cambiarEstadoOrden(String codOrden, String codDetalle, EstadoDetalle estado, String observacion){
        DetalleServicio detalleServicio=obtenerDetalle(obtenerOrden(codOrden), codDetalle);
        detalleServicio.cambiarEstado(estado, observacion);
    }
    public void finalizarOrden(String codOrden){
        obtenerOrden(codOrden).finalizar();
    }
    public String consultarOrdenesClientes(String documento){
        Cliente cliente= obtenerCliente(documento);
        String mensaje="";
        boolean tieneOrdenes= false;
        for (OrdenServicio ordenServicio: listaOrdenes){
            if (ordenServicio.getCliente()==cliente){
                tieneOrdenes= true;
                mensaje+=
                        "Orden " + ordenServicio.getCodigo()
                                + " Fecha: " + ordenServicio.getFecha()
                                + "  Estado: " + ordenServicio.getEstado()
                                + "  Valor total: $" + ordenServicio.calcularTotal() ;

                for (DetalleServicio detalleServicio : ordenServicio.getListaDetalles()) {
                    mensaje +=  detalleServicio.getCodigo()
                            + " Equipo: " + detalleServicio.getEquipo().getCodigo()
                            + " Servicio: " + detalleServicio.getServicio().getNombre()
                            + " Estado: " + detalleServicio.getEstado()
                            + " Valor: $" + detalleServicio.calcularValor() + "\n";
                }
            }
        }

        if (!tieneOrdenes) {
            return "El cliente " + documento + " no tiene órdenes registradas.";
        }
        return mensaje;
    }

    public String consultarTrabajosTecnico(String codigoTecnico) {
        Optional<Tecnico> tecnicoEncontrado = buscarTecnico(codigoTecnico);
        if (tecnicoEncontrado.isEmpty()) {
            return "No existe un técnico con ese código";
        }
        Tecnico tecnico = tecnicoEncontrado.get();

        String mensaje = "Técnico: " + tecnico.getNombre() + " (" + tecnico.getCodigo() + ")\n\n";
        mensaje += listarDetallesPorEstado(tecnico, EstadoDetalle.PENDIENTE);
        mensaje += listarDetallesPorEstado(tecnico, EstadoDetalle.EN_PROCESO);
        mensaje += listarDetallesPorEstado(tecnico, EstadoDetalle.FINALIZADO);
        mensaje += listarDetallesPorEstado(tecnico, EstadoDetalle.CANCELADO);
        return mensaje;
    }
    //metodo auxiliar del anterior
    private String listarDetallesPorEstado(Tecnico tecnico, EstadoDetalle estado) {
        String mensaje = estado + ":\n";
        boolean hayDetalles = false;
        for (DetalleServicio detalleServicio : tecnico.getListaDetallesAsignados()) {
            if (detalleServicio.getEstado() == estado) {
                mensaje += "   - " + detalleServicio.getCodigo() + detalleServicio.getServicio().getNombre()
                        + "  Equipo: " + detalleServicio.getEquipo().getCodigo() + "\n";
                hayDetalles = true;
            }
        }
        if (!hayDetalles) {
            mensaje += "   (ninguno)\n";
        }
        return mensaje + "\n";
    }
    public List<Equipo> consultarEquiposPendientes(){
        List<Equipo> equiposPendientes= new ArrayList<>();
        for (OrdenServicio ordenServicio: listaOrdenes){
            for (DetalleServicio detalleServicio: ordenServicio.getListaDetalles()){
                if (detalleServicio.estaPendiente()&& !equiposPendientes.contains(detalleServicio.getEquipo())){
                    equiposPendientes.add(detalleServicio.getEquipo());
                }
            }
        }
        return equiposPendientes;
    }

    public String generarReportesServicios(){
        String mensaje= "Reporte Servicios \n";

        for (TipoServicio tipoServicio:TipoServicio.values()){
            int cantidad=0;
            double total= 0;
            for (OrdenServicio ordenServicio: listaOrdenes){
                for (DetalleServicio detalleServicio: ordenServicio.getListaDetalles()){
                    if (detalleServicio.getServicio().getTipo()==tipoServicio){
                        cantidad++;
                        total+= detalleServicio.calcularValor();
                    }
                }
            }
            mensaje += tipoServicio + ": cantidad=" + cantidad
                    + ", total facturado=$" +  total + "\n";
        }
        return mensaje;
    }
    public String generarReporteTecnicos(){
        String mensaje= "";
        for (Tecnico tecnico: listaTecnicos){
            mensaje+= tecnico.getCodigo() + " - " + tecnico.getNombre()
                    + ": intervenciones finalizadas=" + tecnico.contarIntervencionesFinalizadas()
                    + ", valor servicios=$" +  tecnico.calcularValorServiciosAtendidos()
                    + "\n";
        }
        return mensaje;
    }
    public List<Repuesto> consultarInvantarioCritico(){
        return listaRepuestos.stream().filter(Repuesto::esCritico).toList();
    }








}



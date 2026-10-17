package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;
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
            listaClientes.add(cliente);
            return"El cliente ha sido registrado exitosamente";

        }else{
            return "Ya hiciste un cliente registrado con ese  documento de identidad";
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
    public void eliminarCliente(String documentoIdentidad) {
        Optional<Cliente> clienteEncontrado = buscarCliente(documentoIdentidad);

        clienteEncontrado.ifPresent(cliente -> {
            listaOrdenes.stream()
                    .filter(o -> o.getCliente() == cliente)
                    .findAny()//no cambia la lista principal
                    .ifPresent(o -> {
                        throw new IllegalStateException("El cliente tiene órdenes registradas y no puede eliminarse");
                    });

            listaClientes.remove(cliente);
        });
    }




    //Equipo
    public String registrarEquipo(String documentoIdentidad, Equipo equipo) {
        Optional<Cliente> clienteEncontrado = buscarCliente(documentoIdentidad);
        if (clienteEncontrado.isEmpty()) {
            return "No existe un cliente con ese documento de identidad";
        }

        if (buscarEquipo(equipo.getCodigo()).isPresent()) {
            return "Ya existe un equipo con ese código";
        }

        Optional<Equipo> serieRepetida = listaClientes.stream()
                .flatMap(c -> c.getListaEquipos().stream()) //flatmap junta los elementos de los dos stream
                .filter(e -> e.getNumeroSerie().equals(equipo.getNumeroSerie()))
                .findAny();
        if (serieRepetida.isPresent()) {
            return "Ya existe un equipo con ese número de serie";
        }

        clienteEncontrado.get().agregarEquipo(equipo);
        return "El equipo ha sido registrado exitosamente";
    }
    public Optional<Equipo> buscarEquipo(String codigo) {
        return listaClientes.stream()
                .flatMap(cliente -> cliente.getListaEquipos().stream())
                .filter(equipo -> equipo.getCodigo().equals(codigo))
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
            return "Ya hiciste un cliente registrado con ese  documento de identidad";

        }else{
            listaTecnicos.add(tecnico);
            return"El cliente ha sido registrado exitosamente";
        }
    }
    public Optional<Tecnico> buscarTecnico(String codigo){
        return listaTecnicos.stream().filter
                (tecnico->tecnico.getCodigo().equals(codigo)).findFirst();
    }
    public boolean actualizarTecnico(String codigo, String nombreNuevo, String documentoIdentidadNuevo,
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
            return "Ya hiciste un cliente registrado con ese  documento de identidad";
        }else{
            listaRepuestos.add(repuesto);
            return"El cliente ha sido registrado exitosamente";
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

        listaOrdenes.add(new OrdenServicio(codigo, observaciones, clienteEncontrado.get(), this));
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
    public DetalleServicio agregarDetalle(String codOrden, String codDetalle, String codEquipo,
                                          String codServicio, String descripcionFalla) {
        OrdenServicio orden = obtenerOrden(codOrden);
        Equipo equipo = obtenerEquipo(codEquipo);
        Servicio servicio = obtenerServicio(codServicio);

        DetalleServicio detalle = new DetalleServicio(codDetalle, equipo, servicio, descripcionFalla);
        orden.agregarDetalle(detalle);
        return detalle;
    }


}



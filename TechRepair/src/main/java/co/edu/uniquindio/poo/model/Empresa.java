package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.Optional;

public class Empresa {
    private final String nombre;
    private final String nit;
    private final String ciudad;

    private final <Cliente> listaClientes= new ArrayList<>();
    private final  <Tecnico> listaTecnicos= new ArrayList<>();
    private  final  <Respuesto> listaRepuestos= new ArrayList<>();
    private final  <OrdenServicio> listaOrdenes= new ArrayList<>();
    private final  <Servivio> listaServicios= new ArrayList<>();

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
        if (clienteEncontrado.isPresent){
            listaCliente.add(cliente);
            return"El cliente ha sido registrado exitosamente";
            return "Ya hiciste un cliente registrado con ese  documento de identidad";
        }else{
            return "Ya hiciste un cliente registrado con ese  documento de identidad";
        }
    }
    public Optional<Cliente> buscarCliente(String documentoIdentidad){
      return listaClientes.stream().filter(c->documentoIdentidad.equals(cliente.getDocumentoIdentidad())).findFirst;
        }



    }

}

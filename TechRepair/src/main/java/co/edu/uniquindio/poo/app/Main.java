package co.edu.uniquindio.poo.app;

import co.edu.uniquindio.poo.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        Empresa empresa = new Empresa("TechRepair", "012", "Armenia");

        cargarDatosPrueba(empresa);

        String menu = "TECH REPAIR\n\n"

                + "1. Registrar Cliente\n"
                + "2. Registrar equipo\n"
                + "3. Registrar tecnico\n"
                + "4. Registrar repuesto\n"
                + "5. Crear Orden de servicio\n"
                + "6. Agregar detalle a una orden\n"
                + "7. Asignar tecnico\n"
                + "8. Asignar tecnico\n"
                + "9. Registrar repuesto utilizado\n"
                + "10. Calcular valor de un detalle\n"
                + "11. Actualizar estado de un detalle\n"
                + "12. Finalizar una orden\n"
                + "13. Consultar ordenes de un cliente\n"
                + "14. Consultar trabajos de un tecnico\n"
                + "15. Consultar equipos pendientes\n"
                + "16. Generar reportes pendientes\n"
                + "17. Generar reporte de tecnicos\n"
                + "18. Consultar inventario critico\n"
                + "0. Salir\n"
                + "DIGITE UNA OPCIÓN: \n ";

        int opcion;

        do {
            opcion = leerEntero(menu);

            switch (opcion) {
                case 1:
                    registrarCliente(empresa);
                    break;
                case 2:
                    registrarEquipo(empresa);
                    break;
                case 3:
                   registrarTecnico(empresa);
                    break;
                case 4:
                    registrarRepuesto(empresa);
                    break;
                case 5:
                    crearOrden(empresa);
                    break;
                case 6:
                    agregarDetalle(empresa);
                    break;
                case 7:
                    asignarTecnico(empresa);
                    break;
                case 8:
                    registrarRepuestoUtilizado(empresa);
                    break;
                case 9:
                    calcularValorDetalle(empresa);
                    break;
                case 10:
                    calcularTotalOrden(empresa);
                    break;
                case 11:
                    cambiarEstadoDetalle(empresa);
                    break;
                case 12:
                    finalizarOrden(empresa);
                    break;
                case 13:
                    consultarOrdenesCliente(empresa);
                    break;
                case 14:
                    consultarTrabajosTecnico(empresa);
                    break;
                case 15:
                    consultarEquiposPendientes(empresa);
                    break;
                case 16:
                  generarReporteServicios(empresa);
                    break;
                case 17:
                    generarReporteTecnicos(empresa);
                    break;
                case 18:
                    consultarInventarioCritico(empresa);
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Hasta luego.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no valida");
                    break;
            }
        } while (opcion != 0);


    }

    //METODOS TABLA
    private static void registrarCliente(Empresa empresa) {
        String documento = leerTexto("Documento de identidad: ");
        String nombre = leerTexto("Nombre completo: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        String direccion = leerTexto("Dirección: ");

        JOptionPane.showMessageDialog(null,
                empresa.registrarCliente(new Cliente(documento, nombre, telefono, correo, direccion,
                        new ArrayList<>(),empresa)));
    }
        private static void registrarEquipo(Empresa empresa) {
            String documentoIdentidad = leerTexto("Documento del cliente dueño:");

            CategoriaEquipo categoria = elegirEnum("Categoría del equipo:", CategoriaEquipo.values());

            String codigo = leerTexto("Código del equipo:");
            String numeroSerie = leerTexto("Número de serie:");
            String marca = leerTexto("Marca:");
            String modelo = leerTexto("Modelo:");
            int anio = leerEntero("Año de fabricación:");
            String estadoFisico = leerTexto("Estado físico:");

            Equipo equipo;
            switch (categoria) {
                case COMPUTADORES_PORTATILES: {
                    Optional<Cliente> cliente = empresa.buscarCliente(documentoIdentidad);

                    // 2. Pedir solo los datos específicos del portatil que recibe el constructor
                    double tamanoPantalla = leerDouble("Tamaño de pantalla (pulgadas):");
                    int ram = leerEntero("Memoria RAM (GB):");
                    int almacenamiento = leerEntero("Almacenamiento (GB):");
                    boolean bateriaExtraible = leerSiNo("¿Tiene batería extraíble?");

                    // 3. Instanciar pasando exactamente los 11 parámetros que pide el constructor
                    equipo = new ComputadorPortatil(codigo, numeroSerie, marca, modelo, anio, estadoFisico, cliente.orElse(null), tamanoPantalla,
                            ram, almacenamiento, bateriaExtraible);
                    break;
                }
                case COMPUTADORES_ESCRITORIO: {
                    Optional<Cliente> cliente = empresa.buscarCliente(documentoIdentidad);

                    String procesador = leerTexto("Tipo de procesador:");
                    int ram = leerEntero("Memoria RAM (GB):");
                    int almacenamiento = leerEntero("Almacenamiento (GB):");
                    boolean tarjetaDedicada = leerSiNo("¿Tiene tarjeta gráfica dedicada?");

                    equipo = new ComputadorEscritorio(codigo, numeroSerie, marca, modelo, anio,
                            estadoFisico, cliente.orElse(null), procesador, ram, almacenamiento, tarjetaDedicada);
                    break;
                }
                case TELEFONO_INTELIGENTE: {
                    Optional<Cliente> cliente = empresa.buscarCliente(documentoIdentidad);

                    String sistemaOperativo = leerTexto("Sistema operativo:");
                    int almacenamiento = leerEntero("Almacenamiento (GB):");
                    int bateria = leerEntero("Capacidad de batería (mAh):");
                    boolean compatible5G = leerSiNo("¿Es compatible con 5G?");

                    equipo = new TelefonoInteligente(codigo, numeroSerie, marca, modelo, anio,
                            estadoFisico, cliente.orElse(null), sistemaOperativo, almacenamiento,
                            bateria, compatible5G);
                    break;
                }
                default:
                    throw new IllegalStateException("Categoría no válida");
            }

            JOptionPane.showMessageDialog(null, empresa.agregarEquipo(documentoIdentidad, equipo));
        }
    private static void registrarTecnico(Empresa empresa) {
        String codigo = leerTexto("Código del técnico:");
        String nombre = leerTexto("Nombre completo:");
        String documentoIdentidad = leerTexto("Documento de identidad:");
        Especialidad especialidad = elegirEnum("Especialidad", Especialidad.values());
        EstadoTecnico estado = elegirEnum("Estado laboral", EstadoTecnico.values());

        JOptionPane.showMessageDialog(null,
                empresa.registrarTecnico(new Tecnico(codigo, nombre, documentoIdentidad, especialidad,
                        estado, new ArrayList<>(), empresa)));
    }



    private static void registrarRepuesto(Empresa empresa) {
    String codigo = leerTexto("Código del repuesto:");
    String nombre = leerTexto("Nombre:");
    String descripcion = leerTexto("Descripción:");
    double precio = leerDouble("Precio unitario:");
    int cantidad = leerEntero("Existencias iniciales:");
    CategoriaEquipo categoria = elegirEnum("Categoría de compatibilidad", CategoriaEquipo.values());

    // Se instancia pasando los 7 parámetros en el orden exacto del constructor:
    // (coodigo, nombre, descripcion, categoriaCompatibilidad, ownedByEmpresa, cantidadDisponible, precio)
    JOptionPane.showMessageDialog(null,
            empresa.registrarRepuesto(new Repuesto(codigo, nombre, descripcion, categoria, empresa,
                    cantidad, precio)));
}

    private static void crearOrden(Empresa empresa) {
        String codigo = leerTexto("Código de la orden:");
        String documento = leerTexto("Documento del cliente:");
        String observaciones = leerTexto("Observaciones:");

        JOptionPane.showMessageDialog(null,empresa.crearOrden(codigo, documento, observaciones));
    }

    private static void agregarDetalle(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");
        String codDetalle = leerTexto("Código del detalle:");
        String codEquipo = leerTexto("Código del equipo:");
        String codServicio = leerTexto("Código del servicio:");
        String falla = leerTexto("Falla reportada:");

        DetalleServicio detalle = empresa.agregarDetalle(codOrden, codDetalle, codEquipo, codServicio, falla);
        JOptionPane.showMessageDialog(null,"Detalle " + detalle.getCodigo() + " agregado a la orden " + codOrden + ".");
    }

    private static void asignarTecnico(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");
        String codDetalle = leerTexto("Código del detalle:");
        String codTecnico = leerTexto("Código del técnico:");

        empresa.asignarTecnico(codOrden, codDetalle, codTecnico);
        JOptionPane.showMessageDialog(null,"Técnico asignado correctamente.");
    }

    private static void registrarRepuestoUtilizado(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");
        String codDetalle = leerTexto("Código del detalle:");
        String codRepuesto = leerTexto("Código del repuesto:");
        int cantidad = leerEntero("Cantidad utilizada:");

        empresa.registrarRepuestoUtilizado(codOrden, codDetalle, codRepuesto, cantidad);
        JOptionPane.showMessageDialog(null ,"Repuesto registrado y descontado del inventario.");
    }

    private static void calcularValorDetalle(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");
        String codDetalle = leerTexto("Código del detalle:");

        double valor = empresa.calcularValorDetalle(codOrden, codDetalle);
        JOptionPane.showMessageDialog(null,String.format("Valor del detalle: $%.0f", valor));
    }

    private static void calcularTotalOrden(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");

        double total = empresa.calcularTotalOrden(codOrden);
        JOptionPane.showMessageDialog(null,String.format("Total de la orden: $%.0f", total));
    }

    private static void cambiarEstadoDetalle(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");
        String codDetalle = leerTexto("Código del detalle:");
        EstadoDetalle estado = elegirEnum("Nuevo estado", EstadoDetalle.values());
        String observaciones = leerTexto("Observaciones técnicas (puede quedar vacío):");

        empresa.cambiarEstadoOrden(codOrden, codDetalle, estado, observaciones);
        JOptionPane.showMessageDialog(null,"Estado actualizado a " + estado + ".");
    }

    private static void finalizarOrden(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");

        empresa.finalizarOrden(codOrden);
        JOptionPane.showMessageDialog(null,"La orden " + codOrden + " fue finalizada.");
    }

    private static void consultarOrdenesCliente(Empresa empresa) {
        String documento = leerTexto("Documento del cliente:");
        JOptionPane.showMessageDialog(null,empresa.consultarOrdenesClientes(documento));
    }

    private static void consultarTrabajosTecnico(Empresa empresa) {
        String codigo = leerTexto("Código del técnico:");
        JOptionPane.showMessageDialog(null,empresa.consultarTrabajosTecnico(codigo));
    }

    private static void consultarEquiposPendientes(Empresa empresa) {
        List<Equipo> pendientes = empresa.consultarEquiposPendientes();

        if (pendientes.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No hay equipos pendientes.");
            return;
        }
        String texto = "Equipos pendientes:\n";
        for (Equipo equipo : pendientes) {
            texto += "  - " + equipo.getCodigo() + "\n";
        }
        JOptionPane.showMessageDialog(null,texto);
    }
    private static void generarReporteServicios(Empresa empresa) {
        String reporte = empresa.generarReportesServicios();
        JOptionPane.showMessageDialog(null, reporte);
    }

    private static void generarReporteTecnicos(Empresa empresa) {
        String reporte = empresa.generarReporteTecnicos();
        JOptionPane.showMessageDialog(null, reporte);
    }

    private static void consultarInventarioCritico(Empresa empresa) {
        List<Repuesto> criticos = empresa.consultarInvantarioCritico();

        if (criticos.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No hay repuestos en estado crítico.");
            return;
        }
        String texto = "Repuestos en estado crítico:\n";
        for (Repuesto repuesto : criticos) {
            texto += "  - " + repuesto.getCoodigo() + " " + repuesto.getNombre()
                    + " (existencias: " + repuesto.getCantidadDisponible() + ")\n";
        }
        JOptionPane.showMessageDialog(null,texto);
    }

    private static void registrarHorasTrabajo(Empresa empresa) {
        String codOrden = leerTexto("Código de la orden:");
        String codDetalle = leerTexto("Código del detalle:");
        double horas = leerDouble("Horas trabajadas:");

        empresa.registrarHorasTrabajo(codOrden, codDetalle, horas);
        JOptionPane.showMessageDialog(null,"Horas registradas.");
    }


    //Lecturas
    private static String leerTexto(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje);
        if (texto == null) {
            throw new IllegalStateException("Operación cancelada");
        }
        return texto.trim();
    }


    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                mostrarError("Debe ingresar un número entero.");
            }
        }
    }


    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                mostrarError("Debe ingresar un número.");
            }
        }
    }


    private static boolean leerSiNo(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }

    private static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //Equipos
    private static <T> T elegirEnum(String titulo, T[] valores) {
        Object elegido = JOptionPane.showInputDialog(null, "Seleccione una opción:", titulo,
                JOptionPane.QUESTION_MESSAGE, null, valores, valores[0]);
        if (elegido == null) {
            throw new IllegalStateException("Operación cancelada");
        }
        for (T valor : valores) {
            if (valor == elegido) {
                return valor;
            }
        }
        throw new IllegalStateException("Opción no válida");
    }
    private static void cargarDatosPrueba(Empresa empresa) {
        try {
            // 1. REGISTRO DE NUEVOS CLIENTES
            Cliente c1 = new Cliente("1001", "Samuel Gómez", "3101234567", "samuel@email.com", "Calle 10 #14-20", new ArrayList<>(), empresa);
            Cliente c2 = new Cliente("1002", "María Cardona", "3207654321", "maria@email.com", "Av. Bolivar #5-12", new ArrayList<>(), empresa);
            Cliente c3 = new Cliente("1003", "Valeria Jiménez", "3001112233", "valeria@email.com", "Cra 19 #22-10", new ArrayList<>(), empresa);

            empresa.registrarCliente(c1);
            empresa.registrarCliente(c2);
            empresa.registrarCliente(c3);

            // 2. REGISTRO DE EQUIPOS (Asignados a los nuevos clientes)
            Equipo eq1 = new ComputadorPortatil("EQ01", "SN-PORT-001", "Lenovo", "ThinkPad T14", 2022, "Bueno", c1, 14.0, 16, 512, false);
            Equipo eq2 = new ComputadorEscritorio("EQ02", "SN-PC-002", "ASUS", "ROG Strix", 2023, "Excelente", c2, "Intel i7", 32, 1024, true);
            Equipo eq3 = new TelefonoInteligente("EQ03", "SN-TEL-003", "Samsung", "Galaxy S23", 2023, "Pantalla rayada", c1, "Android", 256, 3900, true);
            Equipo eq4 = new ComputadorPortatil("EQ04", "SN-PORT-004", "HP", "Pavilion 15", 2021, "Desgastado", c3, 15.6, 8, 256, true);

            empresa.agregarEquipo("1001", eq1);
            empresa.agregarEquipo("1002", eq2);
            empresa.agregarEquipo("1001", eq3);
            empresa.agregarEquipo("1003", eq4);

            // 3. REGISTRO DE NUEVOS TÉCNICOS
            Tecnico t1 = new Tecnico("TEC01", "Nicolás Cárdenas", "80111", Especialidad.COMPUTADORES, EstadoTecnico.ACTIVO, new ArrayList<>(), empresa);
            Tecnico t2 = new Tecnico("TEC02", "Violeta Morales", "80222", Especialidad.SOPORTE_GENERAL, EstadoTecnico.INACTIVO, new ArrayList<>(), empresa);

            empresa.registrarTecnico(t1);
            empresa.registrarTecnico(t2);

            // 4. REGISTRO DE REPUESTOS
            Repuesto r1 = new Repuesto("REP01", "SSD NVMe 512GB", "Disco estado sólido", CategoriaEquipo.COMPUTADORES_PORTATILES, empresa, 10, 180000.0);
            Repuesto r2 = new Repuesto("REP02", "Módulo RAM 8GB", "Memoria SODIMM", CategoriaEquipo.COMPUTADORES_PORTATILES, empresa, 2, 95000.0);
            Repuesto r3 = new Repuesto("REP03", "Pantalla OLED", "Módulo de pantalla", CategoriaEquipo.TELEFONO_INTELIGENTE, empresa, 1, 450000.0);
            Repuesto r4 = new Repuesto("REP04", "Pasta Térmica", "Disipación alta", CategoriaEquipo.COMPUTADORES_ESCRITORIO, empresa, 15, 35000.0);

            empresa.registrarRepuesto(r1);
            empresa.registrarRepuesto(r2);
            empresa.registrarRepuesto(r3);
            empresa.registrarRepuesto(r4);

            // 5. ÓRDENES, DETALLES, TRABAJOS Y ESTADOS

            // --- ORDEN 01 (Finalizada) ---
            empresa.crearOrden("ORD01", "1001", "El portátil no enciende y el teléfono tiene la pantalla rota.");

            empresa.agregarDetalle("ORD01", "DET01", "EQ01", "SERV01", "Fallo en encendido / posibles memorias RAM");
            empresa.agregarDetalle("ORD01", "DET02", "EQ03", "SERV02", "Pantalla rota por caída");

            empresa.asignarTecnico("ORD01", "DET01", "TEC01"); // Asignado a Nicolás Cárdenas
            empresa.asignarTecnico("ORD01", "DET02", "TEC02"); // Asignado a Violeta Morales

            empresa.registrarHorasTrabajo("ORD01", "DET01", 3.0);
            empresa.registrarHorasTrabajo("ORD01", "DET02", 2.0);

            empresa.registrarRepuestoUtilizado("ORD01", "DET01", "REP01", 1);
            empresa.registrarRepuestoUtilizado("ORD01", "DET02", "REP03", 1);

            empresa.cambiarEstadoOrden("ORD01", "DET01", EstadoDetalle.FINALIZADO, "Instalación de SSD realizada con éxito.");
            empresa.cambiarEstadoOrden("ORD01", "DET02", EstadoDetalle.FINALIZADO, "Cambio de pantalla completado.");

            // --- ORDEN 02 (En Proceso) ---
            empresa.crearOrden("ORD02", "1002", "Mantenimiento preventivo general y cambio de pasta térmica.");

            empresa.agregarDetalle("ORD02", "DET03", "EQ02", "SERV01", "Calentamiento excesivo en procesador");

            empresa.asignarTecnico("ORD02", "DET03", "TEC01"); // Asignado a Nicolás Cárdenas
            empresa.registrarHorasTrabajo("ORD02", "DET03", 1.5);
            empresa.registrarRepuestoUtilizado("ORD02", "DET03", "REP04", 1);

            empresa.cambiarEstadoOrden("ORD02", "DET03", EstadoDetalle.EN_PROCESO, "Mantenimiento en ejecución.");

            // --- ORDEN 03 (Pendiente) ---
            empresa.crearOrden("ORD03", "1003", "Revisión de bisagra y mantenimiento general.");
            empresa.agregarDetalle("ORD03", "DET04", "EQ04", "SERV01", "Bisagra izquierda dura al abrir");

        } catch (Exception e) {
            System.out.println("Error al cargar los datos de prueba: " + e.getMessage());
        }
    }

    // Este metodo muestra varias opciones como botones para que el usuario elija una
    public static String elegirOpcion(String mensaje, String[] opciones) {
        String titulo = "Seleccione";
        String opcionPorDefecto = opciones[0];
        int indice = JOptionPane.showOptionDialog(null, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opcionPorDefecto);
        while (indice == -1) {
            String mensajeError = "Debe seleccionar una opcion.\n" + mensaje;
            indice = JOptionPane.showOptionDialog(null, mensajeError, titulo,
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opcionPorDefecto);
        }
        String opcionElegida = opciones[indice];
        return opcionElegida;
    }
}












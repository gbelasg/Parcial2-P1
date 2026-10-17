package co.edu.uniquindio.poo.app;

import co.edu.uniquindio.poo.model.Empresa;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
     Empresa empresa= new Empresa("TechRepair", "012", "Armenia");
    static void main() {

        Empresa empresa= new Empresa("TechRepair", "012", "Armenia");

         cargarDatosPrueba(empresa);

            String menu= "===TECH REPAIR===\n\n"

                    +"1. Registrar Cliente\n"
                    +"2. Registrar equipo\n"
                    +"3. Registrar tecnico\n"
                    +"4. Registrar repuesto\n"
                    +"5. Crear Orden de servicio\n"
                    +"6. Agregar detalle a una orden\n"
                    +"7. Asignar tecnico\n"
                    +"8. Asignar tecnico\n"
                    +"9. Registrar repuesto utilizado\n"
                    +"10. Calcular valor de un detalle\n"
                    +"11. Actualizar estado de un detalle\n"
                    +"12. Finalizar una orden\n"
                    +"13. Consultar ordenes de un cliente\n"
                    +"14. Consultar trabajos de un tecnico"
                    +"15. Consultar equipos pendientes\n"
                    +"16. Generar reportes pendientes\n"
                    + "17. Generar reporte de tecnicos\n"
                    +"18. Consultar inventario critico"
                    +"0. Salir"
                    +"DIGITE UNA OPCIÓN: ";

            int opcion;

            do {
                opcion= leerEntero(menu);

                switch (opcion){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opción no valida");
                        break;
                }
            } while (opcion!=0);



    }

    private static void cargarDatosPrueba(Empresa empresa) {
    }
}

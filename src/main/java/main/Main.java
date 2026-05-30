/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import javax.swing.JOptionPane;
import java.sql.Connection;
import acceso_datos.ConexionBD;
import acceso_datos.MetodosBD;

/**
 *
 * @author Admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection conn = ConexionBD.getConexion();
        if (conn == null) {
            JOptionPane.showMessageDialog(null,
                    "Error crítico: No se pudo establecer conexión con la Base de Datos.\nVerifique los servicios locales de MySQL.",
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opcionesMenuPrincipal = {
            "Módulo Clientes",
            "Módulo Videojuegos",
            "Módulo Alquileres",
            "Salir del Sistema"
        };

        boolean mantenerProgramaActivo = true;

        while (mantenerProgramaActivo) {
            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "SISTEMA VIDEO CLUB - MENÚ PRINCIPAL\nSeleccione un módulo para interactuar:",
                    "Video Club - Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesMenuPrincipal,
                    opcionesMenuPrincipal[0]
            );

            switch (seleccion) {
                case 0:
                    menuClientes();
                    break;
                case 1:
                    menuVideojuegos();
                    break;
                case 2:
                    menuAlquileres();
                    break;
                case 3: 
                case -1:
                    mantenerProgramaActivo = false;
                    JOptionPane.showMessageDialog(null, "Cerrando el sistema de Video Club. ¡Hasta luego!");
                    break;
            }
        }
    }

    private static void menuClientes() {
        String[] opciones = {"Listar Clientes", "Ingresar Cliente", "Volver al Menú Principal"};
        boolean enSubMenu = true;

        while (enSubMenu) {
            int seleccion = JOptionPane.showOptionDialog(null,
                    "MÓDULO CLIENTES\nSeleccione una operación:", "Gestión de Clientes",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == 0) {
                MetodosBD.listarClientes();
            } else if (seleccion == 1) {
                String cedula = JOptionPane.showInputDialog("Ingrese la Cédula (9 caracteres exactos):");
                if (cedula == null) {
                    continue;
                }
                String nombre = JOptionPane.showInputDialog("Ingrese el Nombre:");
                if (nombre == null) {
                    continue;
                }

                String apellido = JOptionPane.showInputDialog("Ingrese el Apellido:");
                if (apellido == null) {
                    continue;
                }

                String telefono = JOptionPane.showInputDialog("Ingrese el Teléfono (8 caracteres):");
                if (telefono == null) {
                    continue;
                }

                String correo = JOptionPane.showInputDialog("Ingrese el Correo Electrónico (Opcional):");
                if (correo == null) {
                    continue;
                }

                String direccion = JOptionPane.showInputDialog("Ingrese la Dirección Exacta:");
                if (direccion == null) {
                    continue;
                }

                if (cedula.trim().isEmpty() || nombre.trim().isEmpty() || apellido.trim().isEmpty() || direccion.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error: Cédula, Nombre, Apellido y Dirección son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                } else {
                    MetodosBD.ingresarCliente(cedula, nombre, apellido, telefono, correo, direccion);
                }
            } else {
                enSubMenu = false;
            }
        }
    }

    private static void menuVideojuegos() {
        String[] opciones = {"Listar Videojuegos", "Ingresar Videojuego", "Volver al Menú Principal"};
        boolean enSubMenu = true;

        while (enSubMenu) {
            int seleccion = JOptionPane.showOptionDialog(null,
                    "MÓDULO VIDEOJUEGOS\nSeleccione una operación:", "Gestión de Videojuegos",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == 0) {
                MetodosBD.listarVideojuegos();
            } else if (seleccion == 1) {
                try {
                    String inputCodigo = JOptionPane.showInputDialog("Ingrese el Código Numérico del Videojuego:");
                    if (inputCodigo == null) {
                        continue;
                    }
                    int codigo = Integer.parseInt(inputCodigo);

                    String nombre = JOptionPane.showInputDialog("Ingrese el Nombre del Videojuego:");
                    if (nombre == null) {
                        continue;
                    }

                    String descripcion = JOptionPane.showInputDialog("Ingrese la Descripción / Sinopsis:");
                    if (descripcion == null) {
                        continue;
                    }

                    String desarrollador = JOptionPane.showInputDialog("Ingrese el Desarrollador (Estudio):");
                    if (desarrollador == null) {
                        continue;
                    }

                    String fecha = JOptionPane.showInputDialog("Ingrese Fecha de Lanzamiento (Formato: AAAA-MM-DD):");
                    if (fecha == null) {
                        continue;
                    }

                    String inputCat = JOptionPane.showInputDialog("Ingrese el ID de la Categoría (1 a 5):\n(1: Acción, 2: Aventura, 3: RPG, 4: Deportes, 5: Estrategia)");
                    if (inputCat == null) {
                        continue;
                    }
                    int categoria = Integer.parseInt(inputCat);

                    if (nombre.trim().isEmpty() || fecha.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El nombre y la fecha de lanzamiento no pueden ir vacíos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MetodosBD.ingresarVideojuego(codigo, nombre, descripcion, desarrollador, fecha, categoria);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: El código del juego y el ID de categoría deben ser estrictamente numéricos.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                enSubMenu = false;
            }
        }
    }

    private static void menuAlquileres() {
        String[] opciones = {"Alquilar Videojuego", "Volver al Menú Principal"};
        boolean enSubMenu = true;

        while (enSubMenu) {
            int seleccion = JOptionPane.showOptionDialog(null,
                    "MÓDULO GESTIÓN DE ALQUILERES\nSeleccione una operación:", "Gestión de Alquileres",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == 0) {
                try {
                    String cedula = JOptionPane.showInputDialog("Ingrese la Cédula del Cliente:");
                    if (cedula == null) {
                        continue;
                    }

                    String inputJuego = JOptionPane.showInputDialog("Ingrese el Código del Videojuego a rentar:");
                    if (inputJuego == null) {
                        continue;
                    }
                    int juego = Integer.parseInt(inputJuego);

                    String inputSucursal = JOptionPane.showInputDialog("Ingrese el Número de Sucursal donde realiza el trámite (1-5):");
                    if (inputSucursal == null) {
                        continue;
                    }
                    int sucursal = Integer.parseInt(inputSucursal);

                    String inputDias = JOptionPane.showInputDialog("Ingrese la Cantidad de Días estipulados para préstamo:");
                    if (inputDias == null) {
                        continue;
                    }
                    int dias = Integer.parseInt(inputDias);

                    if (cedula.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: La cédula del cliente es requerida para el alquiler.", "Validación", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MetodosBD.ingresarAlquiler(cedula, juego, sucursal, dias);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: Asegúrese de ingresar solo números enteros en Código, Sucursal y Días.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                enSubMenu = false;
            }
        }
    }

}

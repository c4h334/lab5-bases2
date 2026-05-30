/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acceso_datos;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MetodosBD {

    public static void ingresarCliente(String cedula, String nombre, String apellido, String telefono, String correo, String direccion) {
        Connection conn = ConexionBD.getConexion();
        String sql = "{call sp_ingresar_cliente(?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, cedula);
            cstmt.setString(2, nombre);
            cstmt.setString(3, apellido);
            cstmt.setString(4, telefono);
            cstmt.setString(5, correo);
            cstmt.setString(6, direccion);

            try (ResultSet rs = cstmt.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, rs.getString("Mensaje"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de BD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void listarClientes() {
        Connection conn = ConexionBD.getConexion();
        String sql = "{call sp_listar_clientes()}";
        StringBuilder sb = new StringBuilder("=== LISTA DE CLIENTES ===\n\n");

        try (CallableStatement cstmt = conn.prepareCall(sql); ResultSet rs = cstmt.executeQuery()) {

            boolean tieneDatos = false;
            while (rs.next()) {
                tieneDatos = true;
                sb.append("Cédula: ").append(rs.getString("Cedula")).append("\n")
                        .append("Nombre: ").append(rs.getString("Nombre")).append(" ").append(rs.getString("Apellido")).append("\n")
                        .append("Teléfono: ").append(rs.getString("Telefono")).append("\n")
                        .append("Correo: ").append(rs.getString("Correo")).append("\n-------------------------\n");
            }

            if (!tieneDatos) {
                JOptionPane.showMessageDialog(null, "No hay clientes registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, sb.toString(), "Clientes Registrados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de BD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void listarVideojuegos() {
        Connection conn = ConexionBD.getConexion();
        String sql = "{call sp_listar_videojuegos()}";
        StringBuilder sb = new StringBuilder("=== LISTA DE VIDEOJUEGOS ===\n\n");

        try (CallableStatement cstmt = conn.prepareCall(sql); ResultSet rs = cstmt.executeQuery()) {

            boolean tieneDatos = false;
            while (rs.next()) {
                tieneDatos = true;
                sb.append("Código: ").append(rs.getInt("Codigo")).append("\n")
                        .append("Nombre: ").append(rs.getString("Nombre")).append("\n")
                        .append("Desarrollador: ").append(rs.getString("Desarrollador")).append("\n-------------------------\n");
            }

            if (!tieneDatos) {
                JOptionPane.showMessageDialog(null, "No hay videojuegos registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, sb.toString(), "Videojuegos", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de BD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ingresarVideojuego(int codigo, String nombre, String descripcion, String desarrollador, String fechaLanzamiento, int idCategoria) {
        Connection conn = ConexionBD.getConexion();
        String sql = "{call sp_ingresar_videojuego(?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, codigo);
            cstmt.setString(2, nombre);
            cstmt.setString(3, descripcion);
            cstmt.setString(4, desarrollador);
            cstmt.setString(5, fechaLanzamiento);
            cstmt.setInt(6, idCategoria);

            try (ResultSet rs = cstmt.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, rs.getString("Mensaje"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de BD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ingresarAlquiler(String cedula, int codigoVideojuego, int numeroSucursal, int cantidadDias) {
        Connection conn = ConexionBD.getConexion();
        String sql = "{call sp_ingresar_alquiler(?, ?, ?, ?)}";

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, cedula);
            cstmt.setInt(2, codigoVideojuego);
            cstmt.setInt(3, numeroSucursal);
            cstmt.setInt(4, cantidadDias);

            try (ResultSet rs = cstmt.executeQuery()) {
                if (rs.next()) {
                   
                    if (rs.getMetaData().getColumnName(1).equalsIgnoreCase("SecuenciaAsignada")) {
                        JOptionPane.showMessageDialog(null, "Alquiler creado con éxito. Secuencia asignada: " + rs.getInt("SecuenciaAsignada"));
                    } else {
                        
                        JOptionPane.showMessageDialog(null, rs.getString("Mensaje"), "Aviso del Sistema", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de BD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

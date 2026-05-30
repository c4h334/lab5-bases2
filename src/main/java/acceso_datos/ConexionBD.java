/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acceso_datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class ConexionBD {

    private static Connection conexion = null;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Properties props = new Properties();
                
                java.io.InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties");
                
                if (input == null) {
                    System.out.println("Error: No se pudo encontrar el archivo db.properties");
                    return null;
                }
                
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");

                conexion = DriverManager.getConnection(url, user, pass);
                System.out.println("Conexión establecida con éxito con VIDEO_CLUB.");
            } catch (IOException e) {
                System.out.println("Error: No se pudo leer el archivo db.properties: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Error de SQL al intentar conectar: " + e.getMessage());
            }
        }
        return conexion;
    }
}

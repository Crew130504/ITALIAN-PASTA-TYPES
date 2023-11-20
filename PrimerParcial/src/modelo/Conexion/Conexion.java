package modelo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection cn = null;
    private static String URLDB = "jdbc:mysql://localhost/primer_parcial";
    private static String usuario = "root";
    private static String contrasena = "";
    
    public static Connection getConexion () {
        try {
            cn = DriverManager.getConnection(URLDB, usuario, contrasena);
        } catch (SQLException sqle) {
            System.out.println("No existe bobo");
        }
        return cn;
    }
    public static void desconectar () {
        cn = null;
    }
}
package controlDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion.Conexion;
import modelo.PastaVO;
import vista.Vista;

public class PastaDAO {
    private Vista vista = new Vista();
    private Connection con;
    private Statement st;
    private ResultSet rs;

public PastaDAO() {
        // Inicializa las variables de conexión y resultados a null
        this.con = null;
        this.st = null;
        this.rs = null;
    }
public boolean verificarBaseDeDatosVacia() {
        try {
            // Obtiene una conexión a la base de datos
            con = Conexion.getConexion();
            // Consulta para contar las filas en la tabla "pastas"
            String consulta = "SELECT COUNT(*) FROM pastas";
            // Prepara la consulta
            st = con.prepareStatement(consulta);
            // Ejecuta la consulta y obtiene el resultado
            rs = st.executeQuery(consulta);

            if (rs.next()) {
                int count = rs.getInt(1);
                // Retorna true si la base de datos está vacía (count = 0)
                return count == 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                // Cierra las conexiones y recursos
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Si hay un error o no se puede determinar el estado de la base de datos, retorna false
        return false;
    }
public ArrayList<PastaVO> listaDePasta() {
        ArrayList<PastaVO> listaPasta = new ArrayList<>();
        String consulta = "SELECT * FROM pastas";
        try {
            // Obtiene una conexión a la base de datos
            con = Conexion.getConexion();
            // Crea una declaración SQL
            st = (Statement) con.createStatement();
            // Ejecuta la consulta
            rs = st.executeQuery(consulta);

            while (rs.next()) {
                // Crea objetos PastaVO y los agrega a la lista
                listaPasta.add(new PastaVO(rs.getString("nombre"), rs.getString("forma"), rs.getString("maridaje"), rs.getString("platoPrincipal"), rs.getString("variedad"), rs.getString("ciudad"), rs.getString("imagen"), rs.getString("receta")));
            }
            // Cierra la declaración y desconecta de la base de datos
            st.close();
            Conexion.desconectar();
        } catch (Exception e) {
            // Manejo de excepciones en caso de error
            vista.nosepudo();
        }
        return listaPasta;
    }
public void insertarDatos(PastaVO pasta) {
        try {
            // Obtiene una conexión a la base de datos
            con = Conexion.getConexion();
            // Crea una declaración SQL
            st = (Statement) con.createStatement();
            // Consulta de inserción
            String insercion = "INSERT INTO pastas VALUES ('" + pasta.getNombre() + "','" + pasta.getMaridaje() + "','" + pasta.getForma() + "','" + pasta.getPlatoPrincipal() + "','" + pasta.getVariedad() + "','" + pasta.getCiudad() + "','" + pasta.getImagen() + "','" + pasta.getReceta() + "')";
            // Ejecuta la consulta de inserción
            st.executeUpdate(insercion);
            // Cierra la declaración y desconecta de la base de datos
            st.close();
            Conexion.desconectar();

        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error
            vista.noInsercion();
            
        }
    }
public void insertarDatos(List<PastaVO> pastas) {
    Connection con = null;
    Statement st = null;

    try {
        // Obtiene una conexión a la base de datos
        con = Conexion.getConexion();
        // Crea una declaración SQL
        st = con.createStatement();
        // Consulta de inserción para una lista de objetos PastaVO
        StringBuilder insercion = new StringBuilder("INSERT INTO pastas VALUES ");

        for (PastaVO pasta : pastas) {
            insercion.append("('")
                    .append(pasta.getNombre()).append("', '")
                    .append(pasta.getForma()).append("', '")
                    .append(pasta.getMaridaje()).append("', '")
                    .append(pasta.getPlatoPrincipal()).append("', '")
                    .append(pasta.getVariedad()).append("', '")
                    .append(pasta.getCiudad()).append("', '")
                    .append(pasta.getImagen()).append("', '")
                    .append(pasta.getReceta()).append("'), ");
        }

        // Verifica si hay elementos para insertar antes de ejecutar la consulta
        if (pastas.isEmpty()) {
            return; // No hay elementos para insertar, salimos del método.
        }

        insercion.setLength(insercion.length() - 2); // Elimina la última coma y espacio

        st.executeUpdate(insercion.toString());
    } catch (SQLException ex) {
        // Manejo de excepciones en caso de error
        ex.printStackTrace();
        // También puedes manejar la excepción de otra manera, como lanzarla nuevamente o registrarla.
        vista.noInsercion();
    } finally {
        try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
public void actualizarDatos(PastaVO pasta) {
        // Consulta de actualización para un objeto PastaVO
        String modificacion = "UPDATE `pastas` SET `nombre`='" + pasta.getNombre() + "', `forma`='" + pasta.getForma() + "', `maridaje`='" + pasta.getMaridaje() + "', `platoPrincipal`='" + pasta.getPlatoPrincipal() + "', `variedad`='" + pasta.getVariedad() + "', `ciudad`='" + pasta.getCiudad() + "' WHERE nombre='" + pasta.getNombre() + "'";
        try {
            // Obtiene una conexión a la base de datos
            con = Conexion.getConexion();
            // Crea una declaración SQL
            st = (Statement) con.createStatement();
            // Ejecuta la consulta de actualización
            st.executeUpdate(modificacion);
            // Cierra la declaración y desconecta de la base de datos
            st.close();
            Conexion.desconectar();

        } catch (SQLException ex) {
            // Manejo de excepciones en caso de error
            vista.nomodificacion();
        }
    }
public int ConsultarVariedad() {
    int cantidad = 0;
    try {
        String consulta = "SELECT COUNT(*) AS cantidad FROM pastas WHERE variedad = 'fresca'";
        con = Conexion.getConexion();
        st = con.createStatement();
        rs = st.executeQuery(consulta);

        if (rs.next()) { // Mover el cursor al primer registro (si existe)
            cantidad = rs.getInt("cantidad");
        } else {
            vista.noresultados();
        }

        Conexion.desconectar();

    } catch (SQLException ex) {
        vista.noconsulta();
        ex.printStackTrace();
    }
    return cantidad;
}
public int ConsultarCantidad() {
    int cantidad = 0;
    try {
        String consulta = "SELECT COUNT(*) AS cantidad FROM pastas";
        con = Conexion.getConexion();
        st = con.createStatement();
        rs = st.executeQuery(consulta);

        if (rs.next()) { // Mover el cursor al primer registro (si existe)
            cantidad = rs.getInt("cantidad");
        } else {
            vista.noresultados();
        }

        Conexion.desconectar();

    } catch (SQLException ex) {
        vista.noconsulta();
        ex.printStackTrace();
    }
    return cantidad;
}
public List<String> consultarFormas() {
    List<String> formas = new ArrayList<>();
    String consulta = "SELECT `forma` FROM `pastas`";
    try {
        con = Conexion.getConexion();
        st = (Statement) con.createStatement();
        rs = st.executeQuery(consulta);

        while (rs.next()) {
            String forma = rs.getString("forma");
            formas.add(forma);
        }
        
        st.close();
        Conexion.desconectar();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return formas;
}
public int ConsultarCantidadCiudad(String ciudad) {
    int cantidad = 0;
    try {
        String consulta = "SELECT COUNT(*) AS cantidad FROM pastas  WHERE Ciudad = '"+ciudad+"'";
        con = Conexion.getConexion();
        st = con.createStatement();
        rs = st.executeQuery(consulta);

        if (rs.next()) { // Mover el cursor al primer registro (si existe)
            cantidad = rs.getInt("cantidad");
        } else {
            vista.noresultados();
        }

        Conexion.desconectar();

    } catch (SQLException ex) {
        vista.noconsulta();
        ex.printStackTrace();
    }
    return cantidad;
}
public JTable cargarDatosDesdeMySQL() {
        JTable table = null;

        try {
            // Consulta SQL para obtener los datos
            String consulta = "SELECT * FROM pastas";
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(consulta);

            // Crear un modelo de tabla
            DefaultTableModel modelo = new DefaultTableModel();

            // Obtener los nombres de las columnas de metadatos
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                modelo.addColumn(rs.getMetaData().getColumnName(i));
            }

            // Rellenar el modelo de tabla con los datos
            while (rs.next()) {
                Object[] fila = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    fila[i - 1] = rs.getObject(i);
                }
                modelo.addRow(fila);
            }

            // Crear el JTable y asignarle el modelo
            table = new JTable(modelo);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return table;
    }
public boolean eliminarPasta(String nombre) {
        String consulta = "DELETE FROM pastas where nombre='" + nombre + "'";
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            st.executeUpdate(consulta);
            st.close();
            Conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            vista.nobtneliminar();
        }
        return false;
    }
public PastaVO consultarPasta(String nombre) {
        PastaVO pastaConsultada = null;
        String consulta = "SELECT * FROM pastas WHERE nombre='"+nombre+"'";
        try {
            con = Conexion.getConexion();
            st = (Statement) con.createStatement();
            rs = st.executeQuery(consulta);
            
            if (rs.next()) {
                String forma = rs.getString("forma");
                String maridaje = rs.getString("maridaje");
                String plato = rs.getString("platoPrincipal");
                String variedad = rs.getString("variedad");
                String ciudad = rs.getString("ciudad");
                String imagen = rs.getString("imagen");
                String receta = rs.getString("receta");
                pastaConsultada = new PastaVO(nombre, forma, maridaje, plato, variedad, ciudad,imagen,receta);
            }
            st.close();
            Conexion.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pastaConsultada;
    } 
}

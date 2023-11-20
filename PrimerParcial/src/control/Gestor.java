package control;

import controlDAO.PastaDAO;
import controlDAO.PropertiesDAO;
import java.util.ArrayList;
import modelo.PastaVO;
import vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import modelo.RAF;
import vista.VistaConsultar;

public class Gestor implements ActionListener {

    private PastaDAO miPastaDAO = new PastaDAO();
    private Vista vista;
    private PropertiesDAO propiedades;
    private VistaConsultar vistaConsulta;

    public Gestor() {
        this.vistaConsulta = new VistaConsultar();
        this.vista = new Vista();
        
        this.vistaConsulta.btnConsultarVariedad.addActionListener(this);
        this.vistaConsulta.btnConsultarTodo.addActionListener(this);
        this.vistaConsulta.btnConsultarCiudadaOrigen.addActionListener(this);
        this.vistaConsulta.btnConsultarForma.addActionListener(this);
        this.vistaConsulta.btnConsultarx.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnSeleccionar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtImagen.setEditable(false);
        this.vista.txtReceta.setEditable(false);
        
        iniciar();

        llenarCombo();
    }

    public void iniciar() {
        this.vista.setTitle("Pasta");

        // Lineas para cargar el properties por primera vez
        if (miPastaDAO.verificarBaseDeDatosVacia()) {
            Properties objpropiedades = new Properties();
            this.propiedades = new PropertiesDAO();
            List<PastaVO> listaPasta = propiedades.Properties(objpropiedades);
            // Insertar los datos de PastaVO en la base de datos
            miPastaDAO.insertarDatos(listaPasta);
        }

        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }

    // Llena un ArrayList y de los objetos de este se obtiene el nombre para pasarlo al combobox
    private void llenarCombo() {
        vista.comboxPasta.removeAllItems(); // Limpia los elementos existentes en el ComboBox

        miPastaDAO = new PastaDAO();
        ArrayList<PastaVO> listaPastas = miPastaDAO.listaDePasta();

        // Agregar la opción nula o predeterminada al principio del ComboBox
        vista.comboxPasta.addItem("Seleccionar");

        if (listaPastas.size() > 0) {
            for (int i = 0; i < listaPastas.size(); i++) {
                vista.comboxPasta.addItem(listaPastas.get(i).getNombre());
            }
        } else {
            vista.nohayregistros();
        }
    }

    private boolean nombreExistenteEnComboBox(String nombre) {
        int itemCount = vista.comboxPasta.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (nombre.equals(vista.comboxPasta.getItemAt(i))) {
                return true; // El nombre ya existe en el ComboBox
            }
        }
        return false; // El nombre no existe en el ComboBox
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnSalir){
            RAF archivo = new RAF();
            archivo.limpiarArchivo();
            archivo.escribir(miPastaDAO.listaDePasta());
            archivo.cerrar();
            System.exit(0);
        }
        if (e.getSource() == vista.btnSeleccionar) {
            ArrayList<PastaVO> listaPastas = miPastaDAO.listaDePasta();
            int pastaSeleccionada = vista.comboxPasta.getSelectedIndex();
            if (pastaSeleccionada == 0) {
                vista.limpiar();
            } else {
                PastaVO pastaSeleccionadaVO = listaPastas.get(pastaSeleccionada - 1);
                vista.txtNombre.setText(pastaSeleccionadaVO.getNombre());
                vista.txtForma.setText(pastaSeleccionadaVO.getForma());
                vista.txtMaridaje.setText(pastaSeleccionadaVO.getMaridaje());
                vista.txtPlato.setText(pastaSeleccionadaVO.getPlatoPrincipal());
                vista.txtCiudad.setText(pastaSeleccionadaVO.getCiudad());
                vista.txtImagen.setText(pastaSeleccionadaVO.getImagen());
                vista.txtReceta.setText(pastaSeleccionadaVO.getReceta());

                // Seleccionar el botón según el valor de variedad
                String variedad = pastaSeleccionadaVO.getVariedad();
                if ("fresca".equals(variedad)) {
                    vista.btnFresca.setSelected(true);
                    vista.btnSeca.setSelected(false);
                } else if ("seca".equals(variedad)) {
                    vista.btnFresca.setSelected(false);
                    vista.btnSeca.setSelected(true);
                }
            }
        }

        if (e.getSource() == vista.btnAgregar) {
            PastaVO agregarPasta = new PastaVO();

            // Obtener valores de las casillas de texto
            String nombre = vista.txtNombre.getText();
            String forma = vista.txtForma.getText();
            String maridaje = vista.txtMaridaje.getText();
            String platoPrincipal = vista.txtPlato.getText();
            String ciudad = vista.txtCiudad.getText();

            // Verificar si alguna casilla está vacía
            if (nombre.isEmpty() || forma.isEmpty() || maridaje.isEmpty() || platoPrincipal.isEmpty() || ciudad.isEmpty()) {
                // Mostrar un mensaje de error si alguna casilla está vacía
                vista.faltanCampos();
            } else if (!vista.btnFresca.isSelected() && !vista.btnSeca.isSelected()) {
                // Mostrar un mensaje de error si ninguno de los dos botones está seleccionado
                vista.seleccionaVariedad();
            } else {
                String rutaImagen = null;
                String rutaReceta = null;

                // Abre un cuadro de diálogo para seleccionar una imagen
                JFileChooser imageChooser = new JFileChooser();
                imageChooser.setDialogTitle("Selecciona una Imagen");
                int imageResult = imageChooser.showOpenDialog(vista); // Cambia "vista" por el componente padre adecuado

                if (imageResult == JFileChooser.APPROVE_OPTION) {
                    // Obtén la ruta de la imagen seleccionada
                    File imageFile = imageChooser.getSelectedFile();
                    rutaImagen = imageFile.getAbsolutePath();
                    rutaImagen = rutaImagen.replace("\\", "\\\\");

                    // Ahora, abre otro cuadro de diálogo para seleccionar una receta
                    JFileChooser recipeChooser = new JFileChooser();
                    recipeChooser.setDialogTitle("Selecciona una Receta");
                    int recipeResult = recipeChooser.showOpenDialog(vista); // Cambia "vista" por el componente padre adecuado

                    if (recipeResult == JFileChooser.APPROVE_OPTION) {
                        // Obtén la ruta de la receta seleccionada
                        File recipeFile = recipeChooser.getSelectedFile();
                        rutaReceta = recipeFile.getAbsolutePath();
                        rutaReceta = rutaReceta.replace("\\", "\\\\");

                        // Verificar si el nombre ya está en el ComboBox
                        if (!nombreExistenteEnComboBox(nombre)) {
                            // El nombre no existe en el ComboBox, proceder con la inserción
                            agregarPasta.setNombre(nombre);
                            agregarPasta.setForma(forma);
                            agregarPasta.setMaridaje(maridaje);
                            agregarPasta.setPlatoPrincipal(platoPrincipal);
                            agregarPasta.setCiudad(ciudad);

                            if (vista.btnFresca.isSelected()) {
                                agregarPasta.setVariedad("fresca");
                            } else if (vista.btnSeca.isSelected()) {
                                agregarPasta.setVariedad("seca");
                            }

                            // Asigna las rutas de la imagen y la receta al objeto PastaVO
                            agregarPasta.setImagen(rutaImagen);
                            agregarPasta.setReceta(rutaReceta); // Agrega un método setReceta si aún no lo tienes

                            // Inserta los datos en la base de datos
                            miPastaDAO.insertarDatos(agregarPasta);
                            llenarCombo();
                            vista.limpiar();
                        } else {
                            // Mostrar un mensaje de error si el nombre ya existe en el ComboBox
                            vista.yaexiste();
                        }
                    } else {
                        // El usuario canceló la selección de la receta, muestra un mensaje de error
                        vista.noSeleccionoReceta();
                    }
                } else {
                    // El usuario canceló la selección de la imagen, muestra un mensaje de error
                    vista.noSeleccionoImagen();
                }
            }
        }

        if (e.getSource() == vista.btnModificar) {
            // Obtener el índice de la pasta seleccionada en el ComboBox
            int pastaSeleccionadaIndex = vista.comboxPasta.getSelectedIndex();

            // Verificar si se ha seleccionado una pasta
            if (pastaSeleccionadaIndex != -1) {
                miPastaDAO = new PastaDAO();
                PastaVO pastaModificada = new PastaVO();

                // Obtener valores de las casillas de texto
                String nombreModificado = vista.txtNombre.getText();
                String formaModificada = vista.txtForma.getText();
                String maridajeModificado = vista.txtMaridaje.getText();
                String platoPrincipalModificado = vista.txtPlato.getText();
                String ciudadModificada = vista.txtCiudad.getText();

                // Verificar si alguna casilla está vacía
                if (nombreModificado.isEmpty() || formaModificada.isEmpty() || maridajeModificado.isEmpty() || platoPrincipalModificado.isEmpty() || ciudadModificada.isEmpty()) {
                    // Mostrar un mensaje de error si alguna casilla está vacía
                    vista.faltanCampos();
                } else if (!vista.btnFresca.isSelected() && !vista.btnSeca.isSelected()) {
                    // Mostrar un mensaje de error si ninguno de los dos botones está seleccionado
                    vista.seleccionaVariedad();
                } else {
                    // Obtener la pasta seleccionada
                    ArrayList<PastaVO> listaPastas = miPastaDAO.listaDePasta();
                    PastaVO pastaSeleccionada = listaPastas.get(pastaSeleccionadaIndex - 1);

                    // Comparar si el nombre ha cambiado
                    if (nombreModificado.equals(pastaSeleccionada.getNombre())) {
                        // El nombre no ha cambiado, se puede proceder con la modificación
                        pastaSeleccionada.setForma(formaModificada);
                        pastaSeleccionada.setMaridaje(maridajeModificado);
                        pastaSeleccionada.setPlatoPrincipal(platoPrincipalModificado);
                        pastaSeleccionada.setCiudad(ciudadModificada);

                        if (vista.btnFresca.isSelected()) {
                            pastaSeleccionada.setVariedad("fresca");
                        } else if (vista.btnSeca.isSelected()) {
                            pastaSeleccionada.setVariedad("seca");
                        }

                        // Llamar al método de actualización en el patrón DAO para guardar los cambios
                        miPastaDAO.actualizarDatos(pastaSeleccionada);

                        // Actualizar el ComboBox con los datos actualizados
                        llenarCombo();

                        // Limpiar los campos de texto
                        vista.limpiar();
                    } else {
                        // Mostrar un mensaje de error si se intenta cambiar el nombre
                        vista.nombreNoModificado();
                    }
                }
            } else {
                // Mostrar un mensaje de error si no se ha seleccionado una pasta
                vista.seleccionaUnaPasta();
            }
        }
        if (e.getSource()== vista.btnConsultar){
            this.vistaConsulta.setVisible(true);}
         if(e.getSource()==vistaConsulta.btnConsultarVariedad){
            int cantidadFresca = miPastaDAO.ConsultarVariedad();
            int cantidadTotal = miPastaDAO.ConsultarCantidad();
            vistaConsulta.variedadPasstas(cantidadFresca, cantidadTotal-cantidadFresca);
         }  
        if(e.getSource()==vistaConsulta.btnConsultarForma){
            List<String> forma = miPastaDAO.consultarFormas();
            vistaConsulta.ListaFormas(forma);
        }
        if(e.getSource()==vistaConsulta.btnConsultarCiudadaOrigen){
            String ciudad = vistaConsulta.txtCiudadOrigen.getText();
            int cantidadEnCiudad = miPastaDAO.ConsultarCantidadCiudad(ciudad);
            vistaConsulta.CantidadEnCiudad(cantidadEnCiudad);
        }
        if(e.getSource()==vistaConsulta.btnConsultarTodo){
        JFrame frame = new JFrame("Pastas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 200);
        JTable table = miPastaDAO.cargarDatosDesdeMySQL();
        frame.setVisible(true);
        frame.add(table);
        }
        if(e.getSource()==vista.btnEliminar){
            //obtiene el nombre de la pasta 
            String nombre=vista.txtNombre.getText();
            //verifica que si se selecciono pasata 
            if (!nombre.isEmpty()){
                //llama al metodo eliminar Pasta Dao 
                if (miPastaDAO.eliminarPasta(nombre)){
                    vista.Eliminado();
                    vista.limpiar();
                }
               //Borra el nombre de la pasta del comboxPasta
                int selectPasta = vista.comboxPasta.getSelectedIndex();
                if (selectPasta != -1) {
                    DefaultComboBoxModel<String> nom = (DefaultComboBoxModel<String>) vista.comboxPasta.getModel();
                    nom.removeElementAt(selectPasta);
                } 
            }else{
                vista.NoEliminado();
                
                }
            }
         if (e.getSource()== vistaConsulta.btnConsultarx){
        String nombreConsulta = vistaConsulta.txtNombreConsulta.getText();
        PastaVO pastaConsultada = miPastaDAO.consultarPasta(nombreConsulta);
        if (nombreConsulta.isEmpty()){
            vistaConsulta.error();
        }
        else if (pastaConsultada == null){
            vistaConsulta.noExiste();
        }
       
        if (pastaConsultada != null){
            vistaConsulta.txtForma.setText(pastaConsultada.getForma());
            vistaConsulta.txtMaridaje.setText(pastaConsultada.getMaridaje());
            vistaConsulta.txtPlatoPrincipal.setText(pastaConsultada.getPlatoPrincipal());
            vistaConsulta.txtCiudad.setText(pastaConsultada.getCiudad());
            vistaConsulta.txtVariedad.setText(pastaConsultada.getVariedad());
            vistaConsulta.txtUrlImagen.setText(pastaConsultada.getImagen());
            vistaConsulta.txtUrlReceta.setText(pastaConsultada.getReceta());
        }
        
         }
    }
}

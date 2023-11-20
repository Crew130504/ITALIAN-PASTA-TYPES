package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class RAF {
    private String nombre;
    private String forma;
    private String maridaje;
    private String platoPrincipal;
    private String variedad;
    private String ciudad;
    private String imagen;
    private String receta;
    private File directorio;
    private File fl;
    private RandomAccessFile archivo;

	// Constructor de la clase RAF
    public RAF(){
        // Configura un JFileChooser para que el usuario seleccione una carpeta donde guardar el archivo RAF

        // Crea una instancia de JFileChooser que se abre en el directorio de inicio del sistema del usuario
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        // Establece el título del diálogo para que el usuario sepa qué se espera
        fileChooser.setDialogTitle("Selecciona una carpeta para guardar el archivo RAF");

        // Configura el JFileChooser para que solo permita seleccionar directorios (carpetas)
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Muestra el diálogo de selección de archivo y espera a que el usuario interactúe con él
        int returnValue = fileChooser.showSaveDialog(null);

        // Verifica si el usuario hizo clic en "Guardar" en el diálogo de selección de carpeta
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // El usuario ha seleccionado un directorio y ha hecho clic en "Guardar"

            // Obtiene el directorio seleccionado por el usuario
            directorio = fileChooser.getSelectedFile();

            // Crea un archivo RAF dentro del directorio seleccionado con el nombre "RAF.txt"
            fl = new File(directorio, "RAF.txt");

            try {         
                    // Si el archivo no existe, créalo
                    archivo = new RandomAccessFile(fl, "rw");
            } catch (FileNotFoundException fnfe) {
                // Maneja la excepción si el archivo no se encuentra
                fnfe.printStackTrace();
            }
        }
    }

        // Método para escribir datos en el archivo RAF
    public void escribir(ArrayList<PastaVO> mispastas) {
        try {
            for (PastaVO pasta : mispastas) {
                // Obtener y formatear los atributos de la instancia PastaVO
                nombre = pasta.getNombre();
                if (nombre.length() < 25) {
                    for (int i = nombre.length(); i < 25; i++)
                    nombre = nombre + " ";
                } else {nombre = nombre.substring(0, 25);}
                forma = pasta.getForma();
                if (forma.length() < 25) {
                    for (int i = forma.length(); i < 25; i++)
                    forma = forma + " ";
                } else {forma = forma.substring(0, 25);}
                maridaje=pasta.getMaridaje();
                if (maridaje.length() < 125) {
                    for (int i = maridaje.length(); i < 125; i++)
                    maridaje = maridaje + " ";
                } else {maridaje = maridaje.substring(0, 125);}
                platoPrincipal=pasta.getPlatoPrincipal();
                if (platoPrincipal.length() < 125) {
                    for (int i = platoPrincipal.length(); i < 125; i++)
                    platoPrincipal = platoPrincipal + " ";
                } else {platoPrincipal = platoPrincipal.substring(0, 125);}
                variedad=pasta.getVariedad();
                if (variedad.length() < 10) {
                    for (int i = variedad.length(); i < 10; i++)
                    variedad = variedad + " ";
                } else {variedad = variedad.substring(0, 10);}
                ciudad=pasta.getCiudad();
                if (ciudad.length() < 10) {
                    for (int i = ciudad.length(); i < 10; i++)
                    ciudad = ciudad + " ";
                } else {ciudad = ciudad.substring(0, 10);}
                imagen=pasta.getImagen();
                if (imagen.length() < 125) {
                    for (int i = imagen.length(); i < 125; i++)
                    imagen = imagen + " ";
                } else {imagen = imagen.substring(0, 125);}
                receta =pasta.getReceta();
                if (receta.length() < 125) {
                    for (int i = receta.length(); i < 125; i++)
                    receta = receta + " ";
                } else {receta = receta.substring(0, 125);}
                // Si el archivo no está vacío, coloca el puntero al final
                if (archivo.length()!= 0) {
                    archivo.seek(archivo.length());
                    archivo.writeChar('\n');
		}
                // Escribe los atributos de cada objeto PastaVO en el archivo
                archivo.writeChars(nombre);
                archivo.writeChars(forma);
                archivo.writeChars(maridaje);
                archivo.writeChars(platoPrincipal);
                archivo.writeChars(variedad);
                archivo.writeChars(ciudad);
                archivo.writeChars(imagen);
                archivo.writeChars(receta);
            }
        }catch (FileNotFoundException fnfe) {/* Archivo no encontrado */
	} catch (IOException ioe) {// Manejar la excepción si ocurre un error al escribir en el archivo
        }
    }
    public void limpiarArchivo() {
        try {
            // Verifica si el archivo existe
            if (archivo != null) {
                // Cierra el archivo antes de eliminarlo
                archivo.close();
                // Elimina el archivo
                if (fl.exists()) {
                    fl.delete();
                }
                // Crea un nuevo archivo RAF dentro del directorio seleccionado con el nombre "RAF.txt"
                archivo = new RandomAccessFile(fl, "rw");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        //Método para cerrar el archivo
        public void cerrar() {
	try {
		archivo.close();
	} catch (IOException e) {e.printStackTrace();}
}
}

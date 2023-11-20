// Importaciones necesarias
package controlDAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import modelo.PastaVO;

// Definición de la clase PropertiesDAO
public class PropertiesDAO {
 
    // Método que recibe un objeto Properties y devuelve una lista de objetos PastaVO
    public List<PastaVO> Properties(Properties propiedades) {
    
        // Declaración de variables
        InputStream entrada = null;
        List<PastaVO> listaPasta = new ArrayList<>();

        try {
            // Se abre un archivo de propiedades
            entrada = new FileInputStream("src/data/Pasta.properties");
        
            // Se carga el archivo de propiedades en el objeto propiedades
            propiedades.load(entrada);
        
            // Se obtiene la cantidad de elementos a procesar
            int cantidad = Integer.parseInt(propiedades.getProperty("Cantidad"));
       
            // Bucle para procesar cada elemento
            for (int i = 1; i <= cantidad; i++) {
                String pastaInfo = propiedades.getProperty("Pasta" + i);

                if (pastaInfo != null) {
                    String[] caracteristicasArray = pastaInfo.split(", ");

                    // Verifica si hay suficientes elementos para crear un objeto PastaVO
                    if (caracteristicasArray.length >= 8) {
                        String nombrePasta = caracteristicasArray[0];
            
                        // Crea un objeto PastaVO y lo agrega a la lista
                        PastaVO agregarPasta = new PastaVO();
                        agregarPasta.setNombre(nombrePasta);
                        agregarPasta.setForma(caracteristicasArray[1]);
                        agregarPasta.setMaridaje(caracteristicasArray[2]);
                        agregarPasta.setPlatoPrincipal(caracteristicasArray[3]);
                        agregarPasta.setVariedad(caracteristicasArray[4]);
                        agregarPasta.setCiudad(caracteristicasArray[5]);
                        agregarPasta.setImagen(caracteristicasArray[6]);
                        agregarPasta.setReceta(caracteristicasArray[7]);
                        listaPasta.add(agregarPasta);
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Devuelve la lista de objetos PastaVO
            return listaPasta;
        }
    }      
}

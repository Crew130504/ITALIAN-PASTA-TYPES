package modelo;

public class PastaVO {
    //Atributos de la pasta
    private String nombre;
    private String forma;
    private String maridaje;
    private String platoPrincipal;
    private String variedad;
    private String ciudad;
    private String imagen;
    private String receta;
    
    //Constructor

    public PastaVO(String nombre, String forma, String maridaje, String platoPrincipal, String variedad, String ciudad, String imagen, String receta) {
        this.nombre = nombre;
        this.forma = forma;
        this.maridaje = maridaje;
        this.platoPrincipal = platoPrincipal;
        this.variedad = variedad;
        this.ciudad = ciudad;
        this.imagen = imagen;
        this.receta = receta;
    }
    public PastaVO() {
    }
    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getMaridaje() {
        return maridaje;
    }

    public void setMaridaje(String maridaje) {
        this.maridaje = maridaje;
    }

    public String getPlatoPrincipal() {
        return platoPrincipal;
    }

    public void setPlatoPrincipal(String platoPrincipal) {
        this.platoPrincipal = platoPrincipal;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }   
}

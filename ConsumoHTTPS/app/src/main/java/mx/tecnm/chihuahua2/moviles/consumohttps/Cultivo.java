package mx.tecnm.chihuahua2.moviles.consumohttps;

public class Cultivo {

    private int id_graminea;
    private String nombre_comun;
    private String nombre_cientifico;
    private String descripcion;
    private String imagen;

    public Cultivo(){

    }

    public Cultivo(int id_graminea, String nombre_comun, String nombre_cientifico, String descripcion, String imagen) {
        this.id_graminea = id_graminea;
        this.nombre_comun = nombre_comun;
        this.nombre_cientifico = nombre_cientifico;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getId_graminea() {
        return id_graminea;
    }
    public void setId_graminea(int id_graminea) {
        this.id_graminea = id_graminea;
    }


    public String getNombre_comun() {
        return nombre_comun;
    }

    public void setNombre_comun(String nombre_comun) {
        this.nombre_comun = nombre_comun;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

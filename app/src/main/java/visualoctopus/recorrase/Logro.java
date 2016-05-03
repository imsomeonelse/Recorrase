package visualoctopus.recorrase;

import java.io.Serializable;

/**
 * Created by chuch_000 on 21/03/2016.
 */
public class Logro implements Serializable{

    private String descripcion;
    private String imagen;

    public Logro(String descripcion, String imagen) {
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }
}

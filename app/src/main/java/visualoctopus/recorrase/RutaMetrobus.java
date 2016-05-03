package visualoctopus.recorrase;

import java.io.Serializable;

/**
 * Created by Sue on 06/03/2016.
 */
public class RutaMetrobus extends Ruta implements Serializable{
    public String estacion;
    public String linea;

    public RutaMetrobus(String nombre, String estacion, String linea, double calificacion) {
        super(nombre, calificacion);
        this.estacion = estacion;
        this.linea = linea;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }
}

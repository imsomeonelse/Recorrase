package visualoctopus.recorrase;

import java.io.Serializable;

/**
 * Created by Sue on 06/03/2016.
 */
public class Ruta implements Serializable{
    private String nombre;
    private double calificacion;

    public Ruta(String nombre, double calificacion) {
        this.nombre = nombre;
        this.calificacion = calificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
}

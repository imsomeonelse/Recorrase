package visualoctopus.recorrase;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sue on 06/03/2016.
 */
public class RutaCamion extends Ruta implements Serializable{
    private String ruta;
    private String ramal;
    private String rutaCompleta;
    private String letrero;
    private ArrayList<Double> calificaciones;
    private ArrayList<Double> latitudes;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Double> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(ArrayList<Double> longitudes) {
        this.longitudes = longitudes;
    }

    public ArrayList<Double> getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(ArrayList<Double> latitudes) {
        this.latitudes = latitudes;
    }

    private ArrayList<Double> longitudes;

    public RutaCamion(String nombre, String ruta, String ramal, String rutaCompleta, String letrero, double calificacion) {
        super(nombre, calificacion);
        this.ruta = ruta;
        this.ramal = ramal;
        this.rutaCompleta = rutaCompleta;
        this.letrero = letrero;
    }

    public ArrayList<Double> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ArrayList<Double> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getRutaCompleta() {
        return rutaCompleta;
    }

    public void setRutaCompleta(String rutaCompleta) {
        this.rutaCompleta = rutaCompleta;
    }

    public String getLetrero() {
        return letrero;
    }

    public void setLetrero(String letrero) {
        this.letrero = letrero;
    }

}

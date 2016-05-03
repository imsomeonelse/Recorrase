package visualoctopus.recorrase;

import java.io.Serializable;

/**
 * Created by chuch_000 on 21/03/2016.
 */
public class Comentario implements Serializable{

    private String texto;
    private String horas;
    private String fecha;
    private String usuario;

    public Comentario(String texto, String horas) {
        this.texto = texto;
        this.horas = horas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public String getHoras() {
        return horas;
    }
}

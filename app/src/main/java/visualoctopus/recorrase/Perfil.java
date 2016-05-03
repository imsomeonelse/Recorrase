package visualoctopus.recorrase;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chuch_000 on 21/03/2016.
 */
public class Perfil implements Serializable{

    private String usuario;
    private String nombre;
    private String correo;
    private String foto;
    private String nivel;
    private List<Comentario> comentarios;
    private List<Logro> logros;

    public Perfil(String usuario, String nombre, String correo, String foto, String nivel, List<Comentario> comentarios, List<Logro> logros) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.nivel = nivel;
        this.comentarios = comentarios;
        this.logros = logros;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFoto() {
        return foto;
    }

    public String getNivel() {
        return nivel;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public List<Logro> getLogros() {
        return logros;
    }
}

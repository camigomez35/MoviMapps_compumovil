package co.edu.udea.moviemapps.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SA on 03/06/2016.
 */
public class Classification {
    int valor;
    int idMovie;
    Long idUsuario;


    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}

package co.edu.udea.moviemapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SA on 03/06/2016.
 */
public class Classification {
    Integer id;
    int valor;
    int idMovie;
    Long idUsuario;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

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

    @Override
    public String toString() {
        return "Classification{" +
                "id=" + id +
                ", valor=" + valor +
                ", idMovie=" + idMovie +
                ", idUsuario=" + idUsuario +
                '}';
    }
}

package co.edu.udea.moviemapps.model;


import com.google.gson.annotations.SerializedName;

public class User {

    private long id;
    @SerializedName("nombre")
    private String name;
    @SerializedName("foto")
    private String photo;
    @SerializedName("correo")
    private String email;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
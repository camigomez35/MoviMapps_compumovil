package co.edu.udea.moviemapps.rest;


import java.util.List;

import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.CountClassification;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieMappsServiceI {

    @POST("api/Calificacions")
    Call<ServiceResult> saveClassification(@Body Classification classification);

    @POST("api/Calificacions")
    Call<Classification> saveClassificationTwo(@Body Classification classification);

    @POST("api/usuarios")
    Call<ServiceResult> saveUser(@Body User user);

    //api/Calificacions?filter[where][and][0][idUsuario]=10205357122842092&filter[where][and][1][idMovie]=271969
    @GET("api/Calificacions")
    Call<List<Classification>> getClassificationMovie(@Query("filter[where][and][0][idUsuario]") long idUsuario,
                                                      @Query("filter[where][and][1][idMovie]") Integer idMovie);


    //api/Calificacions/count?where[idMovie]=246655&where[valor]=1
    @GET("api/calificacions/count")
    Call<CountClassification> getClassificationCount(@Query("where[idMovie]") Integer idMovie,
                                               @Query("where[valor]") Integer valor);

    @PUT("api/Calificacions/{id}")
    Call<Classification> updateClassification(@Path("id") Integer id, @Body Classification classification);
}

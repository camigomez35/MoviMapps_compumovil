package co.edu.udea.moviemapps.rest;


import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.model.ServiceResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieDBServiceI {

    @GET("movie/now_playing")
    Call<ServiceResult> top_peliculas(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> movieById(@Path("id") String id,
                          @Query("api_key") String apiKey);

}

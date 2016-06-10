package co.edu.udea.moviemapps.rest;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieDBService {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MovieDBServiceI instance;

    public static MovieDBServiceI getInstance() {
        if (instance == null) {
            Interceptor requestInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().build();
                    return chain.proceed(newRequest);
                }
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(requestInterceptor);
            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            instance = retrofit.create(MovieDBServiceI.class);
        }
        return instance;
    }
}

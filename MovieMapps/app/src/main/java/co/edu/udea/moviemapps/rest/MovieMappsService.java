package co.edu.udea.moviemapps.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieMappsService {

    public static final String BASE_URL = "http://moviemappssw-samsax.c9users.io:8080/";
    private static MovieMappsServiceI instance;

    public static MovieMappsServiceI getInstance() {
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
            instance = retrofit.create(MovieMappsServiceI.class);
        }
        return instance;
    }
}

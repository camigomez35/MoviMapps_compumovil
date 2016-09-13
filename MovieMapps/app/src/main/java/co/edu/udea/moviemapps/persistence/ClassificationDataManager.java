package co.edu.udea.moviemapps.persistence;

import android.util.Log;
import android.widget.Toast;

import co.edu.udea.moviemapps.fragment.MovieDetail;
import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.rest.MovieMappsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SA on 07/06/2016.
 */

public class ClassificationDataManager {

    public int id=0;

    public static ClassificationDataManager getInstance() {
        return new ClassificationDataManager();
    }

    public int saveClassification(Classification classification){
        MovieMappsService.getInstance().saveClassification(classification).enqueue(new Callback<ServiceResult>() {
            @Override
            public void onResponse(Call<ServiceResult> call, Response<ServiceResult> response) {
                Call callResponse = call;
                Response responseService = response;
                //id = response.body().getIdCalificacion();
                Log.i("Good @@@@@", response.body().getIdCalificacion().toString());
            }

            @Override
            public void onFailure(Call<ServiceResult> call, Throwable t) {
                Log.i("Bad","Error" + t.getMessage());
            }
        });
        return id;
    }

    public int saveClassificationTwo(Classification classification){
        Call<Classification> call= MovieMappsService.getInstance().saveClassificationTwo(classification);
        call.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                Call callResponse = call;
                Response responseService = response;
                //id = response.body().getIdCalificacion();
                Log.i("Good @@@@@", ""+response.body().getId());
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable t) {
                Log.i("Bad","Error" + t.getMessage());
            }
        });
        return id;
    }

}

/*
new Callback<ServiceResult>() {
@Override
public void onResponse(Call<ServiceResult> call, Response<ServiceResult> response) {
        Call callResponse = call;
        Response responseService = response;
        Log.i("Good @@@@@", responseService.message());
        }


@Override
public void onFailure(Call<ServiceResult> call, Throwable t) {
        Log.i("Bad","Error" + t.getMessage());
        }
        } */
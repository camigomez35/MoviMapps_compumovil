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

    public static ClassificationDataManager getInstance() {
        return new ClassificationDataManager();
    }

    public void saveClassification(Classification classification){
        Log.e("INFO @@@", classification.getIdMovie()+ "  "+ classification.getValor()+ "  "+ classification.getIdUsuario());
        MovieMappsService.getInstance().saveClassification(classification).enqueue(new Callback<ServiceResult>() {
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
        });
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
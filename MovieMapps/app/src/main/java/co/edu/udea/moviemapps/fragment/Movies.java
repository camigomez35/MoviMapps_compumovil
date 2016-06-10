package co.edu.udea.moviemapps.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;
import co.edu.udea.moviemapps.listener.OnItemMovieListener;

import co.edu.udea.moviemapps.adapters.MovieAdapter;
import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.rest.MovieDBService;
import co.edu.udea.moviemapps.model.ServiceResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Movies extends Fragment implements OnItemMovieListener {
    public static final int ID = 1;

    private RecyclerView recyclerView;
    private String moviesApiKey;
    private LinearLayoutManager layoutManager;
    private OnFragmentInteractionListener listener;
    private List<Movie> movies;

    public static Movies newInstance() {
        return new Movies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesApiKey = getResources().getString(R.string.api_key);

        recyclerView = (RecyclerView) view.findViewById(R.id.movies_list);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        new DownloadMovies().execute();
        listener = (OnFragmentInteractionListener) getActivity();
    }

    @Override
    public void onItemClick(Movie movie, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(MovieDetail.MOVIE_ARG_ID, String.valueOf(movie.getId()));
        listener.setFragment(MovieDetail.ID, bundle, true);
    }

    private class DownloadMovies extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Call<ServiceResult> result = MovieDBService.getInstance().top_peliculas(moviesApiKey);
            result.enqueue(new Callback<ServiceResult>() {
                @Override
                public void onResponse(Call<ServiceResult> call, Response<ServiceResult> response) {
                    setMovies(response.body());
                }

                @Override
                public void onFailure(Call<ServiceResult> call, Throwable t) {
                }
            });

            return null;
        }
    }

    public boolean setMovies(ServiceResult result) {
        if (result != null) {
            movies = result.getResults();
            MovieAdapter adapter = new MovieAdapter((ArrayList<Movie>) movies, this);
            recyclerView.setAdapter(adapter);
            return true;
        }
        return false;
    }
}

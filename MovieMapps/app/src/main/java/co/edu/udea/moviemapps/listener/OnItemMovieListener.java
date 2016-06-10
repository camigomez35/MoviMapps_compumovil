package co.edu.udea.moviemapps.listener;

import android.view.View;

import co.edu.udea.moviemapps.model.Movie;


public interface OnItemMovieListener {

    void onItemClick(Movie movie, View view, int position);

}

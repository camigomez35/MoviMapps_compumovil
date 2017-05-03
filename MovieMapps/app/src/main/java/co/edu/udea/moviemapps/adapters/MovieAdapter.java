package co.edu.udea.moviemapps.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.udea.moviemapps.listener.OnItemMovieListener;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.util.MovieMappsUtils;

import static co.edu.udea.moviemapps.R.*;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> movies;
    private OnItemMovieListener listener;

    public MovieAdapter(ArrayList<Movie> movies, OnItemMovieListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.movie_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (movies.get(position) != null) {
            if (movies.get(position).getPosterPath() != null) {
                downloadImage(holder, movies.get(position).getPosterPath());
            }
            holder.movieTitle.setText(movies.get(position).getTitle());
            holder.movieLength.setText(String.valueOf(movies.get(position).getReleaseDate()));

            holder.movieTitle.setSelected(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(movies.get(position), holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieLength;
        ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(id.poster);
            movieTitle = (TextView) itemView.findViewById(id.movie_title);
            movieLength = (TextView) itemView.findViewById(id.movie_length);
        }
    }

    private void downloadImage(final ViewHolder viewHolder, String imageUrl) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                return MovieMappsUtils.getImage(params);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                viewHolder.moviePoster.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute(imageUrl);
    }

}

package co.edu.udea.moviemapps.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.listener.OnItemMovieListener;
import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.CountClassification;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.rest.MovieMappsServiceI;
import co.edu.udea.moviemapps.util.MovieMappsUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static co.edu.udea.moviemapps.R.*;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> movies;
    private OnItemMovieListener listener;
    private int likesCount = 0, dislikesCount =0;

    public static final String BASE_URL = "http://moviemappssw-samsax.c9users.io:8080/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public MovieAdapter(ArrayList<Movie> movies, OnItemMovieListener listener) {
        this.listener = listener;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.movie_adapter, parent, false);
        return new ViewHolder(view);
    }


    private void DownloadClassification(final ViewHolder viewHolder,final int idMovie, final int value) {
        MovieMappsServiceI apiService = retrofit.create(MovieMappsServiceI.class);
        Call<CountClassification> call = apiService.getClassificationCount(idMovie, value);
        call.enqueue(new Callback<CountClassification>() {
            @Override
            public void onResponse(Call<CountClassification> call, Response<CountClassification> response) {
                CountClassification count = response.body();
                if (value == 1) {
                    likesCount = count.getCount();
                    setBackground(viewHolder);
                } else if (value == 2) {
                    dislikesCount = count.getCount();
                    setBackground(viewHolder);
                }
            }

            @Override
            public void onFailure(Call<CountClassification> call, Throwable t) {
                Log.i("Calificar", "Error" + t.getMessage());
            }


        });

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (movies.get(position) != null) {

            DownloadClassification(holder, movies.get(position).getId(), 1);
            DownloadClassification(holder, movies.get(position).getId(), 2);
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
        TextView movieTitle, movieLength, like;
        ImageView moviePoster;


        public ViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(id.poster);
            movieTitle = (TextView) itemView.findViewById(id.movie_title);
            movieLength = (TextView) itemView.findViewById(id.movie_length);
            like = (TextView) itemView.findViewById(id.likeAdapter);
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

    public void setBackground(final ViewHolder viewHolder){
        viewHolder.like.setText("Likes: " +likesCount+"/"+(likesCount+dislikesCount));



    }
}

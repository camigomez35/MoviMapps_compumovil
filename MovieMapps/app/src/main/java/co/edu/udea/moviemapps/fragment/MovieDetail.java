package co.edu.udea.moviemapps.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.CountClassification;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.persistence.ClassificationDataManager;
import co.edu.udea.moviemapps.rest.MovieDBService;
import co.edu.udea.moviemapps.rest.MovieMappsServiceI;
import co.edu.udea.moviemapps.util.MovieMappsUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieDetail extends Fragment implements View.OnClickListener {
    public static final int ID = 2;
    public static final String MOVIE_ARG_ID = "movieId";
    public String apiKey = "d4aadc42b63f7a1565bffa6dd41f1bfc";
    public Classification classification;
    public List<Classification> classifications;
    public ImageView moviePoster;
    public ImageButton like, dislike;
    public TextView movieTitle, movieOverview, likes, dislikes;
    private int movieId, likesCount, dislikesCount;
    private Button btShare;
    private String title, text;


    public static final String BASE_URL = "http://moviemappssw-samsax.c9users.io:8080/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static MovieDetail newInstance(String movieId) {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.movieId =Integer.valueOf(movieId);
        return movieDetail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = Integer.valueOf(getArguments().getString(MOVIE_ARG_ID));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviePoster = (ImageView) view.findViewById(R.id.poster);
        movieOverview = (TextView) view.findViewById(R.id.overview);
        movieTitle = (TextView) view.findViewById(R.id.movie_title);
        like  = (ImageButton) view.findViewById(R.id.like);
        dislike = (ImageButton) view.findViewById(R.id.dislike);
        likes = (TextView) view.findViewById(R.id.likes);
        dislikes = (TextView) view.findViewById(R.id.dislikes);
        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        btShare = (Button) view.findViewById(R.id.share);
        btShare.setOnClickListener(this);
        new DownloadMovie().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like:
                classification = new Classification();
                classification.setIdMovie(movieId);
                classification.setIdUsuario(MovieMapps.getUser().getId());
                classification.setValor(Integer.parseInt("1"));
                ClassificationDataManager.getInstance().saveClassification(classification);
                likesCount = likesCount +1;
                likes.setText("Like: " + likesCount);
                setBackground();
                Toast.makeText(this.getContext(), "+1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dislike:
                classification = new Classification();
                classification.setIdMovie(movieId);
                classification.setIdUsuario(MovieMapps.getUser().getId());
                classification.setValor(Integer.parseInt("2"));
                ClassificationDataManager.getInstance().saveClassification(classification);
                dislikesCount = dislikesCount +1;
                dislikes.setText("Like: "+ dislikesCount);
                setBackground();
                Toast.makeText(this.getContext(), "-1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setPackage("com.facebook.orca");
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "Let's go to the cinema!! Let's see "+ title);
                shareIntent.setType("text/plain");
                // Launch sharing dialog for image
                startActivity(Intent.createChooser(shareIntent, "Share"));
                break;
        }
    }

    private class DownloadMovie extends AsyncTask<Void, Void, Response> {
        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            Call<Movie> results = MovieDBService.getInstance().movieById(String.valueOf(movieId), apiKey);
            results.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    setMovie(response.body());
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });
            return response;
        }
    }

    public void setMovie(Movie movie) {
        if (movie != null) {
            title = movie.getTitle();
            text = movie.getOverview();
            movieTitle.setText(title);
            movieOverview.setText(text);
            DownloadClassification(movie.getId(), 1);
            DownloadClassification(movie.getId(), 2);
            downloadImage(moviePoster, movie.getPosterPath());

        }
    }

    private void downloadImage(final ImageView imageView, String url) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                Log.d("PARAMS", "doInBackground: "+ params[0]);
                return MovieMappsUtils.getImage(params);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute(url);
    }

    private void DownloadClassification(final int idMovie, final int value) {
        MovieMappsServiceI apiService = retrofit.create(MovieMappsServiceI.class);
        Call<CountClassification> call = apiService.getClassificationCount(idMovie, value);
        call.enqueue(new Callback<CountClassification>() {
            @Override
            public void onResponse(Call<CountClassification> call, Response<CountClassification> response) {
                int statusCode = response.code();
                CountClassification count = response.body();
                if (value == 1) {
                    likesCount = count.getCount();
                    likes.setText("Like: " + likesCount);
                    setBackground();
                } else if (value == 2) {
                    dislikesCount = count.getCount();
                    dislikes.setText("Dislike: " + dislikesCount);
                    setBackground();
                }
                Log.e("Count", count.getCount() + "");
            }

            @Override
            public void onFailure(Call<CountClassification> call, Throwable t) {
                Log.i("Calificar", "Error" + t.getMessage());
            }
        });
    }

    public void setBackground(){
        if(likesCount>dislikesCount){
            movieTitle.setBackground(getResources().getDrawable(R.drawable.background_like));
        }else if(likesCount<dislikesCount){
            movieTitle.setBackground(getResources().getDrawable(R.drawable.background_dislike));
        }else{
            movieTitle.setBackground(getResources().getDrawable(R.drawable.background_title));
        }

    }
}

